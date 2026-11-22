package io.github.parkwoong.demo.config;

import io.github.parkwoong.demo.properties.BackendApiProperties;
import io.github.parkwoong.demo.properties.BypassOptionProperties;
import io.github.parkwoong.demo.properties.TokenKeyProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    BackendApiProperties.class,
    BypassOptionProperties.class,
    TokenKeyProperties.class
})
public class PropsConfig {}
