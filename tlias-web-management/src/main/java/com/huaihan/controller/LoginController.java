package com.huaihan.controller;

import com.huaihan.pojo.Emp;
import com.huaihan.pojo.LoginInfo;
import com.huaihan.pojo.Result;
import com.huaihan.service.EmpService;
import com.huaihan.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    @PostMapping
    public Result login(@RequestBody Emp emp){
        log.info("登录");
        LoginInfo  loginInfo = empService.login(emp);
        if (loginInfo!=null) {
            // ========== 生成token时，claims必须put empId ==========
            Map<String,Object> claims = new HashMap<>();//loginInfo.getEmp().getId()
            claims.put("empId", loginInfo.getId());
            String token = JwtUtils.generateToken(claims);
            loginInfo.setToken(token);
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }

}
