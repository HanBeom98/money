Money Service API
💡 프로젝트 개요
Money Service API는 동영상 플랫폼에서 발생하는 수익 정산 및 통계 관리를 위한 마이크로서비스 기반 애플리케이션입니다. API Gateway를 통해 사용자 요청을 각 서비스로 라우팅하고, 유저 관리, 동영상 관리, 통계 처리 및 정산 작업을 수행합니다.

🔑 주요 기능
API Gateway: 모든 클라이언트 요청을 관리하고 각 마이크로서비스로 전달.
User Service: 사용자 정보 관리, JWT 기반 인증 및 OAuth2 소셜 로그인 처리.
Video Service: 동영상 CRUD, 광고 수익 및 동영상 수익 정산 기능 제공.
Statistics Service: 통계 데이터 관리 및 배치 작업을 통한 데이터 집계.
Eureka Server: 각 서비스의 동적 검색 및 로드 밸런싱 제공.
🛠 기술 스택
Backend: Spring Boot, Spring Cloud, Netflix Eureka, Spring Batch, Spring Security, JPA, MySQL, JWT
Cloud: AWS (EC2, RDS)
CI/CD: GitHub Actions (계획 중)
Build Tool: Gradle
API Documentation: SpringDoc OpenAPI
⚙️ 아키텍처
API Gateway: 클라이언트 요청을 각 서비스로 라우팅하고 트래픽을 분산 처리.
Eureka Server: 서비스 간 동적 검색 및 연결 제공.
plaintext
코드 복사
[Client] -> [API Gateway] -> [Eureka Server] -> [User Service]
                                       -> [Video Service]
                                       -> [Statistics Service]
💪 성능 최적화
@Transactional(readOnly = true): 데이터베이스 성능 최적화.
Redis 캐싱: 자주 조회되는 데이터에 대해 캐싱을 도입하여 응답 시간 단축.
Spring Cloud LoadBalancer: 서비스 간 트래픽 분산으로 성능 저하 방지.
🛠 문제 해결
JWT 인증 관리: JWT 토큰의 만료 및 유효성 검증 과정에서 발생한 문제를 해결하고 인증 로직을 강화.
배치 처리 성능 문제: 대량 데이터 처리 성능을 Spring Batch 최적화 및 MySQL 인덱스를 추가하여 개선.
📦 설치 및 실행
프로젝트 클론
bash
코드 복사
git clone https://github.com/your-repo/money-service.git
각 서비스 실행
bash
코드 복사
# Eureka 서버 실행
cd eureka-server
./gradlew bootRun

# API Gateway 실행
cd api-gateway
./gradlew bootRun

# User Service 실행
cd user-service
./gradlew bootRun

# Video Service 실행
cd video-service
./gradlew bootRun

# Statistics Service 실행
cd statistics
./gradlew bootRun
🔮 향후 개선 사항
CI/CD 도입: 자동화된 배포 파이프라인 구축 예정.
OAuth2 인증 확장: 구글, 네이버 등의 추가적인 소셜 로그인 제공자 지원 예정.
통계 대시보드: 실시간 통계 데이터 시각화 기능 추가 예정.
