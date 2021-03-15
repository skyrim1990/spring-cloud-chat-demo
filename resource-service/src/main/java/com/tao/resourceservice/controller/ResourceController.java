package com.tao.resourceservice.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;

import com.tao.resourceservice.model.Resource;
import com.tao.resourceservice.service.ResourceService;
import com.tao.resourceservice.util.JsonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {

  @Autowired
  private ResourceService service;

  @GetMapping("/findAll")
  public JsonResponse findAllResources() {
    List<Resource> res = service.findResources();
    return JsonResponse.success("success", res);
  }

  @PostMapping("/insert")
  public JsonResponse insertResources(
      @RequestParam("name") String name,
      @RequestParam("note") String note,
      @RequestParam("file") Part file) throws NoSuchAlgorithmException {
    
    if(name == null || name.length() == 0) {
      return JsonResponse.error("请添加文件名 !", null);
    }
    if(file == null) {
      return JsonResponse.error("请添加文件 !", null);
    }
    
      

    String fileName = file.getSubmittedFileName();

    // 生成serialname
    long stamp = new Date().getTime();
    String newName = stamp + "_" + name + ".png";

    //写入文件 

    try{
      file.write(newName);
    } catch (Exception e) {
      e.printStackTrace();
      return JsonResponse.error("上传资源失败 !", null);
    }

    Resource res = new Resource();
    res.setName(name);
    res.setSerialname(newName);
    res.setNote(note);

    int result = service.insertResource(res);
    if(result != 1) {
      return JsonResponse.error("上传资源失败 !", null);
    } else {
      return JsonResponse.success("上传资源成功 !", newName);
    }

  }
  
}
