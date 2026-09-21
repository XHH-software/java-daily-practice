package com.huaihan.service.impl;

import com.huaihan.mapper.DeptMapper;
import com.huaihan.pojo.Dept;
import com.huaihan.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有部门
     */
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    /**
     * 根据ID删除部门
     */
    @Override
    public void deleteId(Integer id) {
        deptMapper.deleteId(id);
    }

    /**
     * 增加部门
     */
    @Override
    public void addDept(Dept dept) {
        // 补全基础属性- createTime,updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        // 调用Mapper接口方法插入数据
        deptMapper.add(dept);
    }

    /**
     * 修改部门
     * 查询回显
     */
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    /**
     * 修改部门
     * 更新数据
     */
    @Override
    public void resetDept(Dept dept) {
        // 补全基础属性- updateTime
        dept.setUpdateTime(LocalDateTime.now());
        // 调用Mapper接口方法插入数据
        deptMapper.resetDept(dept);
    }


}
