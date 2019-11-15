package com.recycle.server.dao.mapper;

import com.recycle.server.entity.Token;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TokenMapper {

    String TOKEN_TABLE_NAME = "token";
    String QUERY_FIELDS = "user_id, token, created_time";

    @Insert({
            "insert into " + TOKEN_TABLE_NAME,
            " (user_id, token, created_time)",
            " values (#{userId}, #{token}, #{createdTime})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id", useCache = false)
    boolean createToken(Token token);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + TOKEN_TABLE_NAME,
            " where user_id = #{userId}",
            " order by created_time desc limit 1"
    })
    Token selectUserLatestToken(@Param("userId") Integer userId);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + TOKEN_TABLE_NAME,
            " where user_id = #{userId}",
            " order by created_time desc",
            " limit #{limit}"
    })
    List<Token> selectUserTokens(@Param("userId") Integer userId, @Param("limit") Integer limit);

}
