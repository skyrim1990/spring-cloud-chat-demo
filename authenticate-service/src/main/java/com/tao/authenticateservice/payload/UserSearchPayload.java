package com.tao.authenticateservice.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserSearchPayload implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = -422708094950974829L;
  private long pageIndex;
  private long pageSize;
  private String name;
  
}
