package com.huaihan.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private Integer page = 1; // 分页查询的页码，如果未指定，默认为1
    private Integer pageSize = 10; // 分页查询的每页记录数，如果未指定，默认为10
    private String name;
    private Integer degree;
    private Integer clazzId;
}
