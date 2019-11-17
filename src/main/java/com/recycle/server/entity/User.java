package com.recycle.server.entity;

import com.recycle.server.constants.Constants;
import com.recycle.server.util.DateExtUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String weiXinOpenId;
    private Integer role;
    private String phone;
    private String address;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    private String token;

    public static User build(String wenXinOpenId) {
        return User.builder()
                .weiXinOpenId(wenXinOpenId)
                .createdTime(DateExtUtils.currentTimestamp())
                .build();
    }

    public static User build(Integer userId, HttpServletRequest request) {
        String sessionToken = request.getHeader(Constants.HEADER_SESSION_KEY);
        return User.builder().id(userId).token(sessionToken).build();
    }

    public static User build(HttpServletRequest request) {
        String sessionToken = request.getHeader(Constants.HEADER_SESSION_KEY);
        Integer userId = Integer.valueOf(request.getHeader(Constants.HEADER_USER_ID_KEY));
        return User.builder().id(userId).token(sessionToken).build();
    }

}
