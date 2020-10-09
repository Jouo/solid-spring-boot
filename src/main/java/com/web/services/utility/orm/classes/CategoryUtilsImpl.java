package com.web.services.utility.orm.classes;

import com.web.services.orm.api.CategoryAPI;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.utility.orm.interfaces.CategoryUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryUtilsImpl implements CategoryUtils {

    @Override
    public Category convertAPI(CategoryAPI api) {
        Category category = new Category();
        setCategory(category, api);
        return category;
    }

    @Override
    public boolean update(Category category, CategoryAPI api) {
        setCategory(category, api);
        return true;
    }

    private void setCategory(Category category, CategoryAPI api) {
        category.setName(api.getName());
    }
}
