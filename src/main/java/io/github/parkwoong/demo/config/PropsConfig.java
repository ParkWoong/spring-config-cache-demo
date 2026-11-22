package io.github.parkwoong.demo.config;

import io.github.parkwoong.demo.properties.CacheProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class PropsConfig {}
