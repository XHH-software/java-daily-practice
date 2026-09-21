package com.huaihan.mapper;

import com.huaihan.pojo.Clazz;
import com.huaihan.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClazzMapper {

    /**
     * 分页查询，班级列表查询
     */
    List<Clazz> PageResult(ClazzQueryParam clazzQueryParam);

    /**
     * 根据id删除班级
     */
    @Delete("DELETE  FROM clazz WHERE id = #{id}")
    void deleteClazz(Integer id);

    /**
     * 添加班级
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time)" +
            "values (#{name},#{room},#{beginDate},#{endDate},#{masterId},#{subject},#{createTime},#{updateTime})")
    void addClazz(Clazz clazz);

    /**
     * 修改班级，数据回显，根据id查询班级
     */
    @Select("SELECT id, name, room, begin_date, end_date, master_id,subject, create_time, update_time FROM clazz WHERE id = #{id};")
    Clazz findClazzById(Integer id);

    /**
     * 修改班级，更新数据
     */
    void updateClazz(Clazz clazz);

    /**
     * 查询所有班级信息
     */
    @Select("SELECT id, name, room, begin_date, end_date, master_id, subject, create_time, update_time FROM clazz")
    List<Clazz> findAllClazz();

    /**
     * 根据班级id统计关联学生数量
     */
    @Select("select count(*) from student where clazz_id = #{clazzId}")
    Long countStudentByClazzId(Integer clazzId);
}
