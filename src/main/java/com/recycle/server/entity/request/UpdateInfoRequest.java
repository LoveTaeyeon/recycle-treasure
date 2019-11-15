package com.recycle.server.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInfoRequest {

    private Integer userId;
    private String name;
    private String phone;
    private String address;

}
