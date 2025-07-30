# 💻 Code Reviewer Backend

GPT를 활용한 자동 코드 리뷰 시스템의 백엔드입니다.  
코드 제출 시 AI가 자동으로 코드 리뷰를 생성하고, 결과를 Excel 또는 Notion 형식으로 내보낼 수 있습니다.

> 🟢 현재 Render를 통해 실제 배포되어 있습니다:  
> https://code-reviewer-8ecw.onrender.com

---

## 🚀 주요 기능

### ✅ GPT 기반 자동 리뷰 생성
- 제출된 코드에 대해 GPT가 코드 퀄리티, 시간복잡도, 난이도 등을 자동 평가

### ✅ 사용자 인증 (JWT)
- 로그인/회원가입 구현
- JWT 토큰 기반 인증 시스템 구축

### ⚙️ 환경별 Redis 적용

로컬 개발 환경에서는 Redis를 적용해 GPT 응답을 캐싱함으로써 요청 중복 시 비용을 줄이도록 구성했습니다.

다만, 현재 Render 무료 배포 환경에서는 Redis 서버 운영이 제한되므로,  
**Spring 프로파일(`@Profile`) 기반으로 Redis 관련 빈을 분리**하여 Render 배포 시에는 자동으로 Redis 기능이 제외되도록 설정했습니다.

향후 다른 플랫폼(AWS, GCP 등)으로 배포할 경우,  
Redis 설정을 위한 프로파일과 `application-redis.properties`를 추가하여  
**기존 Redis 구현체를 그대로 재사용할 수 있는 구조**로 설계되어 있습니다.


### ✅ 코드 리뷰 결과 Export
- Excel 파일로 다운로드 가능
- (선택) Notion 연동도 고려된 구조 설계
- 
### ✅ Swagger를 통한 API 문서화
Swagger UI를 통해 API 테스트 및 문서 확인 가능
→ /swagger-ui/index.html



---

## 🛠️ 사용 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Auth | Spring Security + JWT |
| AI Integration | OpenAI GPT-3.5 API |
| DB | PostgreSQL(배포), H2(테스트용) |
| Cache | Redis(로컬환경적용) |
| Deployment | Render, Docker |
| Docs & Test | Swagger, Postman |

---

## 주요 API

### 인증

#### 회원가입  
`POST /api/users/register`

```json
{
  "email": "test@example.com",
  "password": "1234"
}
 ```

### 코드 제출 예시
**요청 헤더:**
Authorization: Bearer <JWT_TOKEN>  
Content-Type: application/json

**요청 예시(0/1 Knapsack문제 - Java)**:
{
  "code": "import java.util.*;\n\npublic class Knapsack {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int n = sc.nextInt();\n        int k = sc.nextInt();\n        int[] w = new int[n+1];\n        int[] v = new int[n+1];\n\n        for (int i = 1; i <= n; i++) {\n            w[i] = sc.nextInt();\n            v[i] = sc.nextInt();\n        }\n\n        int[][] dp = new int[n+1][k+1];\n\n        for (int i = 1; i <= n; i++) {\n            for (int j = 1; j <= k; j++) {\n                if (w[i] > j) {\n                    dp[i][j] = dp[i-1][j];\n                } else {\n                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j - w[i]] + v[i]);\n                }\n            }\n        }\n\n        System.out.println(dp[n][k]);\n    }\n}"
}

### 📤 리뷰 결과 Excel 예시

| SubmissionId | Title        | 문제 링크                                                                          | 언어   | 플랫폼 | 요약           | 전략                    | 코드 퀄리티            | 개선점          | 시간복잡도     | 난이도     | 태그       | 리뷰 시간               |
| ------------ | ------------ | ------------------------------------------------------------------------------ | ---- | --- | ------------ | --------------------- | ----------------- | ------------ | --------- | ------- | -------- | ------------------- |
| 42           | 0/1 Knapsack | [https://www.acmicpc.net/problem/12865](https://www.acmicpc.net/problem/12865) | Java | BOJ | DP로 최적 가치 계산 | 2차원 배열을 활용한 Bottom-Up | 조건 분기가 명확하고 중복 없음 | 변수명 개선 여지 있음 | O(n \* k) | GOLD\_5 | DP, 배낭문제 | 2025-07-30 14:15:32 |



### 📤 리뷰 결과 Excel 예시 (실제 결과 캡처)

아래는 코드 제출 후 GPT 기반 리뷰가 자동 생성되어 Excel 파일로 출력된 실제 결과 화면입니다.

![Excel Review Sample 1](https://private-user-images.githubusercontent.com/97783193/472373519-027d5438-acaa-4dcc-bcc4-4f70e384eb65.png)

![Excel Review Sample 2](https://private-user-images.githubusercontent.com/97783193/472373760-ec06a536-761c-46f9-b7b4-d11257a8f7c1.png)





