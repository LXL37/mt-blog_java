package com.meet.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Article;
import com.meet.domain.entity.Category;
import com.meet.domain.vo.*;
import com.meet.service.ArticleService;
import com.meet.mapper.ArticleMapper;
import com.meet.service.CategoryService;
import com.meet.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.meet.constants.SystemConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lenovo
 */
@Service
public class ArticleServiceImpl  extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Resource
    private CategoryService categoryService;

    @Resource
    private ArticleMapper articleMapper;
    @Override
    public ResponseResult hotArticleList() {
        //查询文章
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章:
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //根据浏览量进行排序

        queryWrapper.orderByDesc(Article::getViewCount);

        //最多查询10条
        Page<Article> page=new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> pageRecords = page.getRecords();
        pageRecords.parallelStream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //bean拷贝
        List<ArticleListVo> ArticleListVo = BeanCopyUtils.copyBeanList(pageRecords, ArticleListVo.class);
        return ResponseResult.okResult(ArticleListVo);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop降序

        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page=new Page(pageNum,pageSize);
        page(page,queryWrapper);
        //查询categoryName
        List<Article> pageRecords = page.getRecords();
        //根据categoryID查询name
         pageRecords.parallelStream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return  ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article,ArticleDetailVo.class);

        //根据分类id查询分类名
        Long categoryId = article.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }


        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult getAllArticleTitle() {
        List<ArticleTitleVo> titleList=articleMapper.getAllArticleTitle();
        return ResponseResult.okResult(titleList);
    }

    @Override
    public ResponseResult writeArticle(Article article) {

        articleMapper.insert(article);
        return ResponseResult.okResult();
    }
}
