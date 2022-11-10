package com.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meet.domain.entity.LoginUser;
import com.meet.domain.entity.User;
import com.meet.mapper.MenuMapper;
import com.meet.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author: alyosha
 * @Date: 2022/3/24 21:05
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户抛出异常
        if (Objects.isNull(user)){
            throw  new RuntimeException("用户不存在!");
        }
        //返回用户信息

        //查询权限信息并且封装
        List<String> permissionKeyList =  menuMapper.getPermsById(user.getId());

        //log.info("after:UserDetailsService   :{}", SecurityContextHolder.getContext().getAuthentication());
        return new LoginUser(user,permissionKeyList);
    }
}
