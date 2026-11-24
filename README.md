# Database Cache / DB 데이터 캐시

[ENG](#eng) | [KR](#kr)

## ENG

### Project structure

```text
pom.xml
src/main/java/io/github/parkwoong/demo/DemoApplication.java
src/main/java/io/github/parkwoong/demo/CacheController.java
src/main/java/io/github/parkwoong/demo/config/PropsConfig.java
src/main/java/io/github/parkwoong/demo/config/Store.java
src/main/java/io/github/parkwoong/demo/infra/mapper/SettingsMapper.java
src/main/java/io/github/parkwoong/demo/infra/repository/SettingsRepository.java
src/main/java/io/github/parkwoong/demo/properties/CacheProperties.java
src/main/resources/application.properties
src/main/resources/schema.sql
src/test/java/io/github/parkwoong/demo/StoreTest.java
```

### Code explanation

At startup, `Store.run` calls `refreshCache`. `SettingsRepository` and `SettingsMapper` load `app_settings`. Each row is stored in a Map under its `setting_key`, configured by `CacheProperties` and `PropsConfig`. Read a row with `store.getCache().get("theme")`, then read its `setting_value`. Missing keys return null.

Changing the database does not change the cached value until `refreshCache()` runs. Refresh updates existing Map entries but does not remove deleted rows. Concurrent reads/refreshes are not protected.

CacheController reads a cached setting and triggers a manual refresh.

### Run and test

Use JDK 8 and Maven 3.6.3+. Set JAVA_HOME to your JDK 8 directory, then run from the repository root:

```sh
java -version
mvn -version
mvn clean verify
mvn spring-boot:run
```

`StoreTest` checks startup loading, cache reads, a missing key, and a database change from light to dark followed by manual refresh.

The app listens on localhost:8080. The original unit tests still check the underlying methods.

#### Postman API

Call GET http://localhost:8080/api/cache/theme with no body. Expected: HTTP 200, {"setting_key":"theme","setting_value":"light"}. POST http://localhost:8080/api/cache/refresh with no body returns HTTP 200, {"entries":1}. An unknown key returns HTTP 404.

## KR

### 프로젝트 구조

```text
pom.xml
src/main/java/io/github/parkwoong/demo/DemoApplication.java
src/main/java/io/github/parkwoong/demo/CacheController.java
src/main/java/io/github/parkwoong/demo/config/PropsConfig.java
src/main/java/io/github/parkwoong/demo/config/Store.java
src/main/java/io/github/parkwoong/demo/infra/mapper/SettingsMapper.java
src/main/java/io/github/parkwoong/demo/infra/repository/SettingsRepository.java
src/main/java/io/github/parkwoong/demo/properties/CacheProperties.java
src/main/resources/application.properties
src/main/resources/schema.sql
src/test/java/io/github/parkwoong/demo/StoreTest.java
```

### 코드 설명

시작 시 `Store.run`이 `refreshCache`를 호출합니다. `SettingsRepository`와 `SettingsMapper`가 `app_settings`를 읽고 `CacheProperties`·`PropsConfig`로 지정한 `setting_key`를 기준으로 각 행을 Map에 저장합니다. `store.getCache().get("theme")`로 행을 얻은 뒤 `setting_value`를 조회합니다. 없는 키는 null을 반환합니다.

DB 값만 변경해서는 캐시가 바뀌지 않으며 `refreshCache()` 호출 후 반영됩니다. 갱신은 기존 Map에 값을 덮어쓰지만 DB에서 삭제된 행을 캐시에서 제거하지 않습니다. 동시 조회·갱신은 보호하지 않습니다.

CacheController는 캐시된 설정을 조회하고 수동 갱신을 실행합니다.

### 실행과 테스트

JDK 8과 Maven 3.6.3 이상을 사용합니다. JAVA_HOME을 JDK 8 경로로 설정하고 저장소 루트에서 실행하세요:

```sh
java -version
mvn -version
mvn clean verify
mvn spring-boot:run
```

`StoreTest`는 초기 로딩, 캐시 조회, 없는 키 및 DB의 light→dark 변경 후 수동 갱신을 확인합니다.

앱은 localhost:8080에서 실행됩니다. 기존 단위 테스트는 내부 메서드를 계속 확인합니다.

#### Postman API

본문 없이 GET http://localhost:8080/api/cache/theme을 호출하세요. 예상 결과: HTTP 200, {"setting_key":"theme","setting_value":"light"}. 본문 없이 POST http://localhost:8080/api/cache/refresh를 호출하면 HTTP 200, {"entries":1}입니다. 없는 키는 HTTP 404를 반환합니다.
