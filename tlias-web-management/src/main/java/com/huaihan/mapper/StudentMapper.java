package com.huaihan.mapper;

import com.huaihan.pojo.PageResult;
import com.huaihan.pojo.Student;
import com.huaihan.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    /**
     * 用于学员列表数据的条件分页查询
     */
    List<Student> pageFindAllStudent(StudentQueryParam studentQueryParam);

    /**
     * 根据id删除学员
     */
    void deleteStudent(List<Integer> ids);

    /**
     * 添加学员
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addStudent(Student student);

    /**
     * 数据回显，根据ID查询学员
     */
    Student findStudentById(Integer id);

    /**
     * 修改学员
     */
    void updateStudent(Student student);

    /**
     * 违纪处理，违纪次数+1
     * @param id 学员ID
     */
    @Update("UPDATE student SET violation_count = violation_count + 1 WHERE id = #{id}")
    void updateStudentViolationCount(Integer id);

    /**
     * 违纪处理,违纪分数累加
     * @param id 学员ID
     * @param score 扣除分数
     */
    @Update("UPDATE student SET violation_score = violation_score + #{score} WHERE id = #{id};")
    void updateStudentViolationScore(Integer id, Integer score);

    /**
     * 统计学员学历
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计班级人数
     */
    List<Map<String, Object>> getStudentCountData();

}
