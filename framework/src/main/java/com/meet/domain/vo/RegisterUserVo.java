package com.meet.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: alyosha
 * @Date: 2022/5/12 19:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserVo {
    @TableId
    private Long id;


    private String password;
    private String checkPassword;
    private String email;
    private int phone;
    private int sex;

    private String userName;


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
