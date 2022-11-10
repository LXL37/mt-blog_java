package com.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.mapper.MenuMapper;
import  com.meet.domain.entity.Menu;
import com.meet.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 菜单表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-04-17 21:48:05
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    @Override
    public ResponseResult getAllList() {



        return ResponseResult.okResult(buildTreeMenu(menuMapper.selectList(null)));
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        return ResponseResult.okResult(menuMapper.getInfoById(id));
    }

    @Override
    public ResponseResult deleteById(Long id) {
        menuMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult saveMenu(Menu menu) {
        menuMapper.insert(menu);
        return ResponseResult.okResult();
    }


    private List<Menu> buildTreeMenu(List<Menu> menus) {

        List<Menu> finalMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (Menu menu : menus) {

            for (Menu e : menus) {
                if (menu.getId().equals(e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }

            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }

        return finalMenus;
    }
}
