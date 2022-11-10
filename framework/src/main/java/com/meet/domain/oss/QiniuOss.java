package com.meet.domain.oss;


import org.apache.ibatis.type.Alias;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: alyosha
 * @Date: 2022/3/28 20:24
 */
@Component
@ConfigurationProperties(prefix = "oss")
public class QiniuOss {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String exChainDn;

    public String getExChainDn() {
        return exChainDn;
    }

    public void setExChainDn(String exChainDn) {
        this.exChainDn = exChainDn;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

}
