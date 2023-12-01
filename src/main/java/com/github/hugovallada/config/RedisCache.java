package com.github.hugovallada.config;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.hugovallada.model.qrcode.ChavePix;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisCache {

    private final ValueCommands<String, ChavePix> commands;

    public RedisCache(RedisDataSource redisDs) {
        commands = redisDs.value(ChavePix.class);
    }

    public ChavePix get(String key) {
        return commands.get(key);
    }

    public void set(String key, ChavePix pix) {
        commands.set(key, pix, new SetArgs().ex(Duration.ofMinutes(30)));
    }

    public void evict(String key) {
        commands.getdel(key);
    }

    public ChavePix getOrSetIfAbsent(String key, Supplier<ChavePix> cache) {
        var cached = get(key);
        if (Objects.nonNull(cached)) {
            return cached;
        }
        var result = cache.get();
        set(key, result);
        return result;
    }

}
