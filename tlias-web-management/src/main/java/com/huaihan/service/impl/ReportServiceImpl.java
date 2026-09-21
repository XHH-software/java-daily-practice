package com.huaihan.service.impl;

import com.huaihan.mapper.EmpMapper;
import com.huaihan.mapper.StudentMapper;
import com.huaihan.pojo.ClazzOption;
import com.huaihan.pojo.JobOption;
import com.huaihan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 统计员工职位人数
     */
    @Override
    public JobOption getEmpJobData() {
        // 调用mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();

        // 组装结果，并返回
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList, dataList);
    }

    /**
     * 统计员工性别
     */
    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    /**
     * 员工学历统计
     */
    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.getStudentDegreeData();
    }

    /**
     * 统计班级人数
     */
    @Override
    public ClazzOption getStudentCountData() {
        List<Map<String, Object>> studentCountData = studentMapper.getStudentCountData();
        /*
            - `dataMap`：循环里面每一行 map 对象
            - `dataMap.get("clazzName")`：取出 map 中 key 为`clazzName`的值（班级名字符串）
            - `.toList()`：把提取出来的所有班级名称，收集成新 List
        */
        List<Object> clazzName = studentCountData.stream().map(dataMap -> dataMap.get("clazzName")).toList();
        List<Object> stuNum = studentCountData.stream().map(dataMap -> dataMap.get("stuNum")).toList();
        return new ClazzOption(clazzName,stuNum);
    }

}
