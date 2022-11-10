package com.meet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meet.domain.entity.Article;
import com.meet.domain.vo.ArticleTitleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lenovo
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 获取所有文章的标题
     * @return
     */
    List<ArticleTitleVo> getAllArticleTitle();
}
