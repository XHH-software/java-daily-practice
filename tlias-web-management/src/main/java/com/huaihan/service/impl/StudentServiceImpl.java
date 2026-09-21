package com.huaihan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaihan.mapper.StudentMapper;
import com.huaihan.pojo.PageResult;
import com.huaihan.pojo.Student;
import com.huaihan.pojo.StudentQueryParam;
import com.huaihan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 用于学员列表数据的条件分页查询
     */
    @Override
    public PageResult<Student> pageFindAllStudent(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        List<Student> studentList = studentMapper.pageFindAllStudent(studentQueryParam);
        Page<Student> studentPage = (Page<Student>) studentList;
        return new PageResult<Student>(studentPage.getTotal(),studentPage.getResult());
    }

    /**
     * 根据id删除学员
     */
    @Override
    public void deleteStudent(List<Integer> ids) {
        studentMapper.deleteStudent(ids);
    }

    /**
     * 添加学员
     */
    @Override
    public void addStudent(Student student) {
        // 1.补全基础属性
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        // 2.调用Mapper
        studentMapper.addStudent(student);
    }

    /**
     * 数据回显，根据ID查询学员
     */
    @Override
    public Student findAtudentById(Integer id) {
        return studentMapper.findStudentById(id);
    }

    /**
     * 修改学员
     */
    @Override
    public void updateStudent(Student student) {
        // 补全基本属性
        student.setUpdateTime(LocalDateTime.now());
        // 调用Mapper
        studentMapper.updateStudent(student);
    }

    /**
     * 违纪处理
     * @param id 学员ID
     * @param score 扣除分数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStudentViolation(Integer id, Integer score) {
        // 违纪次数 + 1
        studentMapper.updateStudentViolationCount(id);
        // 扣除分数累加
        studentMapper.updateStudentViolationScore(id,score);
    }
}
