package io.github.parkwoong.demo;

import static org.junit.jupiter.api.Assertions.*;

import io.github.parkwoong.demo.config.Store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class StoreTest {
    @Autowired Store store;
    @Autowired JdbcTemplate jdbc;

    @Test
    void originalDatabaseLoadingAndManualRefresh() {
        assertTrue(store.getTokenKeyList().isEmpty());
        assertEquals("N", store.getOptionMap().get("sample-client").get("sample-key"));
        assertEquals(
                "http://localhost:9090",
                store.getClientBackendMap().get("sample-client").get("backend_api"));
        jdbc.update(
                "update client_backend_api set backend_api=? where client_id=?",
                "http://localhost:9091",
                "sample-client");
        store.refreshClientBackendMap();
        assertEquals(
                "http://localhost:9091",
                store.getClientBackendMap().get("sample-client").get("backend_api"));
    }
}
