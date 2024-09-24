# Money Service API



## 💡 프로젝트 개요
**Money Service API**는 동영상 플랫폼에서 발생하는 **수익 정산** 및 **통계 관리**를 위한 마이크로서비스 기반 애플리케이션입니다. **API Gateway**를 통해 사용자 요청을 각 서비스로 라우팅하고, 유저 관리, 동영상 관리, 통계 처리 및 정산 작업을 수행합니다.

## 🔑 주요 기능
- **API Gateway**: 모든 클라이언트 요청을 중앙에서 관리하고 각 마이크로서비스로 라우팅합니다.
- **User Service**: 사용자 정보 관리, JWT 기반 인증 및 OAuth2 소셜 로그인 (카카오) 처리.
- **Video Service**: 동영상 CRUD, 광고 수익 및 동영상 수익 정산 기능 제공.
- **Statistics Service**: 동영상 통계 데이터를 주기적으로 처리하고, 배치 작업을 통해 조회수 및 광고 수익 집계.
- **Eureka Server**: 각 마이크로서비스의 동적 검색 및 로드 밸런싱 제공.

## 🛠 기술 스택
- **Backend**: JAVA21, Spring Boot, Spring Cloud Netflix Eureka, Spring Cloud Gateway, Spring Batch, Spring Security, JPA (Hibernate), MySQL, JWT
- **Cloud**: AWS (RDS, EC2, S3(중단))
- **Build Tool**: Gradle
- **Version Control**: GitHub
- **API Documentation**: SpringDoc OpenAPI
- **CI/CD**: GitHub Actions (계획 중)

## ⚙️ 아키텍처
- **API Gateway**: 클라이언트 요청을 각 서비스로 라우팅하고, 트래픽을 분산 처리합니다.
- **Eureka Server**: 각 서비스는 Eureka에 등록되며, 이를 통해 동적으로 서비스 위치를 검색하고 연결됩니다.

```plaintext
[Client] -> [API Gateway] -> [Eureka Server] -> [User Service]
                                       -> [Video Service]
                                       -> [Statistics Service]
🚀 성능 최적화
@Transactional(readOnly = true): 데이터베이스 성능 최적화를 위해 읽기 전용 트랜잭션 적용.
Redis 캐시 도입: 자주 조회되는 데이터에 대한 캐싱을 통해 응답 시간 단축.
Spring Cloud LoadBalancer: 서비스 간 트래픽을 분산하여 성능 저하 없이 안정적으로 운영 가능.
🔧 문제 해결
JWT 인증 관리: JWT 토큰의 만료 및 유효성 검증 과정에서 발생한 문제를 개선하고, 인증 로직을 강화.
배치 처리 성능 문제: 대량의 통계 데이터를 처리할 때 성능 저하 문제가 발생하였으나, Spring Batch 최적화 및 MySQL 인덱스를 추가하여 해결.
📦 설치 및 실행
프로젝트 클론


git clone https://github.com/your-repo/money-service.git
각 서비스 실행


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
```

## 🔮 향후 개선 사항
-**CI/CD 도입**: 자동화된 배포 파이프라인 구축 예정.

-**OAuth2 인증 확장**: 구글, 네이버 등 추가적인 소셜 로그인 제공자 지원 예정.

-**통계 대시보드**: 실시간 통계 데이터를 시각화하여 사용자에게 직관적인 정보를 제공할 예정.
