package com.huaihan.service;

import com.huaihan.pojo.Clazz;
import com.huaihan.pojo.ClazzQueryParam;
import com.huaihan.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    /**
     * 分页查询，班级列表查询
     */
    PageResult<Clazz> pageFindAll(ClazzQueryParam clazzQueryParam);

    /**
     * 根据id删除班级
     */
    void deleteClazz(Integer id);

    /**
     * 添加班级
     */
    void addClazz(Clazz clazz);

    /**
     * 修改班级，数据回显，根据id查询班级
     */
    Clazz findClazzById(Integer id);

    /**
     * 修改班级，更新数据
     */
    void updateClazz(Clazz clazz);

    /**
     * 查询所有班级信息
     */
    List<Clazz> findAllClazz();



}
