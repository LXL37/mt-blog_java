package com.meet.controller;

import com.meet.domain.ResponseResult;

import com.meet.domain.entity.Article;
import org.springframework.web.bind.annotation.*;
import com.meet.service.ArticleService;

import javax.annotation.Resource;


/**
 * @author lenovo
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     *  查询热门文章
     * @return
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        return articleService.hotArticleList();
    }

    /**
     * 查询文章列表
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){

        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/allArticleTitle")
    public ResponseResult getAllArticleTitle(){
        return articleService.getAllArticleTitle();
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){



        return articleService.getArticleDetail(id);

    }
    @PostMapping("/writeArticle")
    public ResponseResult writeArticle(@RequestBody Article article){return articleService.writeArticle(article);}

}
