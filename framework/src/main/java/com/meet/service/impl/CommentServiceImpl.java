package com.meet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Comment;
import com.meet.domain.vo.CommentVo;
import com.meet.domain.vo.PageVo;
import com.meet.enums.AppHttpCodeEnum;
import com.meet.exception.SystemException;
import com.meet.mapper.CommentMapper;
import com.meet.mapper.UserMapper;
import com.meet.service.CommentService;
import com.meet.service.UserService;
import com.meet.utils.BeanCopyUtils;
import com.meet.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 15:25:14
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private UserService userService;
    @Resource
    private CommentMapper commentMapper;
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {

            //查询对应文章的根评论
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            //对articleId进行判断
            queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
            //根评论 rootId为-1
            queryWrapper.eq(Comment::getRootId,-1);

            //评论类型
            queryWrapper.eq(Comment::getType,commentType);

            //分页查询
            Page<Comment> page = new Page(pageNum,pageSize);
            page(page,queryWrapper);

            List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

            //查询所有根评论对应的子评论集合，并且赋值给对应的属性
            for (CommentVo commentVo : commentVoList) {
                //查询对应的子评论
                List<CommentVo> children = getChildren(commentVo.getId());
                //赋值
                commentVo.setChildren(children);
            }

            return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
        }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        comment.setCreateBy(SecurityUtils.getUserId());
        save(comment);
        
        return null;
    }

    @Override
    public ResponseResult allCommentList(String commentType, Long articleId) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);

        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        List<CommentVo> commentVoList = toCommentVoList(commentMapper.selectList(queryWrapper));
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(commentVoList);
    }

    /**
     * 根据根评论id查询 子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment>  comments=list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;


    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVos.stream()
                .forEach(commentVo -> {

                    String nickName = userService.getById(commentVo.getCreateBy()).getUserName();
                    commentVo.setUserName(nickName);
                    if (commentVo.getToCommentUserId()!=-1){
                        String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getUserName();
                        commentVo.setToCommentUserName(toCommentUserName);
                    }
                });
        return commentVos;
    }
}
