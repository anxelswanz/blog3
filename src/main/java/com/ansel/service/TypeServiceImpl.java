package com.ansel.service;

import com.ansel.NotFoundException.NotFoundException;
import com.ansel.dao.TypeRepository;
import com.ansel.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Ansel Zhong
 * coding time
 */
@Service
public class TypeServiceImpl implements TypeService{
  @Autowired
  private TypeRepository typeRepository;

  @Override
  @Transactional
  public Type saveType(Type type) {

    return  typeRepository.save(type);
  }
  @Transactional
  @Override
  public Type getType(Long id) {
    System.out.println("getType 查询");
    Type type= typeRepository.getTypeById(id);
    return type;
  }
  @Transactional
  @Override
  public Page<Type> listType(Pageable pageable) {
    return typeRepository.findAll(pageable);
  }

  @Transactional
  @Override
  public Type updateType(Long id, Type type) {
    Type t = typeRepository.getTypeById(id);
    if (t==null) {
      throw new NotFoundException("The Id doesn't exist");
    }
    BeanUtils.copyProperties(type, t);
    return typeRepository.save(t);
  }
  @Transactional
  @Override
  public void deleteType(Long id) {
       typeRepository.deleteById(id);
  }
  @Transactional
  @Override
  public Type getTypeByName(String name) {
    Type type = typeRepository.findByName(name);
    return type;
  }
  @Transactional
  @Override
  public List<Type> listType() {
    return typeRepository.findAll();
  }

  @Transactional
  @Override
  public List<Type> listTypeTop(Integer size) {
    Sort sort = Sort.by(Sort.Direction.DESC, "posts.size");
    Pageable pageable= PageRequest.of(0, size,sort);

    return typeRepository.findTop(pageable);
  }
}
