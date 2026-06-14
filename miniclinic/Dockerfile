# 第一階段：使用 Maven 進行專案編譯與打包
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# 因為 Render 的 Root Directory 已經切換到內層的 miniclinic 了
# 這裡直接複製當前目錄下的 pom.xml 與 src 資料夾即可
COPY pom.xml ./
COPY src ./src

# 執行 Maven 打包，跳過測試以加速部署
RUN mvn clean package -DskipTests

# 第二階段：使用輕量級的 JRE 執行環境來運行程式
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 從第一階段複製打包好的 jar 檔過來
COPY --from=build /app/target/*.jar app.jar

# 暴露 Spring Boot 預設的 8080 埠口
EXPOSE 8080

# 啟動指令
ENTRYPOINT ["java", "-jar", "app.jar"]
