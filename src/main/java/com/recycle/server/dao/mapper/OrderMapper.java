package com.recycle.server.dao.mapper;

import com.recycle.server.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {

    String ORDER_TABLE_NAME = "order";
    String QUERY_FIELDS = "id, user_id, status, bottle_count, carton_count, scrap_iron_count," +
            " other, remark, created_time, modified_time";

    @Insert({
            "<script>",
            "insert into " + ORDER_TABLE_NAME,
            " (",
            " user_id, status",
            "<if test='bottleCount != null'>, bottle_count</if>",
            "<if test='cartonCount != null'>, carton_count</if>",
            "<if test='scrapIronCount != null'>, scrap_iron_count</if>",
            "<if test='other != null'>, other</if>",
            "<if test='remark != null'>, remark</if>",
            "<if test='createdTime != null'>, created_time</if>",
            " ) values ( ",
            " #{userId}, #{status}",
            "<if test='bottleCount != null'>, #{bottleCount}</if>",
            "<if test='cartonCount != null'>, #{cartonCount}</if>",
            "<if test='scrapIronCount != null'>, #{scrapIronCount}</if>",
            "<if test='other != null'>, #{other}</if>",
            "<if test='remark != null'>, #{remark}</if>",
            "<if test='createdTime != null'>, #{createdTime}</if>",
            " )",
            "</script>"
    })
    boolean createOrder(Order order);

    @Update({
            "update " + ORDER_TABLE_NAME,
            "<set>",
            "   <if test='status != null'> status = #{status}, </if>",
            "   <if test='bottleCount != null'> bottle_count = #{bottleCount}, </if>",
            "   <if test='cartonCount != null'> carton_count = #{cartonCount}, </if>",
            "   <if test='scrapIronCount != null'> scrap_iron_count = #{scrapIronCount}, </if>",
            "   <if test='other != null'> other = #{other}, </if>",
            "   <if test='remark != null'> remark = #{remark}, </if>",
            "   <if test='modifiedTime != null'> modified_time = #{modifiedTime}, </if>",
            "</set>",
            " where id = #{id}"
    })
    boolean updateOrder(Order order);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + ORDER_TABLE_NAME,
            " where user_id = #{userId}",
            " <if test='createdTime != null'> and created_time > #{createdTime} </if>",
            " order by created_time desc",
            " limit #{limit}"
    })
    List<Order> selectOrdersByUserId(@Param("userId") Integer userId,
                                     @Param("createdTime") Long createdTime,
                                     @Param("limit") Integer limit);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + ORDER_TABLE_NAME,
            " where id = #{id}"
    })
    Order selectOrder(@Param("id") Integer id);

}
