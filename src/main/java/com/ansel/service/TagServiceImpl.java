package com.ansel.service;

import com.ansel.NotFoundException.NotFoundException;
import com.ansel.dao.TagRepository;
import com.ansel.dao.TypeRepository;
import com.ansel.pojo.Tag;
import com.ansel.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ansel Zhong
 * coding time
 */
@Service
public class TagServiceImpl implements TagService{
  @Autowired
  private TagRepository tagRepository;

  @Override
  public Tag saveTag(Tag tag) {
    return tagRepository.save(tag);
  }

  @Override
  public Tag getTag(Long id) {
    return tagRepository.getById(id);
  }

  @Override
  public Page<Tag> listTag(Pageable pageable) {
    return tagRepository.findAll(pageable);
  }

  @Override
  public List<Tag> listTagTop(Integer size) {
    Sort sort = Sort.by(Sort.Direction.DESC, "posts.size");
    Pageable pageable= PageRequest.of(0, size,sort);

    return tagRepository.findTop(pageable);
  }


  @Override
  public Tag updateTag(Long id, Tag tag) {
    Tag t = tagRepository.findTypeById(id);
    if (t==null) {
      throw new NotFoundException("The Id doesn't exist");
    }
    BeanUtils.copyProperties(tag, t);
    return tagRepository.save(t);
  }

  @Override
  public void deleteTag(Long id) {
    tagRepository.deleteById(id);
  }

  @Override
  public Tag getTagByName(String name) {
    Tag tag = tagRepository.findByName(name);
    return tag;
  }

  @Override
  public List<Tag> listTag() {
    return tagRepository.findAll();
  }

  @Override
  public List<Tag> listTags(String ids) {
    List<Long> list = convertToList(ids);
    return tagRepository.findAllById(list);
  }

  private List<Long> convertToList(String ids) {
    List<Long> list = new ArrayList<>();
    if (!"".equals(ids) && ids != null){
      String[] idarray = ids.split(",");
      for (int i = 0; i < idarray.length; i++) {
        list.add(new Long(idarray[i]));
      }
    }
    return list;
  }
}
