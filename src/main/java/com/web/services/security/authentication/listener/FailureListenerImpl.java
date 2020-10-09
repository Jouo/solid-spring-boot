package com.web.services.security.authentication.listener;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.web.services.Setting;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@DependsOn({"setting"})
public class FailureListenerImpl implements FailureListener {

    private LoadingCache<String, Integer> cache;

    public FailureListenerImpl() {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(Setting.AUTH_BAN_TIME, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    public Integer load(String ip) {
                        return 0;
                    }
                });
    }

    @Override
    public void save(String ip) {
        int attempts = this.cache.getUnchecked(ip);
        if (attempts == 0) {
            this.cache.put(ip, 1);
        }
    }

    @Override
    public void loginSucceeded(String ip) {
        this.cache.invalidate(ip);
    }

    @Override
    public void loginFailed(String ip) {
        int attempts;
        try {
            attempts = this.cache.get(ip);
        }
        catch (Exception exception) {
            attempts = 1;
        }
        attempts++;
        this.cache.put(ip, attempts);
    }

    @Override
    public boolean isBlocked(String ip) {
        try {
            return this.cache.get(ip) >= Setting.AUTH_ATTEMPTS;
        }
        catch (Exception exception) {
            return false;
        }
    }
}
