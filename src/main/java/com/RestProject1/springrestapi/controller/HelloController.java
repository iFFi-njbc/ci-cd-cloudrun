package com.RestProject1.springrestapi.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Hello Controller")
public class HelloController {

    @GetMapping("/hello")
    @ApiOperation(value = "GET Hello Message")
    public ResponseEntity<?> hello()
    {
        return new ResponseEntity<>("Hello to my application !!!", HttpStatus.OK);
    }
}
