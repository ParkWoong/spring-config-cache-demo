package io.github.parkwoong.demo.infra.repository;

import io.github.parkwoong.demo.infra.mapper.SettingsMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SettingsRepository {

    @Autowired private SettingsMapper mapper;

    public List<Map<String, String>> getSettings() {

        return mapper.selectAllSettings();
    }
}
