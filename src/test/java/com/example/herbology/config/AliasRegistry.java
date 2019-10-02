package com.example.herbology.config;

import cucumber.api.java.Before;

import java.util.HashMap;
import java.util.Map;


public class AliasRegistry {
    private Map<String, String> registry = new HashMap<>();

    @Before
    public void clearState() {
        registry.clear();
    }

    public void put(String alias, String value) {
        registry.put(alias, value);
    }

    public String resolve(String alias) {
        return registry.getOrDefault(alias, alias);
    }

    public Long resolveAsLong(String alias) {
        return Long.parseLong(resolve(alias));
    }
}
