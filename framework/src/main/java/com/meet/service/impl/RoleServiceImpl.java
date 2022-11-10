package com.meet.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.domain.ResponseResult;

import com.meet.domain.entity.Role;
import com.meet.domain.vo.SysRoleListVo;
import com.meet.enums.AppHttpCodeEnum;
import com.meet.mapper.RoleMapper;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.springframework.stereotype.Service;
import com.meet.service.RoleService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-04-17 21:48:16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public ResponseResult listRoles() {

   /*     LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        queryWrapper.orderByAsc(Role::getId);
        //最多查询10条
        Page<Role> page=new Page<>(SystemConstants.ROLE_PAGE_CURRENT,SystemConstants.ROLE_PAGE_SIZE);
        page(page,queryWrapper);
        List<Role> records = page.getRecords();
        //bean拷贝
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(records, RoleVo.class);
*/
        List<Role> roles = roleMapper.selectList(null);
        SysRoleListVo sysRoleListVo =new SysRoleListVo(roles, roles.size(),10);
        return ResponseResult.okResult(sysRoleListVo);
    }

    @Override
    public ResponseResult getRoleInfo(Long id) {

        Role role = getById(id);

        return ResponseResult.okResult(role);
    }


    @Override
    public ResponseResult deleteRole(Long[] id) {
       LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper();
       queryWrapper.in(Role::getId,id);
       roleMapper.delete(queryWrapper);
       return ResponseResult.okResult();
    }



    @Override
    public ResponseResult assignRolePerms(Long id, Long[] ids) {

        for (Long aLong : ids) {
            roleMapper.assignRolePerms(id,aLong);
        }

        return ResponseResult.okResult();
    }
    @Override
    public ResponseResult saveRole(Role role) {
        safeRole(role);
        roleMapper.insert(role);
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult updateRole(Role role) {
        safeRole(role);
        roleMapper.updateById(role);
        return ResponseResult.okResult();
    }

    private void safeRole(Role role) {
        //新增角色时的安全性判断

        //对数据进行非空判断
        if (!StringUtils.hasText(role.getName())){
            throw new SyntaxException(AppHttpCodeEnum.ROLE_NAME_NOT_NULL.getMsg());
        }
        if (!StringUtils.hasText(role.getCode())){
            throw new SyntaxException(AppHttpCodeEnum.ROLE_CODE_NOT_NULL.getMsg());
        }
        if (role.getStatus()==null){
            throw new SyntaxException(AppHttpCodeEnum.STATUS_NOT_NULL.getMsg());
        }
        //(不为空)对数据进行存在的判断
        //省略写
        if (existRoleName(role.getName())){
            throw new SyntaxException(AppHttpCodeEnum.ROLE_NAME_EXIST.getMsg());
        }
        if (existCode(role.getCode())){
            throw new SyntaxException(AppHttpCodeEnum.CODE_EXIST.getMsg());
        }


    }

    private boolean existCode(String code) {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getCode,code);
        return count(queryWrapper)>0;
    }

    private boolean existRoleName(String name) {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName,name);
        return count(queryWrapper)>0;
    }
}
