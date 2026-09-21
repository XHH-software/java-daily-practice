package com.huaihan.controller;

import com.huaihan.anno.Log;
import com.huaihan.pojo.Clazz;
import com.huaihan.pojo.ClazzQueryParam;
import com.huaihan.pojo.PageResult;
import com.huaihan.pojo.Result;
import com.huaihan.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    /**
     * 分页查询，班级列表查询
     */
    @GetMapping
    public Result pageFindAll(ClazzQueryParam clazzQueryParam){
        log.info("【分页查询：{}】",clazzQueryParam);
        // 调用Service层，用PageResult分页结果封装类封装
        PageResult<Clazz> clazzPageResult = clazzService.pageFindAll(clazzQueryParam);
        return Result.success(clazzPageResult);
    }

    /**
     * 根据id删除班级
     */
    @Log
    @DeleteMapping("/{id}")
    public Result deleteClazz(@PathVariable Integer id){
        log.info("根据id【{}】删除班级",id);
        clazzService.deleteClazz(id);
        return Result.success();
    }

    /**
     * 添加班级
     */
    @Log
    @PostMapping
    public Result addClazz(@RequestBody Clazz clazz){
        log.info("添加班级：【{}】",clazz);
        clazzService.addClazz(clazz);
        return Result.success();
    }

    /**
     * 修改班级，数据回显，根据id查询班级
     */
    @GetMapping("/{id}")
    public Result findClazzById(@PathVariable Integer id){
        log.info("根据id【{}】查询班级",id);
        // 调用Service层，返回数据用Clazz封装
        Clazz clazz = clazzService.findClazzById(id);
        return  Result.success(clazz);
    }

    /**
     * 修改班级，更新数据
     */
    @Log
    @PutMapping
    public Result updateClazz(@RequestBody Clazz clazz){
        log.info("修改部门数据【{}】",clazz);
        clazzService.updateClazz(clazz);
        return Result.success();
    }

    /**
     * 查询所有班级信息
     */
    @GetMapping("/list")
    public Result findAllClazz(){
        log.info("查询所有班级信息：");
        List<Clazz> allClazz = clazzService.findAllClazz();
        return Result.success(allClazz);
    }
}
