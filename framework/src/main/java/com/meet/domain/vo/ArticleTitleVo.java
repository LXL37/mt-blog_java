package com.meet.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alyosha
 * @Date: 2022/5/14 22:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTitleVo {
    private Long id;
    /**
     * 标题
     */
    private String title;
}
