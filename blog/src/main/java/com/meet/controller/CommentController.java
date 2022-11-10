package com.meet.controller;

import com.meet.constants.SystemConstants;
import com.meet.domain.ResponseResult;
import com.meet.domain.entity.Comment;
import com.meet.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: alyosha
 * @Date: 2022/3/26 15:39
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @GetMapping("/allCommentList/{articleId}")
    public ResponseResult allCommentList(@PathVariable("articleId") Long articleId){
        return commentService.allCommentList(SystemConstants.ARTICLE_COMMENT,articleId);
    }

    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }




}
