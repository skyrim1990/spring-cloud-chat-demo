package com.tao.resourceservice.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("resource")
public class Resource {
  private long id;
  private String serialname;
  private String name;
  private String note;
  private Date createdAt;
  private Date updatedAt;
}
