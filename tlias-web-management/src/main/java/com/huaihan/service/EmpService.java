package com.huaihan.service;

import com.huaihan.pojo.Emp;
import com.huaihan.pojo.EmpQueryParam;
import com.huaihan.pojo.LoginInfo;
import com.huaihan.pojo.PageResult;

import java.util.List;

public interface EmpService {

    /**
     * 查询员工
     */

    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     */
    void save(Emp emp);

    /**
     * 删除员工
     */
    void delete(List<Integer> ids);

    /**
     * 修改员工，数据回显，根据ID查询员工
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工
     */
    void update(Emp emp);

    /**
     * 查询所有班主任（job=1），用于班级新增页面下拉框
     */
    List<Emp> listTeacher();

    /**
     * 登录
     */
    LoginInfo login(Emp emp);
}
