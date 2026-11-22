package io.github.parkwoong.demo.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("db.bypass-option.column")
public class BypassOptionProperties {

    private String clientId;
    private String tokenKeyId;
    private String mode;
}
