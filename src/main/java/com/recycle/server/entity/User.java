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
public class User {

    private Integer id;
    private String wenXinOpenId;
    private Integer role;
    private String phone;
    private String address;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    private String token;

}
