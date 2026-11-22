package io.github.parkwoong.demo;

import java.net.URI;
import java.util.Map;
import org.springframework.stereotype.Component;
@Component
public class RouteCache {
 private volatile Map<String, String> routes = Map.of();
 public void refresh(Map<String, String> replacement) {
  var snapshot = Map.copyOf(replacement);
  snapshot.forEach((name, address) -> {
   var uri = URI.create(address);
   if (name.isBlank() || !("http".equals(uri.getScheme()) || "https".equals(uri.getScheme())) || uri.getHost() == null || uri.getUserInfo() != null)
    throw new IllegalArgumentException("Invalid route");
  });
  routes = snapshot;
 }
 public String find(String client) { return routes.get(client); }
}
