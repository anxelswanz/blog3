package com.ansel.dao;

import com.ansel.pojo.Post;
import com.ansel.pojo.Tag;
import javafx.geometry.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {

  public Post findPostById(Long id);

  @Query("select p from Post p where p.recommended = true")
   public List<Post> findTop(Pageable pageable);

  @Query("select p from Post p where p.published = true")
  public Page<Post> findPublishedPosts(Pageable pageable);

  @Query(value = "select p from Post p where p.user.id = ?1")
  public Page<Post> findAllByUserId(Long userId, Pageable pageable);

  @Query("select p from Post p where p.title like ?1 or p.content like ?1")
  public Page<Post> findByQury(String query,Pageable pageable);

  @Query("select function('date_format',p.updateTime,'%Y') as year from Post p group by function('date_format',p.updateTime,'%Y') order by function('date_format',p.updateTime,'%Y')  desc")
  List<String> findGroupYears();

  @Query("select p from Post p where  function('date_format',p.updateTime,'%Y') = ?1")
  List<Post> findByYear(String year);
}

