package com.meet.controller;

import com.meet.annotation.SystemLog;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.User;
import com.meet.domain.vo.RegisterUserVo;
import com.meet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: alyosha
 * @Date: 2022/3/27 18:56
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){

        return userService.userInfo();
    }
    @GetMapping("/info/{id}")
    public ResponseResult getUserInfoById(@PathVariable("id")Long id){
        return userService.getUserInfoById(id);
    }
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterUserVo user){
        return userService.register(user);
    }
    @GetMapping("/list")
    @SystemLog(businessName = "查询全部用户")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResponseResult listAll(){


        return userService.listAll();
    }
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public ResponseResult deleteUser(@RequestBody Long[] ids){
        return userService.deleteUser(ids);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public ResponseResult SysSaveUser(@RequestBody User user){
        return userService.SysSaveUser(user);
    }
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public ResponseResult SysUpdateUser(@RequestBody User user){
        return userService.SysUpdateUser(user);
    }


    @PostMapping("/role/{id}")
    @PreAuthorize("hasAnyAuthority('sys:user:role')")
    public ResponseResult assignUserRoles(@PathVariable("id") Long id,@RequestBody Long[] ids){return  userService.assignUserRoles(id,ids);}
}
