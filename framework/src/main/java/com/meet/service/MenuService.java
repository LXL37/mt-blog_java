package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Menu;


/**
 * 菜单表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-04-17 21:48:05
 */
public interface MenuService extends IService<Menu> {
    /**
     * 查询菜单表列表
     * @return
     */
    ResponseResult getAllList();

    /**
     * 根据id返回菜单信息
     * @param id
     * @return
     */
    ResponseResult getInfoById(Long id);

    /**
     * 删除菜单根据id
     * @param id
     * @return
     */
    ResponseResult deleteById(Long id);

    /**
     * 根据sys创建menu信息
     * @param menu
     * @return
     */
    ResponseResult saveMenu(Menu menu);
}
