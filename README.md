# Configuration and Cache Refresh / 설정 바인딩과 캐시 갱신

[ENG](#eng) | [KR](#kr)

## ENG

Loads route settings with @ConfigurationProperties and ApplicationRunner. Refresh replaces the full cache with a new, read-only Map.

### Project structure

```text
pom.xml
src/
├── main/
│   ├── java/io/github/parkwoong/demo/
│   │   ├── DemoApplication.java
│   │   ├── RouteSettings.java
│   │   └── RouteCache.java
│   └── resources/
│       └── application.properties
└── test/java/io/github/parkwoong/demo/
    └── RouteCacheTest.java
```

Java package: `io.github.parkwoong.demo`.

| File | Role |
|---|---|
| [DemoApplication.java](src/main/java/io/github/parkwoong/demo/DemoApplication.java) | Starts Spring Boot and runs the example. |
| [RouteSettings.java](src/main/java/io/github/parkwoong/demo/RouteSettings.java) | Binds demo.routes from application.properties. |
| [RouteCache.java](src/main/java/io/github/parkwoong/demo/RouteCache.java) | Validates route entries, replaces the snapshot, and looks up a route by client name. |
| [application.properties](src/main/resources/application.properties) | Holds the demo settings. |
| [pom.xml](pom.xml) | Dependencies and build settings. |

### How it works

At startup, DemoApplication loads RouteSettings into RouteCache and prints the sample route. refresh() validates every entry before replacing the volatile Map reference. Removed entries do not stay in the cache. A failed refresh keeps the old snapshot.

### Run

Requirements: Java 17+ and Maven 3.6.3+. Run these commands from the repository root.

```sh
mvn clean verify
mvn spring-boot:run
```

The default output includes http://localhost:9090. Change demo.routes.sample in src/main/resources/application.properties to try another route. No connection is made to that address.

### Tests

RouteCacheTest checks startup binding, removal of stale entries, invalid route rejection, and preservation of the last valid snapshot.

[RouteCacheTest.java](src/test/java/io/github/parkwoong/demo/RouteCacheTest.java)

### Scope and limits

This is an in-memory, single-process cache. It does not watch files or reload settings on a timer. refresh() must be called from Java code. The demo uses a properties file instead of a database.

This learning example uses Spring Boot 3.5.16. Before production use, move to a supported version; see the [3.5 OSS support notice](https://spring.io/blog/2026/06/25/spring-boot-3-5-16-available-now/). Production connections and load tests are not included.

Adapted from a Java proxy project using generic names and sample data. Original Git history, production settings, and business identifiers are not included.

## KR

@ConfigurationProperties와 ApplicationRunner로 라우팅 설정을 읽습니다. 갱신 시 전체 캐시를 새로운 읽기 전용 Map으로 교체합니다.

### 프로젝트 구조

```text
pom.xml
src/
├── main/
│   ├── java/io/github/parkwoong/demo/
│   │   ├── DemoApplication.java
│   │   ├── RouteSettings.java
│   │   └── RouteCache.java
│   └── resources/
│       └── application.properties
└── test/java/io/github/parkwoong/demo/
    └── RouteCacheTest.java
```

Java 패키지: `io.github.parkwoong.demo`.

| 파일 | 역할 |
|---|---|
| [DemoApplication.java](src/main/java/io/github/parkwoong/demo/DemoApplication.java) | Spring Boot를 시작하고 예제를 실행합니다. |
| [RouteSettings.java](src/main/java/io/github/parkwoong/demo/RouteSettings.java) | application.properties의 demo.routes 설정을 바인딩합니다. |
| [RouteCache.java](src/main/java/io/github/parkwoong/demo/RouteCache.java) | 라우팅 항목을 검증하고 스냅샷을 교체하며 클라이언트 이름으로 주소를 조회합니다. |
| [application.properties](src/main/resources/application.properties) | 예제 실행 설정을 정의합니다. |
| [pom.xml](pom.xml) | 의존성과 빌드 설정입니다. |

### 동작 방식

시작 시 DemoApplication이 RouteSettings를 RouteCache에 반영하고 sample 주소를 출력합니다. refresh()는 모든 항목을 검증한 후 volatile Map 참조를 교체합니다. 삭제된 항목은 캐시에 남지 않으며 갱신이 실패하면 이전 스냅샷을 유지합니다.

### 실행 방법

Java 17 이상, Maven 3.6.3 이상이 필요합니다. 저장소 루트에서 실행하세요.

```sh
mvn clean verify
mvn spring-boot:run
```

기본 출력에는 http://localhost:9090이 포함됩니다. 다른 주소를 사용하려면 src/main/resources/application.properties의 demo.routes.sample을 변경하세요. 해당 주소에 실제로 접속하지는 않습니다.

### 테스트

RouteCacheTest는 초기 설정 바인딩, 삭제 항목 제거, 잘못된 주소 거부 및 마지막 정상 스냅샷 유지를 확인합니다.

[RouteCacheTest.java](src/test/java/io/github/parkwoong/demo/RouteCacheTest.java)

### 범위와 제한

단일 프로세스의 메모리 캐시입니다. 파일 변경 감지나 주기적 설정 재로딩은 없으며 Java 코드에서 refresh()를 호출해야 합니다. 독립 실행을 위해 DB 대신 설정 파일을 사용합니다.

Spring Boot 3.5.16 기반 학습용 예제입니다. 운영 도입 전 지원 버전으로 전환하세요. [3.5 OSS 지원 종료 안내](https://spring.io/blog/2026/06/25/spring-boot-3-5-16-available-now/)를 참고할 수 있습니다. 운영 시스템 연결 및 부하 테스트는 포함하지 않습니다.

기존 Java 프록시 프로젝트에서 기술을 추출해 범용 이름과 샘플 데이터로 재작성했습니다. 기존 Git 이력, 운영 설정 및 업무 식별자는 포함하지 않습니다.
