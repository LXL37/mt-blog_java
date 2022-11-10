package com.meet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-03-26 15:25:14
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查询评论
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    ResponseResult addComment(Comment comment);

    /**
     * 全部评论
     * @param articleComment
     * @param articleId
     * @return
     */
    ResponseResult allCommentList(String commentType, Long articleId);
}
