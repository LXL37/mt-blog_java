package com.meet.controller;



import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Role;
import com.meet.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.ResultSet;

/**
 * 角色表(Role)表控制层
 *
 * @author makejava
 * @since 2022-04-17 21:48:16
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public ResponseResult listRoles(){
        return roleService.listRoles();
    }
    @GetMapping("/info/{id}")
    public ResponseResult getRoleInfo(@PathVariable("id") Long id ){
        return roleService.getRoleInfo(id);
    }
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public ResponseResult deleteRole(@RequestBody Long[] id){
        return roleService.deleteRole(id);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public ResponseResult saveRole(@RequestBody Role role){
        return  roleService.saveRole(role);
    }
     @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public ResponseResult updateRole(@RequestBody Role role){
        return  roleService.updateRole(role);
    }


    @PostMapping("/perm/{id}")
    public ResponseResult assignRolePerms(@PathVariable("id") Long id,@RequestBody Long[] ids){return  roleService.assignRolePerms(id,ids);}
}

