package io.github.parkwoong.demo.infra.repository;

import io.github.parkwoong.demo.infra.mapper.TokenKeyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TokenKeyRepository {

    @Autowired private TokenKeyMapper mapper;

    public List<Map<String, String>> getTokenKeys() {

        return mapper.selectAllTokenKey();
    }
}
