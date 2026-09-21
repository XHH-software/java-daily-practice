# java-daily-practice

> 个人Java学习项目仓库，存放JavaWeb实战项目，基于SpringBoot生态开发，支持Docker容器一键部署，整合前端静态资源。

## 📁 项目列表

### tlias-web‑management 员工管理系统

#### ✨ 项目简介

Tlias是一套校园/企业员工信息管理后台，实现员工、部门、班级、学生信息维护，集成JWT登录认证、AOP操作日志、阿里云OSS文件上传、MyBatis数据持久化。
前端静态页面由Nginx托管，配置反向代理转发接口请求到后端，整体支持 Docker Compose 容器化部署。

#### 🛠️ 技术栈

**后端**

- 后端框架：SpringBoot 3.x
- ORM框架：MyBatis
- 数据库：MySQL 8.0
- 认证：JWT令牌 + 拦截器
- 切面：AOP（自动记录操作日志）
- 文件存储：阿里云OSS
- 部署：Docker + Docker Compose
  **前端&服务代理**
- 静态页面：打包后的前端页面
- Web代理服务器：Nginx 1.22.0
- Nginx实现静态资源托管、`/api`接口反向代理转发至SpringBoot后端
  
  #### 📌 核心功能
1. 用户登录：JWT生成令牌，服务端校验Token，未登录拦截
2. 部门管理：部门新增、查询、编辑、删除
3. 员工管理：员工分页查询、新增、修改、删除
4. 班级管理：班级CRUD，关联学生信息
5. 学生管理：学生信息维护
6. AOP日志：自动捕获接口操作，存入数据库，记录操作人、操作时间、操作内容
7. 文件上传：图片上传至阿里云OSS对象存储
8. 全局异常处理器：统一捕获异常，返回友好提示
9. 分页查询、数据统计报表

#### 📂 项目结构

```
java‑daily‑practice
├── tlias‑web‑management          # SpringBoot后端源码
│   ├── src/main/java/com/huanhan # Java业务代码
│   ├── src/main/resources        # yml配置、mapper映射文件
│   ├── Dockerfile                # SpringBoot镜像构建脚本
│   ├── docker‑compose.yml        # mysql+springboot容器编排
│   ├── pom.xml                   # Maven依赖管理
│   └── target                    # mvn打包输出jar包
└── nginx‑1.22.0‑web              # 前端静态页面 + Nginx部署文件
```

## 📝 个人总结

本项目是 SpringBoot 综合实战项目，练习分层开发、AOP 切面编程、JWT 身份认证、阿里云 OSS 文件存储、项目打包以及 Docker 容器化部署，熟悉前后端分离后端接口开发与线上部署流程。