package com.meet.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lenovo
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 头像
     */
    private String avatar;

    private int sex;

    private String email;


}
