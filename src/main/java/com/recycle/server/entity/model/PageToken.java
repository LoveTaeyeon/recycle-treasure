package com.recycle.server.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageToken {

    private Integer type;
    private Long preId;
    private Long nextId;
    private String nextPageToken;
    private String prePageToken;

    public static PageToken empty() {
        return PageToken.builder().build();
    }

}
