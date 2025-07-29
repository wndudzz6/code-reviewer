# 1. Java 17 기반 슬림 이미지 사용
FROM openjdk:17-jdk-slim

# 2. 앱 실행 디렉토리 설정
WORKDIR /app

# 3. Gradle 캐시 및 전체 소스 복사
COPY . .

# 4. Gradle로 빌드 (테스트 생략)
RUN ./gradlew build -x test

# 5. JAR 실행
CMD ["java", "-jar", "build/libs/code-reviewer.jar"]
