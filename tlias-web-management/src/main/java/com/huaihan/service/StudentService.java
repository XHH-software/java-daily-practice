package com.huaihan.service;

import com.huaihan.pojo.PageResult;
import com.huaihan.pojo.Student;
import com.huaihan.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {

    /**
     * 用于学员列表数据的条件分页查询
     */
    PageResult<Student> pageFindAllStudent(StudentQueryParam studentQueryParam);

    /**
     * 根据id删除学员
     */
    void deleteStudent(List<Integer> ids);

    /**
     * 添加学员
     */
    void addStudent(Student student);

    /**
     * 数据回显，根据ID查询学员
     */
    Student findAtudentById(Integer id);

    /**
     * 修改学员
     */
    void updateStudent(Student student);

    /**
     * 违纪处理
     * @param id 学员ID
     * @param score 扣除分数
     */
    void updateStudentViolation(Integer id, Integer score);
}
