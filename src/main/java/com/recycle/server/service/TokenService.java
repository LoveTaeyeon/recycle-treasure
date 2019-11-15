package com.recycle.server.service;

import com.recycle.server.entity.Token;

public interface TokenService {

    boolean createToken(Token token);

    Token queryToken(Integer userId);

}
