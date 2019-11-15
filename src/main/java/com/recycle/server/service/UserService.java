package com.recycle.server.service;

import com.recycle.server.entity.User;

public interface UserService {

    User weiXinLogin(User user) throws Exception;

    User getUser(User user) throws Exception;

    User updateUser(User user) throws Exception;

}
