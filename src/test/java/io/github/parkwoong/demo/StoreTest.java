package io.github.parkwoong.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.parkwoong.demo.config.Store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class StoreTest {
    @Autowired private Store store;
    @Autowired private JdbcTemplate jdbc;

    @Test
    void loadsReadsAndRefreshesCache() {
        assertEquals("light", store.getCache().get("theme").get("setting_value"));
        assertNull(store.getCache().get("missing"));

        jdbc.update("update app_settings set setting_value=? where setting_key=?", "dark", "theme");
        assertEquals("light", store.getCache().get("theme").get("setting_value"));

        store.refreshCache();
        assertEquals("dark", store.getCache().get("theme").get("setting_value"));
    }
}
