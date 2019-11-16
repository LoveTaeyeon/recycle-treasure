package com.recycle.server.dao.mapper;

import com.recycle.server.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Mapper
@Component
public interface UserMapper {

    String USER_TABLE_NAME = "user";
    String QUERY_FIELDS = "id, wenXinOpenId, role, phone, address, createdTime";

    @Insert({
            "insert into " + USER_TABLE_NAME,
            " (wen_xin_open_id, created_time)",
            " values (#{wenXinOpenId}, #{createdTime})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id", useCache = false)
    boolean createUser(User user);

    @Update({
            "<script>",
            "update " + USER_TABLE_NAME,
            "<set>",
            "   <if test='role != null'> role = #{role}, </if>",
            "   <if test='phone != null'> phone = #{phone}, </if>",
            "   <if test='address != null'> role = #{address}, </if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    boolean updateUser(User user);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + USER_TABLE_NAME,
            " where id = #{id}"
    })
    User selectById(@Param("id") Integer id);

    @Select({
            "<script>",
            "select " + QUERY_FIELDS,
            " from " + USER_TABLE_NAME,
            " where id in ",
            " <foreach collection=\"ids\" index = \"index\" item = \"id\" open= \"(\" separator=\",\" close=\")\">",
            "    #{id}",
            " </foreach>",
            "</script>"
    })
    List<User> selectByIds(@Param("ids") Collection<Integer> ids);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + USER_TABLE_NAME,
            " where wei_xin_open_id = #{weiXinOpenId}"
    })
    User selectByWXOpenId(@Param("weiXinOpenId") String weiXinOpenId);

}
