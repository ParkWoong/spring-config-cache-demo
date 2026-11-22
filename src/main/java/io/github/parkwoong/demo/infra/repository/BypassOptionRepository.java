package io.github.parkwoong.demo.infra.repository;

import io.github.parkwoong.demo.infra.mapper.BypassOptionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BypassOptionRepository {

    @Autowired private BypassOptionMapper mapper;

    public List<Map<String, String>> getOptions() {

        return mapper.selectAllOption();
    }
}
