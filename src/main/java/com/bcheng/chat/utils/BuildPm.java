package com.bcheng.chat.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author Bcheng
 * @Create 2022/2/25
 * @Description
 */
public interface BuildPm<T> {
    @JsonIgnore
    T build();
}
