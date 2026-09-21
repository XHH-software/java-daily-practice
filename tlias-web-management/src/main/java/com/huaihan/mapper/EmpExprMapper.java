package com.huaihan.mapper;

import com.huaihan.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工工作经历,是员工信息发附属信息
 */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量添加员工经历
     * @param exprList
     */
    void insertBeach(List<EmpExpr> exprList);

    /**
     * 根据员工ID删除员工的工作经历信息
     * @param empIds
     */
    void deleteByEmpIds(List<Integer> empIds);
}
