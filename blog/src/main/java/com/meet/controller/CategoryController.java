package com.meet.controller;

import com.meet.domain.ResponseResult;
import com.meet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lenovo
 */
@RestController
@RequestMapping("/category")
public class CategoryController {


    @Resource
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseResult getCategory(){

        return categoryService.categoryList();
    }

}
