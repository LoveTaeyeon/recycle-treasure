package com.recycle.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Integer userId;
    private Integer status;
    private Integer bottleCount;
    private Integer cartonCount;
    private Integer scrapIronCount;
    private String other;
    private String remark;
    private Long createdTime;
    private Timestamp modifiedTime;
    private Timestamp updatedTime;

    private User userInfo;

}
