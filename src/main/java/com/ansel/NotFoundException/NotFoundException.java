package com.ansel.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/22
 * @Description 页面找不到
 */

/**
 * @ResponseStatus(HttpStatus.NOT_FOUND)最终会将exception转换资源找不到的状态 会对应到404
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
