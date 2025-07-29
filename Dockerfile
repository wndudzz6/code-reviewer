# Java 17 기반 슬림 이미지 사용
FROM openjdk:17-jdk-slim

# 앱 실행 디렉토리 설정
WORKDIR /app

# Gradle 캐시 및 전체 소스 복사
COPY . .

#실행권한부여
RUN chmod +x ./gradlew

# Gradle로 빌드 (테스트 생략)
RUN ./gradlew build -x test

#포트
EXPOSE 8080

# JAR 실행
CMD ["java", "-Dspring.profiles.active=postgres", "-jar", "build/libs/code-reviewer.jar"]

