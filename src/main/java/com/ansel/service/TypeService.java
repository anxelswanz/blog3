package com.ansel.service;

import com.ansel.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface TypeService {
  //新增
  Type saveType(Type type);

  //查询
  Type getType(Long id);

  //分页查询
  Page<Type> listType(Pageable pageable);

  //修改
  Type updateType(Long id, Type type);

  //删除
  void deleteType(Long id);

  //通过名称查询
  Type getTypeByName(String name);

  //获取所有
  List<Type> listType();

  //首页
  List<Type> listTypeTop (Integer size);
}
