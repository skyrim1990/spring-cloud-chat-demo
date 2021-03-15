package com.tao.resourceservice.repository;

import java.util.List;

import com.tao.resourceservice.model.Resource;

import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository {
  public List<Resource> findResources();
  public int insertResource(Resource resource);
}
