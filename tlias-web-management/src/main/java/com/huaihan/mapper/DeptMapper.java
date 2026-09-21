package com.huaihan.mapper;

import com.huaihan.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    // 方式一：手动结果映射——使用@Results - @Result 手动封装，解决实体类属性值和数据库字段名不一致而无法自动封装的问题
    /*
    @Results(
            {@Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime"),
    })
     */

    // 方法二：起别名
    // @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc;")


    /**
     * 查询所有部门
     */
    @Select("SELECT id, name, create_time, update_time FROM dept ORDER BY update_time DESC;")
    List<Dept> findAll();

    /**
     * 根据ID删除部门
     */
    @Delete("DELETE FROM dept WHERE id = #{id};")
    void deleteId(Integer id);

    /**
     * 增加部门
     */
    @Insert("INSERT INTO dept(name, create_time, update_time) VALUES(#{name},#{createTime},#{updateTime});")
    void add(Dept dept);

    /**
     * 修改部门
     * 查询回显
     */
    @Select("SELECT id, name, create_time, update_time FROM dept WHERE id = #{id};")
    Dept getById(Integer id);

    /**
     * 修改部门
     * 更新数据
     */
    @Update("UPDATE dept SET name = #{name},update_time = #{updateTime} WHERE id = #{id};")
    void resetDept(Dept dept);
}
