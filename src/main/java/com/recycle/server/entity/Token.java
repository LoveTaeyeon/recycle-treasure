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
public class Token {

    private Integer id;
    private Integer userId;
    private String token;
    private Timestamp createdTime;

}
