package com.tao.authenticateservice.payload;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class PageParam implements Serializable{
  /**
   *
   */
  private static final long serialVersionUID = -6978136980453195749L;
  private long offset;
  private long limit;
  public PageParam() {
    this.offset = 0;
    this.limit = 1;
  }
  // page index start at 0
  public PageParam(long pageIndex, long pageSize) {
    this.offset = pageIndex * pageSize; 
    this.limit = pageSize;
  }
  
}
