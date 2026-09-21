# 基础镜像，JDK17（如果你的项目是JDK8就换成 openjdk:8-jre）
FROM openjdk:17-jre-slim

# 设置工作目录
WORKDIR /app

# 复制jar包到容器内
COPY target/tlias-web-management-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口，和项目yml的server.port保持一致
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java","-jar","app.jar"]
