package com.huaihan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaihan.mapper.EmpExprMapper;
import com.huaihan.mapper.EmpMapper;
import com.huaihan.pojo.*;
import com.huaihan.service.EmpLogService;
import com.huaihan.service.EmpService;
import com.huaihan.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    /**
     * 分页查询，原始的分页查询
     */
    /*
    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        // 调用mapper接口，查询总记录数
        Long total = empMapper.count();
        // 计算每页起始页码
        Integer start = (page - 1) * pageSize;
        // 调用mapper接口，查询结果列表
        List<Emp> rows = empMapper.list(start, pageSize);
        // 封装结果 PageResult
        return new PageResult<Emp>(total,rows);
    }
     */
    /**
     * 分页查询,使用PageHelper插件
     */
    /*
    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        // 1.设置分页参数(PageHelper)
        PageHelper.startPage(page,pageSize);
        // 2.执行查询
        List<Emp> empList = empMapper.list();
        // 3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }
*/
    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
        // 1.设置分页参数(PageHelper)
        PageHelper.startPage(page,pageSize);
        // 2.执行查询
        List<Emp> empList = empMapper.list(name, gender, begin, end);
        // 3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }*/
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 1.设置分页参数(PageHelper)
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        // 2.执行查询
        List<Emp> empList = empMapper.list(empQueryParam);
        // 3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }

    /**
     * 新增员工
     */
    @Transactional(rollbackFor = {Exception.class}) // 事务管理默认出现运行时异常RuntimeException才会回滚
    @Override
    public void save(Emp emp) {
        try {
            // 1. 补全基础属性
            emp.setCreateTime(LocalDateTime.now());// 创建时间
            emp.setUpdateTime(LocalDateTime.now());// 修改时间

            // 保存员工基本信息
            empMapper.insert(emp);

            // 2. 保存员工工作经历信息
            List<EmpExpr> exprList = emp.getExprList();

            // CollectionUtils.isEmpty() 判断集合是否为空
            if (!CollectionUtils.isEmpty(exprList)) {
                // 遍历集合，为empId赋值
                exprList.forEach(e -> {
                    e.setEmpId(emp.getId());
                });
                empExprMapper.insertBeach(exprList);
            }
        } finally {
            // 在新增员工信息时，无论是成功还是失败，都要记录操作日志。
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"新增员工"+emp);
            empLogService.insertLog(empLog);
        }

    }

    /**
     * 删除员工
     * @param ids
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //1.批量删除员工基本信息
        empMapper.deleteByIds(ids);

        //2.批量删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);

    }

    /**
     * 修改员工，数据回显，根据ID查询员工
     */
    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    /**
     * 修改员工
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        // 1.根据ID修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 2.根据ID修改员工的工作经历信息
        // 2.1 先根据员工ID删除原有的工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        // 2.2 再添加这个员工新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(e -> {
                e.setEmpId(emp.getId());
            });
            empExprMapper.insertBeach(exprList);
        }
    }

    @Override
    public List<Emp> listTeacher() {
        return empMapper.listTeacher();
    }

    /**
     * 登录
     */
    @Override
    public LoginInfo login(Emp emp) {
        // 1. 调用Mapper，根据用户名和密码查询员工信息
        LoginInfo loginInfo = empMapper.findByUserNameAndPassword(emp);

        // 生成令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",emp.getId());
        claims.put("username",emp.getUsername());
        String token = JwtUtils.generateToken(claims);

        // 2. 判断用户是否存在，存在则拼接
        if(loginInfo!=null){
            return new LoginInfo(loginInfo.getId(),loginInfo.getUsername(),loginInfo.getName(),token);
        }
        // 2. 不存在
        return null;
    }
}
