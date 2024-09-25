# Money Service API

## 💡 프로젝트 개요
**Money Service API**는 동영상 플랫폼에서 발생하는 **수익 정산** 및 **통계 관리**를 위한 마이크로서비스 기반 애플리케이션입니다. **API Gateway**를 통해 사용자 요청을 각 서비스로 라우팅하고, 유저 관리, 동영상 관리, 통계 처리 및 정산 작업을 수행합니다.

## 🔑 주요 기능
- **API Gateway**: 모든 클라이언트 요청을 중앙에서 관리하고 각 마이크로서비스로 라우팅합니다.
- **User Service**: 사용자 정보 관리, JWT 기반 인증 및 OAuth2 소셜 로그인 (카카오) 처리.
- **Video Service**: 동영상 CRUD, 광고 수익 및 동영상 수익 정산 기능 제공.
    - **정산 기능 구현 시 사용된 기술**
        - **Spring Batch**: 대용량 데이터를 처리하기 위한 배치 작업에 사용.
        - **Batch Insert/Update**: 대량 데이터를 효율적으로 처리하여 데이터베이스 I/O를 줄임.
        - **성능 최적화**: 데이터베이스 쿼리 최적화 및 커넥션 풀 조정으로 성능 개선.
        - **Retry 처리**: 서버 간 통신 실패 시 재시도 로직을 추가하여 안정성을 향상.
- **Statistics Service**: 동영상 통계 데이터를 주기적으로 처리하고, 배치 작업을 통해 조회수 및 광고 수익을 집계.
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

![제목 없는 다이어그램 drawio (1)](https://github.com/user-attachments/assets/825c134e-fe15-4e59-acc5-37e9cf0693b4)


## 🚀 성능 최적화
- `@Transactional(readOnly = true)`: 데이터베이스 성능 최적화를 위해 읽기 전용 트랜잭션을 적용.
- **Redis 캐시 도입**: 자주 조회되는 데이터에 대한 캐싱을 통해 응답 시간을 단축.
- **Spring Cloud LoadBalancer**: 서비스 간 트래픽을 분산하여 성능 저하 없이 안정적으로 운영 가능.
- **Batch 작업 최적화**: Spring Batch를 사용하여 대용량 데이터를 처리할 때, 최적화된 배치 전략을 사용했습니다.
    - **Batch Insert/Update**: 대량 데이터를 한 번에 처리하여 데이터베이스 I/O 부하를 줄임.
    - **커넥션 풀 최적화**: 데이터베이스 커넥션 풀 크기를 조정하여 대량 데이터 처리를 효율적으로 수행.
    - **쿼리 최적화**: 적절한 인덱스를 추가하여 대규모 데이터 조회 시 성능을 개선.
    - **Retry 처리**: 서버 간 통신 실패 시 재시도 로직을 추가하여 안정성을 향상.

## 🔧 문제 해결
- **JWT 인증 관리**: JWT 토큰의 만료 및 유효성 검증 과정에서 발생한 문제를 해결하고, 인증 로직을 강화하였습니다.
- **배치 처리 성능 문제**: 대량의 통계 데이터를 처리할 때 성능 저하 문제가 발생하였으나, Spring Batch 최적화와 MySQL 인덱스 추가로 해결하였습니다.

## 🔮 향후 개선 사항
- **CI/CD 도입**: 자동화된 배포 파이프라인 구축 예정.
- **OAuth2 인증 확장**: 구글, 네이버 등 추가적인 소셜 로그인 제공자 지원 예정.
- **통계 대시보드**: 실시간 통계 데이터를 시각화하여 사용자에게 직관적인 정보를 제공할 예정.

[부가 설명- Notion 링크](https://third-amber-e30.notion.site/Money-10c720a78baf800c8819d97aeddb7a27?pvs=4)
