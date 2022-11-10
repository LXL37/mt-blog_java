package com.meet.domain.vo;

import com.meet.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alyosha
 * @Date: 2022/5/8 15:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserListVo {
    private List<User> user;
    private int total;
    private int size;
    private int current;
}
