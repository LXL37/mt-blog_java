package com.meet.controller;

import com.meet.annotation.SystemLog;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.User;
import com.meet.domain.vo.RegisterUserVo;
import com.meet.enums.AppHttpCodeEnum;
import com.meet.exception.SystemException;
import com.meet.service.BlogLoginService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author lenovo
 */
@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    public ResponseResult login(@RequestBody RegisterUserVo user) throws IOException {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logOut")
    public ResponseResult logOut(){
        return blogLoginService.logOut();
    }
}
