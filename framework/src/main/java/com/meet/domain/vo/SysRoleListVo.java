package com.meet.domain.vo;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.meet.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色表(Role)表实体类
 *
 * @author makejava
 * @since 2022-04-17 21:48:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SysRoleListVo {
    private List<Role> roleList;

    /**
     * 分页相关
     */
    private Integer total;
    private int size;


}