package com.meet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meet.domain.entity.Role;
import com.meet.domain.entity.User;
import com.meet.domain.vo.UserInfoVo;

import java.util.List;


/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-24 20:39:18
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 更新用户查询信息
     * @param user
     * @return
     */
    void updateUserInfo(User  user);

    /**
     * 查询全部用户
     * @return
     */
    List<User> listAll();

    /**
     * 根据id查询用户登角色
     * @param id
     * @return
     */
    List<String> getUserRoleNameById(Long id);

    /**
     * 分配用户权限
     * @param id
     * @param aLong
     */
    void assignUserRoles(Long id, Long aLong);
}
