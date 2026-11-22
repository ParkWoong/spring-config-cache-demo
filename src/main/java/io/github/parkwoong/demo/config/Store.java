package io.github.parkwoong.demo.config;

import io.github.parkwoong.demo.infra.repository.BypassOptionRepository;
import io.github.parkwoong.demo.infra.repository.ClientBackendApiRepository;
import io.github.parkwoong.demo.infra.repository.TokenKeyRepository;
import io.github.parkwoong.demo.properties.BackendApiProperties;
import io.github.parkwoong.demo.properties.BypassOptionProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Store implements ApplicationRunner {

    private List<Map<String, String>> tokenKeyList;
    private Map<String, Map<String, String>> clientBackendMap = new HashMap<>();
    private Map<String, Map<String, String>> optionMap = new HashMap<>();

    @Autowired private BackendApiProperties apiProperties;

    @Autowired private BypassOptionProperties optionProperties;

    @Autowired private TokenKeyRepository tokenKeyRepository;

    @Autowired private ClientBackendApiRepository backendApiRepository;

    @Autowired private BypassOptionRepository optionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        refreshTokenKeyList();
        refreshClientBackendMap();
        refreshOptionList();
    }

    public void refreshTokenKeyList() {

        log.info("refresh: token key list");
        tokenKeyList = tokenKeyRepository.getTokenKeys();
    }

    public void refreshClientBackendMap() {

        log.info("refresh: client backend api map");
        backendApiRepository
                .getClientBackendApis()
                .forEach(map -> clientBackendMap.put(map.get(apiProperties.getKey()), map));
    }

    public void refreshOptionList() {

        log.info("refresh: option list");
        optionRepository
                .getOptions()
                .forEach(
                        map -> {
                            final String clientId = map.get(optionProperties.getClientId());

                            if (!optionMap.containsKey(clientId))
                                optionMap.put(clientId, new HashMap<>());

                            optionMap
                                    .get(clientId)
                                    .put(
                                            map.get(optionProperties.getTokenKeyId()),
                                            map.get(optionProperties.getMode()));
                        });
    }
}
