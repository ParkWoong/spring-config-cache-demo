package io.github.parkwoong.demo.config;

import io.github.parkwoong.demo.infra.repository.SettingsRepository;
import io.github.parkwoong.demo.properties.CacheProperties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Component
public class Store implements ApplicationRunner {
    private Map<String, Map<String, String>> cache = new HashMap<>();
    @Autowired private CacheProperties cacheProperties;
    @Autowired private SettingsRepository settingsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        refreshCache();
    }

    public void refreshCache() {
        log.info("refresh: settings cache");
        settingsRepository
                .getSettings()
                .forEach(row -> cache.put(row.get(cacheProperties.getKey()), row));
    }
}
