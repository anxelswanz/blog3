package com.ansel.dao;

import com.ansel.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/26
 * @Description
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostIdAndParentCommentNull(Long postId,Sort sort);

}
