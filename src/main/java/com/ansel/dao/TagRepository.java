package com.ansel.dao;

import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
     public Tag findTypeById(Long id);

     Tag findByName(String name);
     @Query("select t from Tag t")
     List<Tag> findTop(Pageable pageable);
}
