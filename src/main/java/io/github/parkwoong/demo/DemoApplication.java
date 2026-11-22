package io.github.parkwoong.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.ApplicationRunner;
@EnableConfigurationProperties(RouteSettings.class)
@SpringBootApplication
public class DemoApplication {
 public static void main(String[] args) { SpringApplication.run(DemoApplication.class, args); }
 @Bean ApplicationRunner load(RouteCache cache, RouteSettings settings) { return args -> { cache.refresh(settings.routes()); System.out.println(cache.find("sample")); }; }
}
