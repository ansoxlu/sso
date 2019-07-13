package com.example.uac.security.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一返回结果
 */
@Getter
@AllArgsConstructor
public class ResultError {
    private int status;
    private String message;
}
