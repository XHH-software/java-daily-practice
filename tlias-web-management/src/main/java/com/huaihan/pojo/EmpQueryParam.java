package com.huaihan.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EmpQueryParam {

    private Integer page = 1; //页码，默认值为1
    private Integer pageSize = 10; //每页展示记录数，默认值为10
    private String name; //姓名
    private Integer gender; //性别
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定日期时间格式
    private LocalDate begin; //入职开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定日期时间格式
    private LocalDate end; //入职结束时间

}
