package com.meet.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 菜单表(Menu)表实体类
 *
 * @author makejava
 * @since 2022-04-17 21:48:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class Menu  {
    @TableId
    private Long id;

    private Long parentId;
    @TableField(exist = false)
    private List<Menu> children=new ArrayList<>();
    //菜单名
    private String name;

    //路由地址
    private String path;

    //组件路径
    private String component;


    //菜单状态（0正常 1停用）
    private int status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;

    private int orderNum;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private int type;

}
