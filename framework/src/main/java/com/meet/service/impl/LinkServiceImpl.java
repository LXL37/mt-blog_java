package com.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Link;
import com.meet.domain.vo.LinkVo;
import com.meet.mapper.LinkMapper;
import com.meet.service.LinkService;
import com.meet.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-03-19 10:13:31
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList=list(queryWrapper);
        //转换成vo
        List<LinkVo> vs = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);

        //封装返回

        return ResponseResult.okResult(vs);
    }
}
