package com.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.LoginUser;
import com.meet.domain.entity.Role;
import com.meet.domain.entity.User;
import com.meet.domain.vo.RegisterUserVo;
import com.meet.domain.vo.SysUserListVo;
import com.meet.domain.vo.UserInfoVo;
import com.meet.enums.AppHttpCodeEnum;
import com.meet.mapper.UserMapper;
import com.meet.service.UserService;
import com.meet.utils.BeanCopyUtils;
import com.meet.utils.JwtUtil;
import com.meet.utils.RedisCache;
import com.meet.utils.SecurityUtils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 16:10:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {

        //获取当前用户id
        Long userId = SecurityUtils.getUserId();

        //根据用户id查询用户信息
     /*   User user = getById(userId);*/
        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.LOGIN_USER_PREFIX + userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(loginUser.getUser(),UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        LoginUser loginUser=new LoginUser(user);
        String userId = user.getId().toString();
        //将user存入到redis中
        redisCache.setCacheObject(SystemConstants.LOGIN_USER_PREFIX +userId,loginUser);
        userMapper.updateUserInfo(user);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult register(RegisterUserVo user) {
        sysRegisterUserSafe(user);

        //对密码进行加密存入到数据库
        user.setPassword( passwordEncoder.encode(user.getPassword()));
        //插入用户数据
        userMapper.insert(BeanCopyUtils.copyBean(user, User.class));

        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult listAll() {

        List<User> users = userMapper.listAll();
        SysUserListVo userListVo=new SysUserListVo(users, users.size(),10,users.size()/10);
        return ResponseResult.okResult(userListVo);
    }

    @Override
    public ResponseResult deleteUser(Long[] ids) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.in(User::getId,ids);
        userMapper.delete(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult SysSaveUser(User user) {
        sysSaveUserSafe(user);
        if (user.getPassword()==null){
            user.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        }
        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    private void sysSaveUserSafe(User user) {
        //新增用户时的安全性判断

        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SyntaxException(AppHttpCodeEnum.USERNAME_NOT_NULL.getMsg());
        }

        if (user.getPhone()==0){
            throw new SyntaxException(AppHttpCodeEnum.PHONE_NOT_NULL.getMsg());
        }

        if (!StringUtils.hasText(user.getEmail())){
            throw new SyntaxException(AppHttpCodeEnum.EMAIL_NOT_NULL.getMsg());
        }
        if (user.getStatus()==null){
            throw new SyntaxException(AppHttpCodeEnum.STATUS_NOT_NULL.getMsg());
        }
        //(不为空)对数据进行存在的判断
        //省略写
        if (existUserName(user.getUserName())){
            throw new SyntaxException(AppHttpCodeEnum.USERNAME_EXIST.getMsg());
        }
        if (existPhone(user.getPhone())){
            throw new SyntaxException(AppHttpCodeEnum.PHONE_EXIST.getMsg());
        }
        if (existEmail(user.getEmail())){
            throw new SyntaxException(AppHttpCodeEnum.EMAIL_EXIST.getMsg());
        }


    }


    private void sysRegisterUserSafe(RegisterUserVo user) {
        sysSaveUserSafe(BeanCopyUtils.copyBean(user,User.class));

        if (!StringUtils.hasText(user.getPassword())){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_NULL.getMsg());
        }
        if (!user.getPassword().equals(user.getCheckPassword())){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_SAME.getMsg());
        }


    }

    @Override
    public ResponseResult getUserInfoById(Long id) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(queryWrapper);
        return ResponseResult.okResult(user);
    }

    @Override
    public ResponseResult assignUserRoles(Long id, Long[] ids) {
        for (Long aLong : ids) {
            userMapper.assignUserRoles(id,aLong);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult SysUpdateUser(User user) {
        sysSaveUserSafe(user);
        userMapper.updateById(user);

        return ResponseResult.okResult();
    }


    private boolean existUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
    private boolean existEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;

    }

    private boolean existPhone(int phone) {
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        return count(queryWrapper)>0;
    }


}
