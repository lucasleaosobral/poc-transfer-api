package com.example.demo.services.cache;

import java.util.Optional;

public interface Cache<T> {

    Optional<T> get(String key, final Class<T> clazz);

    void put(String key, Object value);

    void flush();

}
