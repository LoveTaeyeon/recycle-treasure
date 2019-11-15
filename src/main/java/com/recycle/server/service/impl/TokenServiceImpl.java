package com.recycle.server.service.impl;

import com.recycle.server.dao.mapper.TokenMapper;
import com.recycle.server.entity.Token;
import com.recycle.server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private TokenMapper tokenMapper;

    @Autowired
    public TokenServiceImpl(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @Override
    public boolean createToken(Token token) {
        return tokenMapper.createToken(token);
    }

    @Override
    public Token queryToken(Integer userId) {
        return tokenMapper.selectUserLatestToken(userId);
    }

}
