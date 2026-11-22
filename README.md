# Database-backed Configuration Store / DB 기반 설정 저장소

[ENG](#eng) | [KR](#kr)

## ENG

Extracts the original Store, configuration-property classes, and its three database lookup paths. It loads token keys, backend routes, and bypass options with ApplicationRunner.

### Source and minimal changes

Kept the original DB-loading implementation and mutable HashMaps. PropsConfig registers only this module’s property classes. Renamed business-specific prefixes and added H2 schema/sample rows. The token table starts empty; no real keys are included. Removed the generated RouteCache snapshot design and properties-only data source.

| Original source | Extracted file |
|---|---|
| `ProxyApplication.java` | [DemoApplication.java](src/main/java/io/github/parkwoong/demo/DemoApplication.java) |
| `config/Store.java` | [config/Store.java](src/main/java/io/github/parkwoong/demo/config/Store.java) |
| `properties/BackendApiProperties.java` | [properties/BackendApiProperties.java](src/main/java/io/github/parkwoong/demo/properties/BackendApiProperties.java) |
| `properties/BypassOptionProperties.java` | [properties/BypassOptionProperties.java](src/main/java/io/github/parkwoong/demo/properties/BypassOptionProperties.java) |
| `properties/TokenKeyProperties.java` | [properties/TokenKeyProperties.java](src/main/java/io/github/parkwoong/demo/properties/TokenKeyProperties.java) |
| `infra/mapper/GfepClientBackendApiMapper.java` | [infra/mapper/ClientBackendApiMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/ClientBackendApiMapper.java) |
| `infra/mapper/GfepBypassOptionMapper.java` | [infra/mapper/BypassOptionMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/BypassOptionMapper.java) |
| `infra/mapper/GfepRhssoTokenKeyMapper.java` | [infra/mapper/TokenKeyMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/TokenKeyMapper.java) |
| `infra/repository/GfepClientBackendApiRepository.java` | [infra/repository/ClientBackendApiRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/ClientBackendApiRepository.java) |
| `infra/repository/GfepBypassOptionRepository.java` | [infra/repository/BypassOptionRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/BypassOptionRepository.java) |
| `infra/repository/GfepRhssoTokenKeyRepository.java` | [infra/repository/TokenKeyRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/TokenKeyRepository.java) |

### Structure

```text
pom.xml
src/main/java/io/github/parkwoong/demo/DemoApplication.java
src/main/java/io/github/parkwoong/demo/config/PropsConfig.java
src/main/java/io/github/parkwoong/demo/config/Store.java
src/main/java/io/github/parkwoong/demo/infra/mapper/BypassOptionMapper.java
src/main/java/io/github/parkwoong/demo/infra/mapper/ClientBackendApiMapper.java
src/main/java/io/github/parkwoong/demo/infra/mapper/TokenKeyMapper.java
src/main/java/io/github/parkwoong/demo/infra/repository/BypassOptionRepository.java
src/main/java/io/github/parkwoong/demo/infra/repository/ClientBackendApiRepository.java
src/main/java/io/github/parkwoong/demo/infra/repository/TokenKeyRepository.java
src/main/java/io/github/parkwoong/demo/properties/BackendApiProperties.java
src/main/java/io/github/parkwoong/demo/properties/BypassOptionProperties.java
src/main/java/io/github/parkwoong/demo/properties/TokenKeyProperties.java
src/main/resources/application.properties
src/main/resources/schema.sql
src/test/java/io/github/parkwoong/demo/StoreTest.java
```

### How it works

On startup, Store.run calls refreshTokenKeyList, refreshClientBackendMap, and refreshOptionList. Repository → Mapper reads each table. The maps are keyed by client ID and token-key ID, as in the original code. StoreTest verifies startup loading and a manual refresh.

### Run and test

Use Java 17 and Maven 3.6.3+. Run from the repository root:

```sh
mvn clean verify
mvn spring-boot:run
```

The app starts without an HTTP server or an added console workflow. Use the tests under src/test for actual calls and results. Database modules initialize H2 at startup.

### Formatting

All Java files use google-java-format 1.24.0 with the AOSP style: four-space indentation, consistent line breaks, and organized imports.

### Original limits

Original refresh methods put entries into existing maps without clearing removed rows. Concurrent refresh/read access is not protected. No scheduler or HTTP refresh endpoint is added. This preserves the source behavior; it is not an atomic cache-reload design.

The build keeps Spring Boot 2.6.6 and the original MyBatis/JJWT APIs for source fidelity. Lombok 1.18.36 supports the local Java 17 build. These are legacy dependencies; this extraction is for learning, not a production-ready upgrade. No production integration or load test is included.

## KR

원본 Store, 설정 프로퍼티 클래스 및 DB 조회 경로 3개를 추출했습니다. ApplicationRunner로 토큰 키·백엔드 경로·우회 옵션을 불러옵니다.

### 원본과 최소 변경 사항

원본 DB 로딩 구현과 변경 가능한 HashMap을 유지했습니다. PropsConfig는 이 모듈의 프로퍼티 클래스만 등록합니다. 업무 전용 접두사를 바꾸고 H2 스키마·샘플 행을 추가했습니다. 토큰 테이블은 비어 있으며 실제 키를 포함하지 않습니다. 새로 작성했던 RouteCache 스냅샷 설계와 설정 파일 전용 데이터 공급 방식은 제거했습니다.

| 원본 파일 | 추출 파일 |
|---|---|
| `ProxyApplication.java` | [DemoApplication.java](src/main/java/io/github/parkwoong/demo/DemoApplication.java) |
| `config/Store.java` | [config/Store.java](src/main/java/io/github/parkwoong/demo/config/Store.java) |
| `properties/BackendApiProperties.java` | [properties/BackendApiProperties.java](src/main/java/io/github/parkwoong/demo/properties/BackendApiProperties.java) |
| `properties/BypassOptionProperties.java` | [properties/BypassOptionProperties.java](src/main/java/io/github/parkwoong/demo/properties/BypassOptionProperties.java) |
| `properties/TokenKeyProperties.java` | [properties/TokenKeyProperties.java](src/main/java/io/github/parkwoong/demo/properties/TokenKeyProperties.java) |
| `infra/mapper/GfepClientBackendApiMapper.java` | [infra/mapper/ClientBackendApiMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/ClientBackendApiMapper.java) |
| `infra/mapper/GfepBypassOptionMapper.java` | [infra/mapper/BypassOptionMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/BypassOptionMapper.java) |
| `infra/mapper/GfepRhssoTokenKeyMapper.java` | [infra/mapper/TokenKeyMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/TokenKeyMapper.java) |
| `infra/repository/GfepClientBackendApiRepository.java` | [infra/repository/ClientBackendApiRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/ClientBackendApiRepository.java) |
| `infra/repository/GfepBypassOptionRepository.java` | [infra/repository/BypassOptionRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/BypassOptionRepository.java) |
| `infra/repository/GfepRhssoTokenKeyRepository.java` | [infra/repository/TokenKeyRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/TokenKeyRepository.java) |

### 프로젝트 구조

```text
pom.xml
src/main/java/io/github/parkwoong/demo/DemoApplication.java
src/main/java/io/github/parkwoong/demo/config/PropsConfig.java
src/main/java/io/github/parkwoong/demo/config/Store.java
src/main/java/io/github/parkwoong/demo/infra/mapper/BypassOptionMapper.java
src/main/java/io/github/parkwoong/demo/infra/mapper/ClientBackendApiMapper.java
src/main/java/io/github/parkwoong/demo/infra/mapper/TokenKeyMapper.java
src/main/java/io/github/parkwoong/demo/infra/repository/BypassOptionRepository.java
src/main/java/io/github/parkwoong/demo/infra/repository/ClientBackendApiRepository.java
src/main/java/io/github/parkwoong/demo/infra/repository/TokenKeyRepository.java
src/main/java/io/github/parkwoong/demo/properties/BackendApiProperties.java
src/main/java/io/github/parkwoong/demo/properties/BypassOptionProperties.java
src/main/java/io/github/parkwoong/demo/properties/TokenKeyProperties.java
src/main/resources/application.properties
src/main/resources/schema.sql
src/test/java/io/github/parkwoong/demo/StoreTest.java
```

### 코드 설명

시작 시 Store.run이 refreshTokenKeyList, refreshClientBackendMap, refreshOptionList를 호출합니다. Repository → Mapper가 각 테이블을 조회합니다. 원본처럼 클라이언트 ID·토큰 키 ID로 Map을 구성합니다. StoreTest에서 초기 로딩과 수동 갱신을 확인합니다.

### 실행과 테스트

Java 17, Maven 3.6.3 이상을 사용합니다. 저장소 루트에서 실행하세요:

```sh
mvn clean verify
mvn spring-boot:run
```

앱은 HTTP 서버나 별도 콘솔 시나리오 없이 시작합니다. 실제 호출과 결과 확인은 src/test의 테스트를 이용하세요. DB 모듈은 시작 시 H2를 초기화합니다.

### 코드 포맷

전체 Java 파일에 google-java-format 1.24.0의 AOSP 스타일을 적용했습니다. 들여쓰기 4칸, 일관된 줄바꿈 및 import 정리를 사용합니다.

### 유지한 원본 동작과 제한

원본 갱신 메서드는 기존 Map에 put하며 삭제된 DB 행을 캐시에서 제거하지 않습니다. 동시 갱신·읽기 접근도 보호하지 않습니다. 스케줄러나 HTTP 갱신 엔드포인트는 추가하지 않았습니다. 원본 동작을 보존한 것이며 원자적 캐시 교체 구현이 아닙니다.

원본 코드 보존을 위해 Spring Boot 2.6.6과 원본 MyBatis/JJWT API를 사용합니다. 로컬 Java 17 빌드를 위해 Lombok 1.18.36을 적용했습니다. 레거시 의존성을 사용하는 학습용 추출본이며 운영용 업그레이드가 아닙니다. 운영 연동 및 부하 테스트는 포함하지 않습니다.
