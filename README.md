Money Service API
<img src="https://example-image-url.com/money-service.jpg" width="700px;" alt="Money Service API Banner"/>
🌟 프로젝트 개요
Money Service API는 동영상 플랫폼에서 발생하는 수익 정산 및 통계 데이터 관리를 위해 설계된 마이크로서비스 아키텍처 기반의 애플리케이션입니다. API Gateway를 통해 모든 사용자 요청이 적절한 서비스로 라우팅되며, 유저 관리, 동영상 관리, 통계 처리 및 정산 작업이 이루어집니다. Netflix Eureka를 사용하여 서비스 간의 동적 검색 및 로드 밸런싱을 지원하며, 각 서비스는 독립적으로 개발, 배포 및 확장이 가능합니다.

💼 주요 기능
API Gateway: 모든 클라이언트 요청을 중앙에서 관리하고 각 마이크로서비스로 라우팅합니다.
User Service: 사용자 정보 관리, JWT 기반 인증 및 OAuth2 소셜 로그인 (카카오, 구글) 처리.
Video Service: 동영상 CRUD, 광고 수익 및 동영상 수익 정산 기능 제공.
Statistics Service: 동영상 통계 데이터를 주기적으로 처리하고, 배치 작업을 통해 조회수 및 광고 수익 집계.
Eureka Server: 각 마이크로서비스의 동적 검색 및 로드 밸런싱 제공.
🛠 기술 스택
Backend: Spring Boot, Spring Cloud Netflix Eureka, Spring Cloud Gateway, Spring Batch, Spring Security, JPA (Hibernate), MySQL, JWT
Cloud: AWS (RDS, EC2)
Build Tool: Gradle
Version Control: GitHub
API Documentation: SpringDoc OpenAPI
CI/CD: GitHub Actions (계획 중)
🏗 아키텍처
마이크로서비스 아키텍처
각 서비스는 독립적으로 개발, 배포 및 확장이 가능하여 시스템 유지보수와 확장성이 뛰어납니다.
각 서비스는 Eureka를 통해 동적으로 검색 및 연결됩니다.
API Gateway
클라이언트 요청을 각 마이크로서비스로 라우팅하며, 로드밸런싱을 통해 트래픽을 관리합니다.
Eureka 서버
각 서비스는 Eureka에 등록되고, 이를 통해 동적으로 서비스 위치를 검색하여 연결됩니다.
아키텍처 다이어그램
css
코드 복사
[Client] -> [API Gateway] -> [Eureka Server] -> [User Service]
                                       -> [Video Service]
                                       -> [Statistics Service]
🔍 기능 상세
1. 사용자 관리 서비스 (User Service)
JWT 기반 인증: 로그인 시 사용자 정보를 기반으로 JWT 토큰을 생성하여 인증을 처리.
OAuth2 소셜 로그인: 카카오, 구글 등의 소셜 로그인 지원.
회원 CRUD: 회원가입, 회원 정보 수정, 삭제 등의 기능.
2. 동영상 관리 서비스 (Video Service)
동영상 CRUD: 동영상을 등록하고, 조회수 및 재생 시간을 기록.
광고 수익 관리: 동영상과 연결된 광고 조회수 기록 및 수익 정산.
정산 기능: 동영상 및 광고 조회수에 따른 수익 계산 및 반환.
3. 통계 관리 서비스 (Statistics Service)
조회수 및 재생 시간 집계: 일/주/월 단위로 통계 데이터를 집계하여 기록.
배치 처리: Spring Batch를 사용하여 주기적으로 통계 데이터를 생성하고, 조회수 및 수익 정산.
4. API Gateway
중앙화된 요청 처리: 클라이언트 요청을 API Gateway에서 처리 후 적절한 마이크로서비스로 전달.
로드밸런싱: 여러 인스턴스 간 트래픽을 균등하게 분산하여 처리.
5. 서비스 등록 및 관리 (Eureka Server)
서비스 검색: 각 마이크로서비스는 Eureka에 등록되며, 이를 통해 동적으로 검색 후 연결.
💡 기술적 선택 이유
1. 마이크로서비스 아키텍처
유연한 확장성: 각 서비스는 독립적으로 개발 및 배포가 가능하여 시스템 확장성과 유지보수성을 높임.
고가용성: 서비스 장애가 발생해도 다른 서비스에 영향을 미치지 않도록 설계하여 시스템 신뢰성 확보.
2. Netflix Eureka & Spring Cloud Gateway
서비스 디스커버리: IP 주소가 동적으로 변경되는 클라우드 환경에서 각 마이크로서비스가 동적으로 검색 및 연결.
로드밸런싱: 여러 인스턴스 간 트래픽을 분산하여 시스템의 안정성을 보장.
3. Spring Security & JWT
간편한 인증 관리: JWT를 통해 인증 및 권한 부여를 쉽게 처리하고, Stateless 인증 구조로 성능 최적화.
OAuth2: 다양한 소셜 로그인 방식(Kakao, Google)을 지원하여 사용자 경험 향상.
4. Spring Batch
대용량 데이터 처리: 배치 작업을 통해 대규모 데이터를 효율적으로 처리하고, 주기적인 조회수 및 수익 집계.
5. MySQL & JPA
관계형 데이터 관리: MySQL을 통해 사용자 및 동영상, 통계 데이터를 안정적으로 관리.
JPA: 데이터베이스와의 상호작용을 추상화하여 효율적이고 가독성 높은 코드 작성 가능.
🚀 성능 최적화
@Transactional(readOnly = true): 읽기 전용 트랜잭션을 적용하여 데이터베이스 리소스를 효율적으로 사용.
Spring Cloud LoadBalancer: 서비스 간 트래픽을 분산하여 성능 저하 없이 안정적으로 운영 가능.
Redis 캐시 도입: 자주 요청되는 데이터를 캐싱하여 데이터베이스 부하를 줄이고 응답 시간 단축.
🔧 문제 해결
1. JWT 인증 및 토큰 관리 문제
JWT 토큰 만료 및 유효성 검증 과정에서 발생한 문제를 개선하여, 토큰 유효성 검증 로직을 강화함.
2. 배치 처리 성능 문제
대량의 통계 데이터를 처리할 때 성능 저하 문제가 있었으나, Spring Batch 최적화 및 MySQL 인덱스를 추가하여 처리 속도를 향상.
📦 설치 및 실행 방법
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
CI/CD 도입: 자동화된 배포 파이프라인을 구축하여 각 서비스 배포 시간을 단축하고 시스템 안정성 강화.
OAuth2 인증 확장: 구글, 네이버 등 추가적인 소셜 로그인 제공자 지원 예정.
통계 대시보드: 실시간 통계 데이터를 시각화하여 사용자에게 직관적인 통계 정보 제공 예정.
📊 프로젝트 결과
마이크로서비스 아키텍처 기반으로 동영상 플랫폼 수익 정산 및 통계 관리 시스템을 독립적으로 설계 및 구현.
JWT 및 OAuth2 인증을 통해 사용자 인증을 보안 강화.
배치 처리 기반 통계 관리: 대량의 통계 데이터를 배치 작업으로 효율적으로 처리하고, 실시간으로 처리하기 어려운 데이터를 주기적으로 집계 및 관리.
