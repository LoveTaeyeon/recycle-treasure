package com.recycle.server.dao.mapper;

import com.recycle.server.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {

    String ORDER_TABLE_NAME = "`order`";
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
            " #{userId}",
            "<if test='status != null'>, #{status}</if>",
            "<if test='bottleCount != null'>, #{bottleCount}</if>",
            "<if test='cartonCount != null'>, #{cartonCount}</if>",
            "<if test='scrapIronCount != null'>, #{scrapIronCount}</if>",
            "<if test='other != null'>, #{other}</if>",
            "<if test='remark != null'>, #{remark}</if>",
            "<if test='createdTime != null'>, #{createdTime}</if>",
            " )",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id", useCache = false)
    boolean createOrder(Order order);

    @Update({
            "<script>",
            "update " + ORDER_TABLE_NAME,
            "<set>",
            "   <if test='order.status != null || updateNull'> status = #{order.status}, </if>",
            "   <if test='order.bottleCount != null || updateNull'> bottle_count = #{order.bottleCount}, </if>",
            "   <if test='order.cartonCount != null || updateNull'> carton_count = #{order.cartonCount}, </if>",
            "   <if test='order.scrapIronCount != null || updateNull'> scrap_iron_count = #{order.scrapIronCount}, </if>",
            "   <if test='order.other != null || updateNull'> other = #{order.other}, </if>",
            "   <if test='order.remark != null || updateNull'> remark = #{order.remark}, </if>",
            "   <if test='order.modifiedTime != null || updateNull'> modified_time = #{order.modifiedTime}, </if>",
            "</set>",
            " where id = #{order.id}",
            "</script>"
    })
    boolean updateOrder(@Param("order") Order order, @Param("updateNull") boolean updateNull);

    @Select({
            "<script>",
            "select " + QUERY_FIELDS,
            " from " + ORDER_TABLE_NAME,
            " where user_id = #{userId}",
            " <if test='createdTime != null'> and #{createdTime} > created_time </if>",
            " order by created_time desc",
            " limit #{limit}",
            "</script>"
    })
    List<Order> selectOrdersByUserId(@Param("userId") Integer userId,
                                     @Param("createdTime") Long createdTime,
                                     @Param("limit") Integer limit);

    @Select({
            "select " + QUERY_FIELDS,
            " from " + ORDER_TABLE_NAME,
            " where id = #{id}"
    })
    Order selectOrder(@Param("id") Long id);

    @Select({
            "<script>",
            "select " + QUERY_FIELDS,
            " from " + ORDER_TABLE_NAME,
            " <if test='createdTime != null'> where #{createdTime} > created_time </if>",
            " order by created_time desc",
            " limit #{limit}",
            "</script>"
    })
    List<Order> recentOrders(@Param("createdTime") Long createdTime, @Param("limit") Integer limit);

}
