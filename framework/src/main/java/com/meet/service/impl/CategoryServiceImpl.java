package com.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Article;
import com.meet.domain.entity.Category;
import com.meet.domain.vo.CategoryVo;
import com.meet.mapper.CategoryMapper;
import com.meet.service.ArticleService;
import com.meet.service.CategoryService;
import com.meet.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-03-13 14:38:07
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;
    @Override
    public ResponseResult categoryList() {
        //查询文章表(已经发布)
        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(articleWrapper);
        //获取文章的id并且去重
        Set<Long> categoryId = list.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryId);
        //文章是正常状态
        List<Category> categoryNormal = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryNormal, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}
