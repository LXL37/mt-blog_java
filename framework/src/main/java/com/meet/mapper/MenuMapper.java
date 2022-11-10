package com.meet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meet.domain.entity.Menu;

import java.util.List;


/**
 * 菜单表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-17 21:48:06
 */
public interface MenuMapper extends BaseMapper<Menu> {

    Menu getInfoById(Long id);

    void deleteById(Long id);

    List<String> getPermsById(Long id);
}
