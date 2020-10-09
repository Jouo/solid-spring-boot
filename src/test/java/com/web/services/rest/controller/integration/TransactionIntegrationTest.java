package com.web.services.rest.controller.integration;

import com.web.services.orm.api.GroupAPI;
import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.business.Stock;
import com.web.services.orm.entity.transaction.Checkout;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.orm.entity.transaction.Sale;
import com.web.services.orm.service.interfaces.CheckoutService;
import com.web.services.orm.service.interfaces.ProductService;
import com.web.services.orm.service.interfaces.PurchaseService;
import com.web.services.orm.service.interfaces.SaleService;
import com.web.services.rest.utility.http.response.BindingExceptionResponse;
import com.web.services.rest.utility.http.response.BindingExceptionResponseImpl;
import com.web.services.utility.orm.interfaces.PurchaseUtils;
import com.web.services.utility.orm.interfaces.SaleUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleUtils saleUtils;

    @Autowired
    private PurchaseUtils purchaseUtils;

    private final String CHECKOUT_PATH = "/api/checkout";
    private final String SALE_PATH = "/api/sale";
    private final String PURCHASE_PATH = "/api/purchase";
    private final String PRODUCT_PATH = "/api/product";

    /*  These values will increase upon running the tests that creates a transaction,
        and will be used upon running the test on the checkout, to match values.    */
    private static Float saleTotal = 0.0f;
    private static Float purchaseTotal = 0.0f;

    // This keeps track of the stock, to test the increment and reduction of it
    private static Map<Long, Integer> productStock = new LinkedHashMap<>();

    // Everything below here will test the majority of the code, through valid input
    private final Integer transactionTimes = 2;
    private static final Integer quantity = 10;
    private final Integer difference = transactionTimes * quantity;

    private static final List<Long> saleProductID = List.of(1L, 2L, 3L);
    private static final List<Long> purchaseProductID = List.of(4L, 5L, 6L);

    private static List<GroupAPI> saleTransaction = new ArrayList<>();
    private static List<GroupAPI> purchaseTransaction = new ArrayList<>();

    private final SaleAPI validSale = new SaleAPI(1L, saleTransaction);
    private final PurchaseAPI validPurchase = new PurchaseAPI(1L, purchaseTransaction);

    // These are used to test the validations upon using wrong input
    private final List<GroupAPI> invalidTransaction = List.of(new GroupAPI(99L, -1));
    private final SaleAPI invalidSale = new SaleAPI(99L, invalidTransaction);
    private final PurchaseAPI invalidPurchase = new PurchaseAPI(99L, invalidTransaction);

    @BeforeAll
    static void setTransactions() {
        for (Long id : saleProductID) {
            saleTransaction.add(new GroupAPI(id, quantity));
        }

        for (Long id : purchaseProductID) {
            purchaseTransaction.add(new GroupAPI(id, quantity));
        }
    }

    //  Sale tests before doing the checkout

    @Order(2)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("Check current product stock before sale")
    void stockBeforeSale(long idProduct) {
        response = rest.exchange(PRODUCT_PATH + "/" + idProduct, HttpMethod.GET, token, Product.class);
        Product product = (Product) response.getBody();
        Stock stock = product.getStock();

        productStock.put(product.getId(), stock.getQuantity());

        assertNotNull(product);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve the empty list of unchecked sales")
    void uncheckedSalesEmptyBefore() {
        response = rest.exchange(SALE_PATH + "/unchecked/1", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, sales.length);
    }

    @Order(4)
    @RepeatedTest(2)
    @DisplayName("Generate two sales")
    void generateSale() {
        HttpEntity<SaleAPI> request = new HttpEntity<>(validSale, headers);
        response = rest.exchange(SALE_PATH, HttpMethod.POST, request, Sale.class);
        Sale sale = (Sale) response.getBody();
        saleTotal += sale.getTotal();
        assertNotNull(sale);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Order(5)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    @DisplayName("Check current product stock after sale")
    void stockAfterSale(long idProduct) {
        response = rest.exchange(PRODUCT_PATH + "/" + idProduct, HttpMethod.GET, token, Product.class);
        Product product = (Product) response.getBody();
        Stock stock = product.getStock();

        Integer currentStock = stock.getQuantity();
        Integer expectedStock = productStock.get(idProduct) - difference;

        assertNotNull(product);
        assertEquals(expectedStock, currentStock);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Compare values between sale and api")
    void compareSale() {
        response = rest.exchange(SALE_PATH + "/1", HttpMethod.GET, token, Sale.class);
        Sale sale = (Sale) response.getBody();
        assertNotNull(sale);
        assertTrue(saleUtils.isMatch(sale, validSale));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Retrieve the list of 2 unchecked sales")
    void uncheckedSalesFilledBefore() {
        response = rest.exchange(SALE_PATH + "/unchecked/1", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionTimes, sales.length);
    }

    @Test
    @Order(8)
    @DisplayName("Retrieve the empty list of checked sales")
    void checkedSalesEmptyBefore() {
        response = rest.exchange(SALE_PATH + "/checked", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, sales.length);
    }

    // Purchase tests before doing the checkout

    @Order(9)
    @ParameterizedTest
    @ValueSource(longs = {4, 5, 6})
    @DisplayName("Check current product stock before purchase")
    void stockBeforePurchase(long idProduct) {
        response = rest.exchange(PRODUCT_PATH + "/" + idProduct, HttpMethod.GET, token, Product.class);
        Product product = (Product) response.getBody();
        Stock stock = product.getStock();

        productStock.put(product.getId(), stock.getQuantity());

        assertNotNull(product);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(10)
    @DisplayName("Retrieve the empty list of unchecked purchases")
    void uncheckedPurchaseEmptyBefore() {
        response = rest.exchange(PURCHASE_PATH + "/unchecked/1", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, purchases.length);
    }

    @Order(11)
    @RepeatedTest(2)
    @DisplayName("Generate two purchases")
    void generatePurchase() {
        HttpEntity<PurchaseAPI> request = new HttpEntity<>(validPurchase, headers);
        response = rest.exchange(PURCHASE_PATH, HttpMethod.POST, request, Purchase.class);
        Purchase purchase = (Purchase) response.getBody();
        purchaseTotal += purchase.getTotal();
        assertNotNull(purchase);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Order(12)
    @ParameterizedTest
    @ValueSource(longs = {4, 5, 6})
    @DisplayName("Check current product stock after purchase")
    void stockAfterPurchase(long idProduct) {
        response = rest.exchange(PRODUCT_PATH + "/" + idProduct, HttpMethod.GET, token, Product.class);
        Product product = (Product) response.getBody();
        Stock stock = product.getStock();

        Integer currentStock = stock.getQuantity();
        Integer expectedStock = productStock.get(idProduct) + difference;

        assertNotNull(product);
        assertEquals(expectedStock, currentStock);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(13)
    @DisplayName("Compare values between purchase and api")
    void comparePurchase() {
        response = rest.exchange(PURCHASE_PATH + "/1", HttpMethod.GET, token, Purchase.class);
        Purchase purchase = (Purchase) response.getBody();
        assertNotNull(purchase);
        assertTrue(purchaseUtils.isMatch(purchase, validPurchase));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(14)
    @DisplayName("Retrieve the list of 2 unchecked purchases")
    void uncheckedPurchaseFilledBefore() {
        response = rest.exchange(PURCHASE_PATH + "/unchecked/1", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionTimes, purchases.length);
    }

    @Test
    @Order(15)
    @DisplayName("Retrieve the empty list of checked purchases")
    void checkedPurchasesEmptyBefore() {
        response = rest.exchange(PURCHASE_PATH + "/checked", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, purchases.length);
    }

    // Testing the checkout methods

    @Test
    @Order(16)
    @DisplayName("Retrieve the empty list of checkouts")
    void getEmptyListBeforeCheckout() {
        response = rest.exchange(CHECKOUT_PATH, HttpMethod.GET, token, Checkout[].class);
        Checkout[] checkouts = (Checkout[]) response.getBody();
        assertNotNull(checkouts);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, checkouts.length);
    }

    @Test
    @Order(17)
    @DisplayName("Retrieve the empty list of checkouts by user id")
    void getEmptyListByUserBeforeCheckout() {
        response = rest.exchange(CHECKOUT_PATH + "/user/1", HttpMethod.GET, token, Checkout[].class);
        Checkout[] checkouts = (Checkout[]) response.getBody();
        assertNotNull(checkouts);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, checkouts.length);
    }

    @Test
    @Order(18)
    @DisplayName("Generate checkout by user id")
    void generateCheckout() {
        response = rest.exchange(CHECKOUT_PATH + "/1", HttpMethod.POST, token, Checkout.class);
        Checkout checkout = (Checkout) response.getBody();
        assertNotNull(checkout);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(19)
    @DisplayName("Retrieve the list of 1 checkout")
    void getFilledListAfterCheckout() {
        response = rest.exchange(CHECKOUT_PATH, HttpMethod.GET, token, Checkout[].class);
        Checkout[] checkouts = (Checkout[]) response.getBody();
        assertNotNull(checkouts);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, checkouts.length);
    }

    @Test
    @Order(20)
    @DisplayName("Retrieve the list of 1 checkout by user id")
    void getFilledListByUserAfterCheckout() {
        response = rest.exchange(CHECKOUT_PATH + "/user/1", HttpMethod.GET, token, Checkout[].class);
        Checkout[] checkouts = (Checkout[]) response.getBody();
        assertNotNull(checkouts);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, checkouts.length);
    }

    @Test
    @Order(21)
    @DisplayName("Compare values between the checkout, apis and total")
    void compareCheckout() {
        response = rest.exchange(CHECKOUT_PATH + "/1", HttpMethod.GET, token, Checkout.class);
        Checkout checkout = (Checkout) response.getBody();
        assertNotNull(checkout);
        assertEquals(saleTotal, checkout.getTotalSale());
        assertEquals(purchaseTotal, checkout.getTotalPurchase());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Retrieve checked and unchecked transactions

    @Test
    @Order(22)
    @DisplayName("Retrieve the empty list of unchecked sales")
    void uncheckedSalesEmptyAfter() {
        response = rest.exchange(SALE_PATH + "/unchecked/1", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, sales.length);
    }

    @Test
    @Order(23)
    @DisplayName("Retrieve the list of 2 checked sales")
    void checkedSalesFilledAfter() {
        response = rest.exchange(SALE_PATH + "/checked", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionTimes, sales.length);
    }

    @Test
    @Order(24)
    @DisplayName("Retrieve the empty list of unchecked purchases")
    void uncheckedPurchaseEmptyAfter() {
        response = rest.exchange(PURCHASE_PATH + "/unchecked/1", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, purchases.length);
    }

    @Test
    @Order(25)
    @DisplayName("Retrieve the list of 2 checked purchases")
    void checkedPurchasesFilledAfter() {
        response = rest.exchange(PURCHASE_PATH + "/checked", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionTimes, purchases.length);
    }

    // Delete the checkout

    @Test
    @Order(26)
    @DisplayName("Delete the checkout")
    void deleteCheckout() {
        response = rest.exchange(CHECKOUT_PATH + "/1", HttpMethod.DELETE, token, Boolean.class);
        assertNotNull(response);
        assertTrue((Boolean) response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(27)
    @DisplayName("Retrieve the empty list of checkouts")
    void getEmptyListAfterCheckout() {
        response = rest.exchange(CHECKOUT_PATH, HttpMethod.GET, token, Checkout[].class);
        Checkout[] checkouts = (Checkout[]) response.getBody();
        assertNotNull(checkouts);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, checkouts.length);
    }

    @Test
    @Order(28)
    @DisplayName("Retrieve the empty list of checkouts by user id")
    void getEmptyListByUserAfterCheckout() {
        response = rest.exchange(CHECKOUT_PATH + "/user/1", HttpMethod.GET, token, Checkout[].class);
        Checkout[] checkouts = (Checkout[]) response.getBody();
        assertNotNull(checkouts);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, checkouts.length);
    }

    @Test
    @Order(29)
    @DisplayName("Retrieve the list of 2 unchecked sales")
    void uncheckedSalesFilledAfter() {
        response = rest.exchange(SALE_PATH + "/unchecked/1", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionTimes, sales.length);
    }

    @Test
    @Order(30)
    @DisplayName("Retrieve the empty list of checked sales")
    void checkedSalesEmptyAfter() {
        response = rest.exchange(SALE_PATH + "/checked", HttpMethod.GET, token, Sale[].class);
        Sale[] sales = (Sale[]) response.getBody();
        assertNotNull(sales);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, sales.length);
    }

    @Test
    @Order(31)
    @DisplayName("Retrieve the list of 2 unchecked purchases")
    void uncheckedPurchaseFilledAfter() {
        response = rest.exchange(PURCHASE_PATH + "/unchecked/1", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionTimes, purchases.length);
    }

    @Test
    @Order(32)
    @DisplayName("Retrieve the empty list of checked purchases")
    void checkedPurchasesEmptyAfter() {
        response = rest.exchange(PURCHASE_PATH + "/checked", HttpMethod.GET, token, Purchase[].class);
        Purchase[] purchases = (Purchase[]) response.getBody();
        assertNotNull(purchases);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, purchases.length);
    }

    // Throw test cases

    @Test
    @Order(33)
    @DisplayName("Throw: Invalid input values to generate a sale")
    void generateSaleThrow() {
        HttpEntity<SaleAPI> request = new HttpEntity<>(invalidSale, headers);
        response = rest.exchange(SALE_PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(34)
    @DisplayName("Throw: Invalid input values to generate a purchase")
    void generatePurchaseThrow() {
        HttpEntity<PurchaseAPI> request = new HttpEntity<>(invalidPurchase, headers);
        response = rest.exchange(PURCHASE_PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}