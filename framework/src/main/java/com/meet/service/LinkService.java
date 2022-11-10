package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-03-19 10:13:31
 */
public interface LinkService extends IService<Link> {
    /**
     * 获取所有友链
     * @return
     */
    public ResponseResult getAllLink();
}
