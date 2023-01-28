package com.ansel.vo;

import com.ansel.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/27
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostQuery2 {
    private String title;
    private Long typeId;
    private boolean recommended;
    private Long userId;
}
