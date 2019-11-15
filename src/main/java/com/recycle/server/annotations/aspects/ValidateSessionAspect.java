package com.recycle.server.annotations.aspects;

import com.recycle.server.annotations.ValidateSession;
import com.recycle.server.constants.ResponseStrings;
import com.recycle.server.entity.Token;
import com.recycle.server.entity.User;
import com.recycle.server.entity.exception.InvalidSession;
import com.recycle.server.service.TokenService;
import com.recycle.server.util.metrics.MainMetrics;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidateSessionAspect {

    private TokenService tokenService;

    @Autowired
    public ValidateSessionAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Pointcut(value = "@annotation(com.recycle.server.annotations.ValidateSession)")
    private void pointcut() {
    }

    @Around("pointcut() && @annotation(validateSession)")
    public Object validate(ProceedingJoinPoint point, ValidateSession validateSession) throws Throwable {
        Object[] args = point.getArgs();
        try {
            User user = (User) args[0];
            Token token = tokenService.queryToken(user.getId());
            if (!token.getToken().equals(user.getToken())) {
                throw new InvalidSession();
            }
        } catch (InvalidSession invalidSession) {
            MainMetrics.INVALID_SESSION.increment();
            throw invalidSession;
        } catch (Exception e) {
            log.error("[Validate Session Failed]", e);
        }
        return point.proceed();
    }

}
