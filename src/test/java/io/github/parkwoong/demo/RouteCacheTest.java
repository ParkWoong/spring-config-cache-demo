package io.github.parkwoong.demo;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RouteCacheTest {
 @Autowired RouteCache cache;
 @Test void bindsAndReplacesWithoutStaleEntries() {
  assertEquals("http://localhost:9090", cache.find("sample"));
  cache.refresh(Map.of("new", "http://localhost:9091"));
  assertNull(cache.find("sample"));
  assertThrows(IllegalArgumentException.class, () -> cache.refresh(Map.of("bad", "file:///tmp/test")));
  assertEquals("http://localhost:9091", cache.find("new"));
 }
}
