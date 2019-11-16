package com.recycle.server.entity.response;

import com.recycle.server.entity.model.PageToken;
import com.recycle.server.util.token.PageTokenUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    Object item;
    List itemList;
    Map itemMap;
    PageToken paging;

    public static TokenResponse build(Object data, PageToken token) {
        return TokenResponse.builder().item(data).paging(PageTokenUtils.build(token)).build();
    }

    public static TokenResponse build(List dataList, PageToken token) {
        return TokenResponse.builder().itemList(dataList).paging(PageTokenUtils.build(token)).build();
    }

    public static TokenResponse build(Map dataMap, PageToken token) {
        return TokenResponse.builder().itemMap(dataMap).paging(PageTokenUtils.build(token)).build();
    }

    public static TokenResponse build(Object data, List dataList, Map dataMap, PageToken token) {
        return TokenResponse.builder()
                .item(data)
                .itemList(dataList)
                .itemMap(dataMap)
                .paging(PageTokenUtils.build(token))
                .build();
    }

}
