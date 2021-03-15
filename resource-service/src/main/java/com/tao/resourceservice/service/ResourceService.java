package com.tao.resourceservice.service;

import java.util.List;

import com.tao.resourceservice.model.Resource;

public interface ResourceService {
  public List<Resource> findResources();
  public int insertResource(Resource resource);  
}
