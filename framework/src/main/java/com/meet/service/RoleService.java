package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Role;


/**
 * 角色表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-04-17 21:48:16
 */
public interface RoleService extends IService<Role> {
    /**
     * 获取权限信息
     * @return
     */
    ResponseResult listRoles();

    /**
     * 根据id获取role信息
     * @param id
     * @return
     */
    ResponseResult getRoleInfo(Long id);

    /**
     * 删除权限信息
     * @param id
     * @return
     */
    ResponseResult deleteRole(Long[] id);

    /**
     * 新增sys权限
     * @param role
     * @return
     */
    ResponseResult saveRole(Role role);

    /**
     * 根据id 添加role对应的ids 权限
     * @param id
     * @param ids
     * @return
     */
    ResponseResult assignRolePerms(Long id, Long[] ids);

    /**
     * 更新role
     * @param role
     * @return
     */
    ResponseResult updateRole(Role role);
}


