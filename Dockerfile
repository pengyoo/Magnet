FROM maven:3.8.4-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

<<<<<<< HEAD
# 声明应用程序使用的端口
EXPOSE 8080

# 设置默认的启动命令
=======
# Build the application with verbose output and list target directory
RUN mvn clean package -DskipTests -X && \
    echo "Maven build completed. Listing target directory:" && \
    ls -l target && \
    echo "Listing all JAR files in target directory:" && \
    find target -name "*.jar"

# Use OpenJDK for running the application
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy all JAR files from build stage and list the contents
COPY --from=build /app/target/*.jar ./app.jar
RUN echo "Listing JAR files in app directory:" && \
    ls -l *.jar
EXPOSE 8080

# Run the application (adjust the JAR file name if necessary)
>>>>>>> 90351d9 (modify dockerfile for caprover)
ENTRYPOINT ["java", "-jar", "app.jar"]

