package com.huaihan.controller;

import com.huaihan.anno.Log;
import com.huaihan.pojo.Emp;
import com.huaihan.pojo.EmpQueryParam;
import com.huaihan.pojo.PageResult;
import com.huaihan.pojo.Result;
import com.huaihan.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 分页查询，原始的分页查询
     */
    /*@GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("【分页查询页码：{}。每页记录数：{}。】",page,pageSize);
        PageResult<Emp> pageResult =  empService.page(page,pageSize);
        return Result.success(pageResult);
    }*/
    /*@GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,
                       Integer gender,
                       @DateTimeFormat(pattern = "yyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyy-MM-dd") LocalDate end){
        log.info("【分页查询页码：{}。每页记录数：{}。姓名：{}。性别：{}。范围匹配的开始时间：{}。范围匹配的结束时间：{}。】",page,pageSize,name,gender,begin,end);
        PageResult<Emp> pageResult =  empService.page(page,pageSize,name,gender,begin,end);
        return Result.success(pageResult);
    }*/
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("【分页查询日志：{}。】",empQueryParam);
        PageResult<Emp> pageResult =  empService.page(empQueryParam);
        return Result.success(pageResult);
    }
    /**
     * 新增员工
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("【新增员工日志：{}。】",emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * 删除员工
     */
    @Log
    @DeleteMapping
    /*public Result delete(Integer[] ids){
        log.info("【删除员工的ID：{}。】", Arrays.toString(ids));
        return Result .success();
    }*/
    // 使用复杂类型接受要加@RequestParam注解
    public Result delete(@RequestParam List<Integer> ids){
        log.info("【删除员工的ID：{}。】", ids);
        empService.delete(ids);
        return Result .success();
    }

    /**
     * 修改员工，数据回显
     */
    @GetMapping("/{id}")
    // @PathVariable接受路径参数
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询的员工信息:{}",id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 修改员工
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改的员工数据:{}",emp);
        empService.update(emp);
        return Result.success();
    }

    /**
     * 查询所有班主任（job=1），用于班级新增页面下拉框
     */
    @GetMapping("/list")
    public Result listTeacher(){
        log.info("查询所有班主任（job=1）");
        List<Emp> teacherList = empService.listTeacher();
        return Result.success(teacherList);
    }
}
