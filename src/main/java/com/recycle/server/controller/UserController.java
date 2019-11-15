package com.recycle.server.controller;

import com.recycle.server.entity.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest request) {
        try {
            return null;
        } catch (Exception e) {
            log.error("[Login Failed] request: " + request, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
