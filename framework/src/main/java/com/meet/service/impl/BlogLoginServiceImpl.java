package com.meet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ForwardingSortedSet;
import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.LoginUser;
import com.meet.domain.entity.User;
import com.meet.domain.vo.BlogUserLoginVo;
import com.meet.domain.vo.RegisterUserVo;
import com.meet.domain.vo.UserInfoVo;
import com.meet.enums.AppHttpCodeEnum;
import com.meet.mapper.UserMapper;
import com.meet.service.BlogLoginService;

import com.meet.utils.BeanCopyUtils;
import com.meet.utils.JwtUtil;
import com.meet.utils.RedisCache;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.SortedSet;
import java.util.function.IntFunction;

/**
 * @Author: alyosha
 * @Date: 2022/3/24 20:56
 */
@Service
@Slf4j
public class BlogLoginServiceImpl  extends ServiceImpl<UserMapper, User> implements BlogLoginService {
    @Resource
    private  AuthenticationManager authenticationManager;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult login(RegisterUserVo user) throws IOException {
        //????????????????????????
        userAuthenticate(user);

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // ??????UserDetailService





        //??????UserId??????Token
        LoginUser loginUser=(LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
       /* String jsonString = JSONObject.toJSONString();*/
        String jwt = JwtUtil.createJWT(userId);
        //???user?????????redis???
        redisCache.setCacheObject(SystemConstants.LOGIN_USER_PREFIX +userId,loginUser);
        //???token???userInfo ????????????
        //???user?????????userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo=new BlogUserLoginVo(jwt,userInfoVo);


        return ResponseResult.okResult(blogUserLoginVo);
    }

    private void userAuthenticate(RegisterUserVo user) {
        System.out.println("loginuser"+user.toString());
        //???????????????????????????
        if (!StringUtils.hasText(user.getUserName())){
            throw new SyntaxException(AppHttpCodeEnum.USERNAME_NOT_NULL.getMsg());
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_NULL.getMsg());
        }
        //????????????
        if (!user.getPassword().equals(user.getCheckPassword())){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_SAME.getMsg());
        }
        //???????????????
        if (!existUserName(user.getUserName())){
            throw new SyntaxException(AppHttpCodeEnum.USERNAME_NOT_EXIST.getMsg());
        }
        if (!existPassword(user)){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_EXIST.getMsg());
        }

    }
    private boolean existUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
    private boolean existPassword(RegisterUserVo userVo) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userVo.getUserName());
        User user = userMapper.selectOne(queryWrapper);

        return passwordEncoder.matches(userVo.getPassword(),user.getPassword());
    }

    @Override
    public ResponseResult logOut() {
        //??????token ????????????userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //??????userId (???token?????????)
        Long userId = loginUser.getUser().getId();
        //??????redis????????????????????????????????????
        redisCache.deleteObject(SystemConstants.LOGIN_USER_PREFIX +userId);
        return ResponseResult.okResult();
    }


}
