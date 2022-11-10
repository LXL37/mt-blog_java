package com.meet.controller;

import com.meet.domain.ResponseResult;
import com.meet.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: alyosha
 * @Date: 2022/3/28 20:14
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;
    @PostMapping("upload")
    public ResponseResult uploadImg(MultipartFile img){

        return uploadService.uploadImg(img);
    }

}
