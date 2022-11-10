package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.User;
import com.meet.domain.vo.RegisterUserVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-03-26 16:10:37
 */
public interface UserService extends IService<User> {
    /**
     * 获取用户个人信息
     * @return
     */
    ResponseResult userInfo();

    /**
     * 更新个人信息
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 注册用户
     * @param user
     * @return
     */
    ResponseResult register(RegisterUserVo user);

    /**
     * 获取全部用户
     * @return
     */
    ResponseResult listAll();

    /**
     * 根据id删除用户 包括批量删除
     * @param ids
     * @return
     */
    ResponseResult deleteUser(Long[] ids);

    /**
     * 根据前端信息新增用户,
     * @param user
     * @return
     */
    ResponseResult SysSaveUser(User user);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    ResponseResult getUserInfoById(Long id);

    /**
     * 根据userId 分配roleId对应的权限
     * @param id
     * @param ids
     * @return
     */
    ResponseResult assignUserRoles(Long id, Long[] ids);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    ResponseResult SysUpdateUser(User user);
}
