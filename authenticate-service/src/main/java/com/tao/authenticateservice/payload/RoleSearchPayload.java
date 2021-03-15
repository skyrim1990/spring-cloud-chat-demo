package com.tao.authenticateservice.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleSearchPayload implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 6018262178463085607L;
  private long pageIndex;
  private long pageSize;
  private String name;
}
