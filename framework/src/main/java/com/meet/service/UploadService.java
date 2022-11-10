package com.meet.service;

import com.meet.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: alyosha
 * @Date: 2022/3/28 20:19
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
