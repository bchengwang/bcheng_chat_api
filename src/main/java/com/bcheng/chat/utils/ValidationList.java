package com.bcheng.chat.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Bcheng
 * @Create 2021/7/20
 * @Description <p>包装 List类型，并声明 @Valid 注解 </p>
 */
@Data
@AllArgsConstructor
public class ValidationList<E> implements List<E> {
    @Valid
    @Delegate
    public List<E> list;

    public ValidationList() {
        this(new ArrayList<>());
    }

    @Override
    public String toString() { return list.toString(); }
}
