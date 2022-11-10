package com.meet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meet.domain.entity.Role;
import com.meet.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;


/**
 * 角色表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-17 21:48:16
 */
public interface RoleMapper extends BaseMapper<Role> {

      /**
       * 根据id删除role
       * @param id
       */
      void deleteRole(Long id);

      /**
       * 插入role_menu 表
       * @param roleId
       * @param permId
       */
      void assignRolePerms( Long roleId,  Long permId);


}
