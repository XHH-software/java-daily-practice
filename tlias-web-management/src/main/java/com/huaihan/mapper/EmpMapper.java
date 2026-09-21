package com.huaihan.mapper;

import com.huaihan.pojo.Emp;
import com.huaihan.pojo.EmpQueryParam;
import com.huaihan.pojo.LoginInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {

    // -----------------------------------原始查询的分页方式-----------------------------------
/*
    // @Select("SELECT COUNT(*) FROM emp e left join dept d ON e.dept_id = d.id")
    // public  Long count();


    // @Select("SELECT e.*,d.name deptName FROM emp e left join dept d ON e.dept_id = d.id ORDER BY e.update_time DESC LIMIT #{start},#{pageSize}")
    // public  List<Emp> list(Integer start, Integer pageSize);
*/
    // -----------------------------------使用PageHelper插件的分页方式，使用普通的查询语句，不考虑分页操作-----------------------------------
/*
    // @Select("SELECT e.*,d.name deptName FROM emp e left join dept d ON e.dept_id = d.id ORDER BY e.update_time DESC")
    // public  List<Emp> list();


    // @Select("SELECT e.*,d.name FROM emp e left join dept d on e.dept_id = d.id WHERE e.name like '%#{name}%' and e.gender = #{gender} and e.entry_date BETWEEN '#{begin}' AND '#{end}' ORDER BY update_time DESC")

    // public  List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
*/
    /**
     * 条件查询员工信息
     */
    List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     * @param emp
     */
    @Options(useGeneratedKeys = true,keyProperty = "id") // 主键返回，在插入数据后，获取到生成的主键
    @Insert("INSERT INTO emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "VALUES (#{username},#{password},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime});")
    void insert(Emp emp);

    /**
     * 根据员工ID删除员工基本信息
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 修改员工，数据回显，根据ID查询员工
     */
    Emp getById(Integer id);

    /**
     * 根据ID修改员工的基本信息
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     */
    List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别
     */
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 查询岗位job=1的班主任，只查询id和name
     */
    @Select("select id,name from emp where job = 1")
    List<Emp> listTeacher();

    /**
     * 登录
     */
    @Select("SELECT id,username,password,name From emp WHERE username = #{username} AND password = #{password}")
    LoginInfo findByUserNameAndPassword(Emp emp);
}
