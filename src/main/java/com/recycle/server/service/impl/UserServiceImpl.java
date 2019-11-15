package com.recycle.server.service.impl;

import com.google.common.base.Strings;
import com.recycle.server.annotations.ValidateSession;
import com.recycle.server.dao.mapper.TokenMapper;
import com.recycle.server.dao.mapper.UserMapper;
import com.recycle.server.entity.Token;
import com.recycle.server.entity.User;
import com.recycle.server.entity.exception.InvalidSession;
import com.recycle.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private TokenMapper tokenMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, TokenMapper tokenMapper) {
        this.userMapper = userMapper;
        this.tokenMapper = tokenMapper;
    }

    @Override
    @Transactional
    public User weiXinLogin(User user) throws Exception {
        // validate is valid data
        if (Strings.isNullOrEmpty(user.getWenXinOpenId()) || Strings.isNullOrEmpty(user.getToken())) {
            throw new InvalidSession();
        }
        User existedUser = userMapper.selectByWXOpenId(user.getWenXinOpenId());
        // sync token
        if (existedUser != null) {
            tokenMapper.createToken(Token.build(existedUser.getId(), user.getToken()));
            existedUser.setToken(user.getToken());
            return existedUser;
        } else {
            // it's new user
            User newUser = User.build(user.getWenXinOpenId());
            userMapper.createUser(newUser);
            tokenMapper.createToken(Token.build(newUser.getId(), user.getToken()));
            newUser.setToken(user.getToken());
            return newUser;
        }
    }

    @Override
    @ValidateSession
    public User getUser(User user) throws Exception {
        user = userMapper.selectById(user.getId());
        Token token = tokenMapper.selectUserLatestToken(user.getId());
        user.setToken(token.getToken());
        return user;
    }

    @Override
    @ValidateSession
    public User updateUser(User user) throws Exception {
        userMapper.updateUser(user);
        return userMapper.selectById(user.getId());
    }

}
