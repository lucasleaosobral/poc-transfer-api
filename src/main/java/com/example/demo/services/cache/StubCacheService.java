package com.example.demo.services.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
@ConditionalOnProperty(value = "app.cache.type", havingValue = "stub")
public class StubCacheService<T> implements Cache {

    @Value("${app.cache.ttl}")
    private int redisTTL;

    private Map<String, FiniteObject> cache = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    @SneakyThrows
    public Optional<T> get(String key, final Class clazz) {
        var cached = cache.get(key);

        if(cached == null)
            return Optional.empty();

        if(cached.ttl < now().toEpochMilli()) {
            cache.remove(key);
            return Optional.empty();

        }
        final T returnObject = (T) convertToClass(objectMapper.writeValueAsString(cached.getObject()), clazz);
        return Optional.of(returnObject);
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, new FiniteObject(value, now().plus(Duration.ofMinutes(redisTTL)).toEpochMilli()));
    }

    @Override
    public void flush() {
        cache.clear();
    }


    @AllArgsConstructor
    @Getter
    private static class FiniteObject {
        private final Object object;
        private final Long ttl;
    }

    private Instant now() { return Instant.now(); }

    @SneakyThrows
    private T convertToClass(final String obj, final Class<T> clazz) {
        return objectMapper.readValue(obj, clazz);
    }
}
