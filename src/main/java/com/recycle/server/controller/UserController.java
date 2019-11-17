package com.recycle.server.controller;

import com.recycle.server.constants.ResponseStrings;
import com.recycle.server.entity.User;
import com.recycle.server.entity.exception.InvalidSession;
import com.recycle.server.entity.request.LoginRequest;
import com.recycle.server.service.UserService;
import com.recycle.server.util.ResUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserController {

    private HttpServletRequest request;
    private UserService userService;

    @Autowired
    public UserController(HttpServletRequest request,
                          UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        try {
            User user = User.builder()
                    .weiXinOpenId(request.getWeiXinOpenId())
                    .token(request.getWeiXinSessionToken())
                    .build();
            user = userService.weiXinLogin(user);
            return ResUtils.ok(user);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Login Failed] request: " + request, e);
            return ResUtils.unknownException(e);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable("id") Integer userId) {
        try {
            User user = userService.getUser(User.build(request), userId);
            return ResUtils.ok(user);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Query User Failed] userId: " + userId, e);
            return ResUtils.unknownException(e);
        }
    }

    @PostMapping("/user/update")
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            User sessionUser = User.build(request);
            if (user.getId() == null || sessionUser.getId() == null) {
                return ResUtils.invalidReq(ResponseStrings.EMPTY_MESSAGE);
            }
            if (!sessionUser.getId().equals(user.getId())) {
                return ResUtils.invalidReq(ResponseStrings.EMPTY_MESSAGE);
            }
            user.setRole(null);
            user.setWeiXinOpenId(null);
            user.setCreatedTime(null);
            user.setToken(sessionUser.getToken());
            User updatedInfo = userService.updateUser(user);
            return ResUtils.ok(updatedInfo);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Update User Failed] user: " + user, e);
            return ResUtils.unknownException(e);
        }
    }

}
