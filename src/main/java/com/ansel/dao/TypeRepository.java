package com.ansel.dao;

import com.ansel.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
     Type getTypeById(Long id);
     Type findByName(String name);

     @Query("select t from Type t")
     List<Type> findTop(Pageable pageable);
}
