# Database Cache / DB 데이터 캐시

[ENG](#eng) | [KR](#kr)

## ENG

A small example of loading database rows into a Java Map and reading them from memory. It keeps the original `ApplicationRunner → Repository → Mapper → Map.put` flow, with generic setting names and one sample table.

### Structure

Java files below are under `src/main/java/io/github/parkwoong/demo/`.

| File | Role |
|---|---|
| [DemoApplication.java](src/main/java/io/github/parkwoong/demo/DemoApplication.java) | Starts Spring Boot. |
| [config/Store.java](src/main/java/io/github/parkwoong/demo/config/Store.java) | Loads rows at startup, holds the Map, and refreshes it. |
| [config/PropsConfig.java](src/main/java/io/github/parkwoong/demo/config/PropsConfig.java) | Registers the cache properties. |
| [properties/CacheProperties.java](src/main/java/io/github/parkwoong/demo/properties/CacheProperties.java) | Defines which database column is the cache key. |
| [infra/repository/SettingsRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/SettingsRepository.java) | Calls the mapper to load settings. |
| [infra/mapper/SettingsMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/SettingsMapper.java) | Reads `app_settings` with MyBatis. |
| [application.properties](src/main/resources/application.properties) | H2 connection and `cache.column.key=setting_key`. |
| [schema.sql](src/main/resources/schema.sql) | Creates the table and inserts `theme=light`. |
| [StoreTest.java](src/test/java/io/github/parkwoong/demo/StoreTest.java) | Shows loading, reading, and manual refresh. |

### How it works

1. H2 creates the sample table at startup.
2. Spring calls `Store.run()`, which calls `refreshCache()`.
3. The repository and mapper read the rows. Each row is stored in the Map using its `setting_key`.
4. Callers read the cached row without querying the database again.
5. Call `refreshCache()` to load updated values from the database.

For a Spring-managed caller with an injected `Store store`:

```java
Map<String, String> setting = store.getCache().get("theme");
String theme = setting == null ? null : setting.get("setting_value");

store.refreshCache();
```

Import `java.util.Map` for this snippet. A missing key returns `null`. Changes in the database do not change the cached value until refresh runs. The test demonstrates this with `light → dark`.

### Run and test

Use Java 17 and Maven 3.6.3+ from the repository root:

```sh
mvn clean verify
mvn spring-boot:run
```

The app loads the cache at startup and exits without an HTTP server. Use `StoreTest` to see the read and refresh calls. No separate database server is needed.

### Scope

Only the original loading and Map access pattern is kept. Domain-specific data and multiple lookup paths were removed. Class/table names are generic; H2 data and the test support standalone execution. Java files use four-space AOSP formatting.

The original refresh behavior updates existing Map entries; it does not remove rows deleted from the database. Concurrent refresh/read access is not protected. This example has no TTL, scheduler, or distributed cache. It uses the original Spring Boot 2.6.6/MyBatis APIs and is for learning, not production deployment.

## KR

DB 데이터를 Java Map에 불러오고 메모리에서 조회하는 최소 예제입니다. 원본의 `ApplicationRunner → Repository → Mapper → Map.put` 흐름을 유지하고, 범용 설정 이름과 샘플 테이블 하나만 사용합니다.

### 프로젝트 구조

아래 Java 파일은 `src/main/java/io/github/parkwoong/demo/` 아래에 있습니다.

| 파일 | 역할 |
|---|---|
| [DemoApplication.java](src/main/java/io/github/parkwoong/demo/DemoApplication.java) | Spring Boot를 시작합니다. |
| [config/Store.java](src/main/java/io/github/parkwoong/demo/config/Store.java) | 시작 시 데이터를 불러오고 Map을 보관·갱신합니다. |
| [config/PropsConfig.java](src/main/java/io/github/parkwoong/demo/config/PropsConfig.java) | 캐시 설정 프로퍼티를 등록합니다. |
| [properties/CacheProperties.java](src/main/java/io/github/parkwoong/demo/properties/CacheProperties.java) | 캐시 키로 사용할 DB 컬럼을 지정합니다. |
| [infra/repository/SettingsRepository.java](src/main/java/io/github/parkwoong/demo/infra/repository/SettingsRepository.java) | Mapper를 호출해 설정을 조회합니다. |
| [infra/mapper/SettingsMapper.java](src/main/java/io/github/parkwoong/demo/infra/mapper/SettingsMapper.java) | MyBatis로 `app_settings`를 조회합니다. |
| [application.properties](src/main/resources/application.properties) | H2 연결과 `cache.column.key=setting_key` 설정입니다. |
| [schema.sql](src/main/resources/schema.sql) | 테이블을 만들고 `theme=light`를 넣습니다. |
| [StoreTest.java](src/test/java/io/github/parkwoong/demo/StoreTest.java) | 로딩·조회·수동 갱신을 보여줍니다. |

### 불러오고 사용하는 방법

1. 시작 시 H2에 샘플 테이블을 만듭니다.
2. Spring이 `Store.run()`을 실행하고 `refreshCache()`를 호출합니다.
3. Repository와 Mapper가 DB 행을 조회합니다. 각 행을 `setting_key` 기준으로 Map에 저장합니다.
4. 사용하는 쪽에서는 DB를 다시 조회하지 않고 캐시에서 값을 읽습니다.
5. DB의 변경 값을 반영하려면 `refreshCache()`를 호출합니다.

Spring 빈에서 `Store store`를 주입받아 다음처럼 사용합니다.

```java
Map<String, String> setting = store.getCache().get("theme");
String theme = setting == null ? null : setting.get("setting_value");

store.refreshCache();
```

위 예제에는 `java.util.Map` import가 필요합니다. 없는 키는 `null`을 반환합니다. DB 값만 바꿔서는 캐시가 바뀌지 않으며 갱신 호출 후 반영됩니다. 테스트는 `light → dark` 변경으로 이 흐름을 확인합니다.

### 실행과 테스트

Java 17, Maven 3.6.3 이상을 사용하고 저장소 루트에서 실행하세요.

```sh
mvn clean verify
mvn spring-boot:run
```

앱은 시작 시 캐시를 불러오고 HTTP 서버 없이 종료합니다. 값 조회와 갱신 호출은 `StoreTest`에서 확인할 수 있습니다. 별도 DB 서버는 필요하지 않습니다.

### 범위

원본의 데이터 로딩과 Map 사용 패턴만 남겼습니다. 업무 전용 데이터와 여러 조회 경로는 제거했습니다. 클래스·테이블은 범용 이름으로 바꾸고, 독립 실행을 위한 H2 데이터와 테스트를 사용합니다. Java 파일은 들여쓰기 4칸의 AOSP 포맷을 적용했습니다.

원본 갱신 방식은 기존 Map에 값을 덮어쓰며 DB에서 삭제된 행을 캐시에서 제거하지 않습니다. 동시 갱신·읽기도 보호하지 않습니다. TTL, 스케줄러, 분산 캐시는 포함하지 않습니다. 원본 Spring Boot 2.6.6/MyBatis API를 사용하는 학습용 예제입니다.
