package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Category;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-03-13 14:38:07
 */
public interface CategoryService extends IService<Category> {
    /**
     * 获取目录列表
     * @return
     */
    ResponseResult categoryList();

}
