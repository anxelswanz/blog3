package com.ansel.service;

import com.ansel.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Ansel Zhong
 * coding time
 */
public interface TagService {
  //新增
  Tag saveTag(Tag type);

  //查询
  Tag getTag(Long id);

  //分页查询
  Page<Tag> listTag(Pageable pageable);
  List<Tag> listTagTop(Integer size);

  //修改
  Tag updateTag(Long id, Tag type);

  //删除
  void deleteTag(Long id);

  //通过名称查询
  Tag getTagByName(String name);

  List<Tag> listTag();

  List<Tag> listTags(String ids);
}
