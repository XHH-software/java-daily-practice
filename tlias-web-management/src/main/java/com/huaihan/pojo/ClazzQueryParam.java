package com.huaihan.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 接收请求参数的实体类
 */
@Data
public class ClazzQueryParam {
    private Integer page = 1; // 分页查询的页码，如果未指定，默认为1
    private Integer pageSize = 10; // 分页查询的每页记录数，如果未指定，默认为10
    private String name; // 班级名称
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定接收的日期格式
    private LocalDate begin; // 范围匹配的开始时间(结课时间)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定接收的日期格式
    private LocalDate end; // 范围匹配的结束时间(结课时间)

}
