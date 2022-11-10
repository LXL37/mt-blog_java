package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.User;
import com.meet.domain.vo.RegisterUserVo;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @Author: alyosha
 * @Date: 2022/3/24 20:41
 */
public interface BlogLoginService  extends IService<User> {
    /**
     * 登录接口
     * @param user
     * @return
     */
    ResponseResult login(RegisterUserVo user) throws IOException;

    /**
     * 退出登录
     * @return
     */
    ResponseResult logOut();
}
