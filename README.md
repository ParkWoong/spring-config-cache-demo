# 설정 바인딩과 캐시 갱신

@ConfigurationProperties와 ApplicationRunner로 초기 라우팅 설정을 불러옵니다. 원본의 DB 로딩은 독립 실행을 위해 설정 파일로 대체하고, 갱신 시 불변 스냅샷을 교체합니다.

기존 Java 프록시 프로젝트에서 기술 주제를 추출하고 클래스·변수·패키지·데이터를 범용 예제로 재작성했습니다. 기존 Git 이력, 운영 설정, 업무 식별자는 포함하지 않습니다.

## 실행

Java 17 이상, Maven 3.6.3 이상이 필요합니다.

```sh
mvn clean verify
mvn spring-boot:run
```

Spring Boot 3.5.16 기반 학습용 예제입니다. [3.5 계열 OSS 지원 종료 안내](https://spring.io/blog/2026/06/25/spring-boot-3-5-16-available-now/)를 참고하고 운영 도입 시 지원 버전으로 전환하세요.

## 검증 범위

`mvn verify`로 단위/통합 테스트와 실행 JAR 패키징을 확인할 수 있습니다. 외부 운영 시스템 연결 및 부하 테스트는 포함하지 않습니다.
