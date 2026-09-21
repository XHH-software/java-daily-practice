package com.huaihan.utils;

public class UserContext {
    private static ThreadLocal<Integer> empId = new ThreadLocal<>();

    public static void setEmpId(Integer id) {
        empId.set(id);
    }

    public static Integer getEmpId() {
        return empId.get();
    }

    public static void remove() {
        empId.remove();
    }
}
