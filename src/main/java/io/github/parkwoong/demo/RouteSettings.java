package io.github.parkwoong.demo;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties("demo")
public record RouteSettings(Map<String, String> routes) {}
