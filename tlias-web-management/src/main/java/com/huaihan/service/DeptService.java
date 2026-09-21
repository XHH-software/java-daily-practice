package com.huaihan.service;

import com.huaihan.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     */
    List<Dept> findAll();

    /**
     * 根据ID删除部门
     */
    void deleteId(Integer id);

    /**
     * 增加部门
     */
    void addDept(Dept dept);

    /**
     * 修改部门
     * 查询回显
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     * 更新数据
     */
    void resetDept(Dept dept);
}
