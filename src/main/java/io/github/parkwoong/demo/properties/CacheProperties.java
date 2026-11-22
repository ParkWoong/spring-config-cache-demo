package io.github.parkwoong.demo.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("cache.column")
public class CacheProperties {

    private String key;
}
