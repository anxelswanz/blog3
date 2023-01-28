package com.ansel.service;

import com.ansel.pojo.Post;
import com.ansel.vo.PostQuery;
import com.ansel.vo.PostQuery2;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface PostService {
  //获取一个
  Post getPost(long id);
  Post getAndConvert(Long id);
  //分页查询
  Page<Post> listPost(Pageable pageable, PostQuery post);
  Page<Post> listPostByUser(PostQuery2 postQuery2, Pageable pageable);
  Page<Post> listPost(Pageable pageable);
  Page<Post> listPublishedPost(Pageable pageable);
  List<Post> listPostTop(Integer size);
  Page<Post> listPost(String query, Pageable pageable);
  Page<Post> listPost(Long tagId, Pageable pageable);
  //string 年份 list 博客列表
  Map<String,List<Post>> archivePosts();
  Long countPost();
  //新增
  Post savePost(Post post);
  Post savePostWhenBrowse(Post post);
  //修改
  Post updatePost(Long id, Post post);
  //删除
  void deletePost(Long id);
}
