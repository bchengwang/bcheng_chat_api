package com.bchengchat.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @Author Bcheng
 * @Create 2021/9/24
 * @Description 分页
 */
@ApiModel(description = "分页")
@Data
public class PagePm {
    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页", required = true)
    @NotNull
    private Integer pageNum = 1;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", required = true)
    @NotNull
    private Integer pageSize = 10;
}
