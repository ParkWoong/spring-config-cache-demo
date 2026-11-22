package io.github.parkwoong.demo.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("db.token-key.column")
public class TokenKeyProperties {

    private String key;
    private String rsaPublicKey;
    private String hs256Secret;
}
