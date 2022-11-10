package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Article;

/**
 * @author lenovo
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查询热门文章
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 查询文章列表
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 获取所有文章的标题
     * @return
     */
    ResponseResult getAllArticleTitle();

    /**
     * 写文章
     * @param article
     * @return
     */
    ResponseResult writeArticle(Article article);
}
