# 使用官方 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-alpine

# 创建一个工作目录
WORKDIR /app

# 将编译好的 JAR 文件复制到容器中
COPY target/Magnet-0.0.1-SNAPSHOT.jar app.jar

# 声明应用程序使用的端口
EXPOSE 8080

# 设置默认的启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]

