package io.github.parkwoong.demo.infra.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TokenKeyMapper {

    @Select("select * from token_key")
    List<Map<String, String>> selectAllTokenKey();
}
