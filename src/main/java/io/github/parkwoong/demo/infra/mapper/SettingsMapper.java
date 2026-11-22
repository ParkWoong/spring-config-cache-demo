package io.github.parkwoong.demo.infra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SettingsMapper {

    @Select("select * from app_settings")
    List<Map<String, String>> selectAllSettings();
}
