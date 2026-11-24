package io.github.parkwoong.demo;

import io.github.parkwoong.demo.config.Store;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class CacheController {
    private final Store store;

    public CacheController(Store store) {
        this.store = store;
    }

    @GetMapping("/api/cache/{key}")
    public ResponseEntity<Map<String, String>> get(@PathVariable String key) {
        Map<String, String> value = store.getCache().get(key);
        return value == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(value);
    }

    @PostMapping("/api/cache/refresh")
    public Map<String, Integer> refresh() {
        store.refreshCache();
        return Collections.singletonMap("entries", store.getCache().size());
    }
}
