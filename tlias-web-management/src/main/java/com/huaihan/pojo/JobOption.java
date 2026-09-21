package com.huaihan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
public class JobOption {
    private List<Object> jobList; // 职位列表
    private List<Object> dataList; // 数据列表
}
