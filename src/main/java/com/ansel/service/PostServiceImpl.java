package com.ansel.service;

import com.ansel.NotFoundException.NotFoundException;
import com.ansel.dao.PostRepository;
import com.ansel.pojo.Post;
import com.ansel.pojo.Type;
import com.ansel.utils.MarkdownUtils;
import com.ansel.vo.PostQuery;
import com.ansel.vo.PostQuery2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Ansel Zhong
 * coding time
 */
@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository postRepository;
  @Override
  public Post getPost(long id) {
    System.out.println("enter:"+"getPost");
    return postRepository.findPostById(id);
  }

  @Override
  public Post getAndConvert(Long id) {
    Post post = getPost(id);
    if (post == null) {
      throw new NotFoundException("The post does not exist");
    }
    savePostWhenBrowse(post);

    //避免修改数据库的值
    Post p = new Post();
    String content = post.getContent();
    System.out.println("content= " +content);
    String content2 = MarkdownUtils.markdownToHtmlExtensions(content);
    System.out.println("content2= " + content2);
    BeanUtils.copyProperties(post, p);
    System.out.println("p= " + p);
    p.setContent(content2);
    return p;
  }

  public Page<Post> listPost(Pageable pageable){
        return postRepository.findAll(pageable);
  }

  @Override
  public Page<Post> listPublishedPost(Pageable pageable) {
    return postRepository.findPublishedPosts(pageable);
  }

  @Override
  public List<Post> listPostTop(Integer size) {
    Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
    Pageable pageable= PageRequest.of(0, size,sort);
    return postRepository.findTop(pageable);
  }

  @Override
  public Page<Post> listPost(String query, Pageable pageable) {
    return postRepository.findByQury(query,pageable);
  }

  @Override
  public Page<Post> listPost(Long tagId, Pageable pageable) {

    return postRepository.findAll(new Specification<Post>() {
      @Override
      public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Join join = root.join("tags");
        return cb.equal(join.get("id"),tagId);
      }
    },pageable);
  }



  @Override
  public Map<String, List<Post>> archivePosts() {
    List<String> years = postRepository.findGroupYears();
    Map<String, List<Post>> map = new HashMap<>();
    for (String year : years) {
       map.put(year, postRepository.findByYear(year));
    }

    return map;
  }

  @Override
  public Long countPost() {
    //返回post集合所有条数
    return postRepository.count();
  }

  @Transactional
  @Override
  public Page<Post> listPost(Pageable pageable, PostQuery post) {
    System.out.println("enter: postserviceimpl -->"+ "listPost");
    System.out.println(post);
    return postRepository.findAll(new Specification<Post>() {
      @Override
      public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (!"".equals(post.getTitle()) && post.getTitle() != null) {
          System.out.println("enter:" + "if (!\"\".equals(post.getTitle()) && post.getTitle() != null)");
          predicates.add(cb.like(root.<String>get("title"), "%"+post.getTitle()+"%"));
        }
        if (post.getTypeId() != null) {
          System.out.println("enter:" + " (post.getTypeId() != null) ");
          predicates.add(cb.equal(root.<Type>get("type").get("id"), post.getTypeId()));
        }
        if (post.isRecommended()) {
          System.out.println("enter" + " if (post.isRecommended())");
          predicates.add(cb.equal(root.<Boolean>get("recommended"), post.isRecommended()));
        }
        System.out.println(predicates);
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    },pageable);
  }

  @Transactional
  @Override
  public Page<Post> listPostByUser(PostQuery2 post, Pageable pageable) {


    return postRepository.findAll(new Specification<Post>() {
      @Override
      public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (!"".equals(post.getTitle()) && post.getTitle() != null) {
          System.out.println("enter:" + "if (!\"\".equals(post.getTitle()) && post.getTitle() != null)");
          predicates.add(cb.like(root.<String>get("title"), "%"+post.getTitle()+"%"));
        }
        if (post.getTypeId() != null) {
          System.out.println("enter:" + " (post.getUserId() != null) ");
          predicates.add(cb.equal(root.<Type>get("type").get("id"), post.getTypeId()));
        }
        if (post.getUserId() != null) {
          System.out.println("enter:" + " (post.getTypeId() != null) ");
          predicates.add(cb.equal(root.<Type>get("user").get("id"), post.getUserId()));
        }
        if (post.isRecommended()) {
          System.out.println("enter" + " if (post.isRecommended())");
          predicates.add(cb.equal(root.<Boolean>get("recommended"), post.isRecommended()));
        }
        System.out.println(predicates);
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        return null;
      }
    },pageable);
  }

  @Transactional
  @Override
  public Post savePost(Post post) {

      post.setCreateDate(new Date());
      post.setUpdateTime(new Date());
      post.setViews(0);

    return postRepository.save(post);
  }

  @Override
  public Post savePostWhenBrowse(Post post) {
    post.setViews(post.getViews() + 1);
    return postRepository.save(post);
  }


  @Transactional
  @Override
  public Post updatePost(Long id, Post post) {
    System.out.println("enter:"+"updatePost");
    Post p = getPost(id);

    if (p == null){
      throw new NotFoundException("Such post does not exist");
    }

    System.out.println("before p:"+p);
    Date createDate = p.getCreateDate();
    Integer views = p.getViews();
    post.setCreateDate(createDate);
    post.setUpdateTime(new Date());
    post.setViews(views);
    System.out.println("post:"+post);
    post.setCreateDate(createDate);
    post.setViews(views);
    System.out.println("update p:" + p);
    return postRepository.save(post);
  }
  @Transactional
  @Override
  public void deletePost(Long id) {
      postRepository.deleteById(id);
  }


}
