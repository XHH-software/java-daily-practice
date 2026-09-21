package com.huaihan.controller;

import com.huaihan.pojo.ClazzOption;
import com.huaihan.pojo.JobOption;
import com.huaihan.pojo.Result;
import com.huaihan.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 统计员工职位人数
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计员工性别
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别");
        List<Map<String,Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    /**
     * 统计学员学历
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学员学历");
        List<Map<String,Object>> degreeDataList = reportService.getStudentDegreeData();
        return Result.success(degreeDataList);
    }

    /**
     * 统计班级人数
     */
    @GetMapping("studentCountData")
    public Result getStudentCountData(){
        log.info("统计班级人数");
        ClazzOption clazzOption = reportService.getStudentCountData();
        return Result.success(clazzOption);
    }
}
