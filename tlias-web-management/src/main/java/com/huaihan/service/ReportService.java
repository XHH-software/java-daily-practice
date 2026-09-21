package com.huaihan.service;

import com.huaihan.pojo.ClazzOption;
import com.huaihan.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计员工职位人数
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 学员学历统计
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计班级人数
     */
    ClazzOption getStudentCountData();
}
