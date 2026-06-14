# MiniClinic 診所預約與統計管理系統

MiniClinic 是一個基於 Spring Boot 的微型診所後端管理系統，支援掛號、病患與醫師管理，以及即時統計 API，已部署於 Render 平台供展示。

## 雲端展示
- 系統部署 URL: https://miniclinic-1rlj.onrender.com
- 統計 API: https://miniclinic-1rlj.onrender.com/api/stats

## 功能重點
- 醫師管理 (Doctor Service)
- 病患管理 (Patient Service)
- 掛號預約 (Appointment Service)
- 統計 API (Stats Service)

## 技術棧
- Java 17 / Spring Boot 3.x
- Spring Data JPA / Hibernate
- H2 (in-memory, PostgreSQL 模式) for production demo
- BCrypt Password Hash
- Docker (multi-stage build)

## 雲端資料庫初始化
- 使用 `schema-prod.sql`（位於 `src/main/resources/`）做 DDL + 初始資料插入。
- `application-prod.properties` 已設定為只讀 `classpath:schema-prod.sql`，並使用 H2 記憶體資料庫，保證雲端獨立運行。

## Dockerfile (multi-stage)
```dockerfile
# 第一階段：Maven 編譯與打包
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

# 第二階段：JRE 輕量化執行環境
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 在本機或 CI 上建置與執行
```bash
# 在專案根目錄（含 pom.xml）執行：
mvn clean package -DskipTests
java -jar target/*.jar
```

## Git 提交建議
```bash
git add README.md
git commit -m "Add README: deployment and API details"
git push origin main
```

## 注意事項
- `README.md` 必須放在與 `pom.xml`、`src/` 同一層級的專案根目錄。請確認 Render 的 Root Directory 指向該目錄。
- 若要改為連接外部 PostgreSQL，請在 `pom.xml` 加入 `org.postgresql:postgresql` 依賴並更新 `application-prod.properties`。

---

需要我幫你執行 `git add` / `commit` / `push` 嗎？