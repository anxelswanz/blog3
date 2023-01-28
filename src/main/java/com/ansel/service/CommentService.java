package com.ansel.service;

import com.ansel.pojo.Comment;

import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/26
 * @Description
 */
public interface CommentService {
    List<Comment> listCommentByPostId(Long postId);
    Comment saveComment(Comment comment);
}
