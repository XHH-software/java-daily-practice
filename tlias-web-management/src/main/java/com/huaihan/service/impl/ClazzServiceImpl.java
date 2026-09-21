package com.huaihan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaihan.exception.BusinessException;
import com.huaihan.mapper.ClazzMapper;
import com.huaihan.pojo.Clazz;
import com.huaihan.pojo.ClazzQueryParam;
import com.huaihan.pojo.PageResult;
import com.huaihan.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    /**
     * 分页查询，班级列表查询
     */
    @Override
    public PageResult<Clazz> pageFindAll(ClazzQueryParam clazzQueryParam) {
        // 1. 设置分页参数
        PageHelper.startPage(clazzQueryParam.getPage(),clazzQueryParam.getPageSize());
        // 2. 调用Mapper层,返回查询结果，封装成List列表类型
        List<Clazz> clazzList = clazzMapper.PageResult(clazzQueryParam);
        // 3. 解析查询结果，封装成Page类型(将List类型强转为Page)
        Page<Clazz> clazzPage = (Page<Clazz>) clazzList;
        return new PageResult<Clazz>(clazzPage.getTotal(),clazzPage.getResult());
    }

    /**
     * 根据id删除班级
     */
    @Override
    public void deleteClazz(Integer id) {
        // 查询该班级关联学生数量
        Long studentCount = clazzMapper.countStudentByClazzId(id);
        if(studentCount > 0){
            // 抛出自定义业务异常，全局异常处理器捕获返回提示
            throw new BusinessException("对不起, 该班级下有学生, 不能直接删除");
        }
        clazzMapper.deleteClazz(id);
    }

    /**
     * 添加班级
     */
    @Override
    public void addClazz(Clazz clazz) {
        // 1. 补全基本属性
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        // 2. 调用Mapper接口
        clazzMapper.addClazz(clazz);
    }

    /**
     * 修改班级，数据回显，根据id查询班级
     */
    @Override
    public Clazz findClazzById(Integer id) {
        return clazzMapper.findClazzById(id);
    }

    /**
     * 修改班级，更新数据
     */
    @Override
    public void updateClazz(Clazz clazz) {
        // 1. 补全基础数据
        clazz.setUpdateTime(LocalDateTime.now());
        // 2. 调用Mapper层
        clazzMapper.updateClazz(clazz);
    }

    /**
     * 查询所有班级信息
     */
    @Override
    public List<Clazz> findAllClazz() {
        return clazzMapper.findAllClazz();
    }
}
