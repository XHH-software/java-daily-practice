package com.huaihan.controller;

import com.huaihan.anno.Log;
import com.huaihan.pojo.Dept;
import com.huaihan.pojo.Result;
import com.huaihan.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping(("/depts"))
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门
     */
    // @RequestMapping(value = "/depts",method = RequestMethod.GET)
    // 简化
    @GetMapping
    public Result list(){
        // System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 根据ID删除部门
     */
    // 方式一：HttpServletRequest request 获取请求参数
    /*
    @DeleteMapping("/depts")
    public Result delete(HttpServletRequest request){
        String s = request.getParameter("id");
        int deleteId = Integer.parseInt(s);
        System.out.println("根据"+ deleteId +"删除部门数据");
        deptService.deleteId(deleteId);
        return Result.success();
    }
*/

    // 方式二：请求时必须传递参数，如不想传递，required设置为false，默认为true
    /*
    @DeleteMapping("/depts")
    public Result delete(@RequestParam(value = "id",required = false) Integer deptId){
        System.out.println("根据"+ deptId +"删除部门数据");
        deptService.deleteId(deptId);
        return Result.success();
    }
*/

    // 简化方式二：前提前端服务器传递的请求参数与服务器端的方法形参名一致
    @Log
    @DeleteMapping
    public Result delete(Integer id){
        // System.out.println("根据"+ id +"删除部门数据");
        log.info("根据{}删除部门数据",id);
        deptService.deleteId(id);
        return Result.success();
    }

    /**
     * 增加部门
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        // System.out.println("增加部门数据：" + dept);
        log.info("增加部门数据：{}", dept);
        deptService.addDept(dept);
        return Result.success();
    }

    /**
     * 修改部门
     * 查询回显
     */
    @GetMapping("/{id}")
    // public Result get(@PathVariable("id") Integer deptId)
    // 简化
    public Result getInfo(@PathVariable Integer id){
        // System.out.println("根据ID查询部门:"+id);
        log.info("根据ID查询部门:{}",id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     * 更新数据
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        // System.out.println("修改部门数据：" + dept);
        log.info("修改部门数据:{}",dept);
        deptService.resetDept(dept);
        return Result.success();
    }
}
