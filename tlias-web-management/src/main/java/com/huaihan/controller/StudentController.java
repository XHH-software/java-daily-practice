package com.huaihan.controller;

import com.huaihan.anno.Log;
import com.huaihan.pojo.PageResult;
import com.huaihan.pojo.Result;
import com.huaihan.pojo.Student;
import com.huaihan.pojo.StudentQueryParam;
import com.huaihan.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 用于学员列表数据的条件分页查询
     */
    @GetMapping
    public Result pageFindAllStudent(StudentQueryParam studentQueryParam){
        log.info("学员列表数据分页查询:{}",studentQueryParam);
        PageResult<Student> studentPageResult = studentService.pageFindAllStudent(studentQueryParam);
        return Result.success(studentPageResult);
    }

    /**
     * 根据id删除学员
     */
    // 路径参数用@PathVariable接收
    @Log
    @DeleteMapping("{ids}")
    public Result deleteStudent(@PathVariable List<Integer> ids){
        log.info("根据id【{}】删除学员",ids);
        studentService.deleteStudent(ids);
        return Result.success();
    }

    /**
     * 添加学员
     */
    @Log
    @PostMapping
    public Result addStudent(@RequestBody Student student){
        log.info("添加学员信息【{}】",student);
        studentService.addStudent(student);
        return Result.success();
    }

    /**
     * 数据回显，根据ID查询学员
     */
    @GetMapping("/{id}")
    public Result findStudentById(@PathVariable Integer id){
        log.info("数据回显，根据ID【{}】查询学员",id);
        Student student = studentService.findAtudentById(id);
        return Result.success(student);
    }

    /**
     * 修改学员
     */
    @Log
    @PutMapping
    public Result updateStudent(@RequestBody Student student){
        log.info("修改的学员数据:{}",student);
        studentService.updateStudent(student);
        return Result.success();
    }

    /**
     * 违纪处理
     * @param id 学员ID
     * @param score 扣除分数
     */
    @Log
    @PutMapping("/violation/{id}/{score}")
    public Result updateStudentViolation(@PathVariable Integer id,@PathVariable Integer score){
        log.info("学员ID：{}，扣除分数：{}",id,score);
        studentService.updateStudentViolation(id,score);
        return Result.success();
    }
}
