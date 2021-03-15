package com.tao.messageservice.util;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CusMessage {
  private String from;
  private String to;
  private String content;
  private String groupId;
}
