package io.github.parkwoong.demo.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("db.client-backend-api.column")
public class BackendApiProperties {

    private String key;
    private String backendApi;
}
