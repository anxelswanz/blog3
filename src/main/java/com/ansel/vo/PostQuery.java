package com.ansel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ansel Zhong
 * coding time
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostQuery {
  private String title;
  private Long typeId;
  private boolean recommended;
}
