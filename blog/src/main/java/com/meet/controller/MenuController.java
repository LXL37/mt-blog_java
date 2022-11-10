package com.meet.controller;



import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Menu;
import com.meet.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 菜单表(Menu)表控制层
 *
 * @author makejava
 * @since 2022-04-17 21:48:03
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController  {
    @Resource
    private MenuService menuService;
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public ResponseResult getAllList(){

        return  menuService.getAllList();
    }
    @GetMapping("/info/{id}")
    public ResponseResult getInfoById(@PathVariable("id")Long id){
        return menuService.getInfoById(id);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public ResponseResult deleteById(@PathVariable("id")Long id){
        return menuService.deleteById(id);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public ResponseResult saveMenu(@RequestBody Menu menu){
        return menuService.saveMenu(menu);
    }
}

