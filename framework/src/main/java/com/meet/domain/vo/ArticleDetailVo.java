package com.meet.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lenovo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {


    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 所属分类名字
     */
    private String  categoryName;

    private String content;

    private Long  categoryId;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 访问量
     */
    private Long viewCount;


    private Date createTime;
}
