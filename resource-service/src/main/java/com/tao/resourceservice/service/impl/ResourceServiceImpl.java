package com.tao.resourceservice.service.impl;

import java.util.List;

import com.tao.resourceservice.model.Resource;
import com.tao.resourceservice.repository.ResourceRepository;
import com.tao.resourceservice.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceRepository repository;

  @Override
  public List<Resource> findResources() {
    log.info("find resources");
    return repository.findResources();
  }

  @Override
  public int insertResource(Resource resource) {
    log.info("insert resources");
    return repository.insertResource(resource);
  }

}
