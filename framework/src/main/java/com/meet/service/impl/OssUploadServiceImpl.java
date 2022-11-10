package com.meet.service.impl;

import com.google.gson.Gson;
import com.meet.domain.ResponseResult;
import com.meet.domain.oss.QiniuOss;
import com.meet.enums.AppHttpCodeEnum;
import com.meet.exception.SystemException;
import com.meet.service.UploadService;
import com.meet.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @Author: alyosha
 * @Date: 2022/3/28 20:20
 */
@Service
public class OssUploadServiceImpl implements UploadService {
    @Resource
    private QiniuOss qiniuOss;
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        String imgOriginalFilename = img.getOriginalFilename();
        if (!(imgOriginalFilename.endsWith(".png")||
               imgOriginalFilename.endsWith("PNG")||
                imgOriginalFilename.endsWith("img"))){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //如果判断通过 上传文件到oss
        String filePath= PathUtils.generateFilePath(imgOriginalFilename);
        String url=ossUpload(img,filePath);
        return ResponseResult.okResult(url);
    }

    public String ossUpload(MultipartFile img,String filePath){
    //构造一个带指定 Region 对象的配置类

        Configuration cfg = new Configuration(Region.autoRegion());
    //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
    //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
    //获取外链
        String exChainDN = new StringBuilder()
                .append("http://").append(qiniuOss.getExChainDn()).append("/"+key).toString();
        System.out.println("外链+"+exChainDN);
        try {
            InputStream inputStream=img.getInputStream();
            Auth auth = Auth.create(qiniuOss.getAccessKey(), qiniuOss.getSecretKey());
            String upToken = auth.uploadToken(qiniuOss.getBucket());
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return exChainDN;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
            return exChainDN;
        } catch ( Exception ex) {
            //ignore
        }
        return exChainDN;
    }
}
