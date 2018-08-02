package com.qianli.ilink.cloud_platform.messagecenter.web;


import com.qianli.ilink.cloud_platform.commons.core.eneity.ResponseEntity;
import com.qianli.ilink.cloud_platform.commons.core.utils.ResponseEntityUtils;
import com.qianli.ilink.cloud_platform.messagecenter.core.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "测试用例",tags = {"hello controller"})
public class HelloController {

    @GetMapping("hello")
    public String hello() throws InterruptedException {
        Thread.sleep(1000);
        return "hello";
    }

    @PostMapping("hello2")
    public ResponseEntity hello2(@Valid @RequestBody @ApiParam() UserEntity user){
        return ResponseEntityUtils.buildSuccess(user);
    }

}
