# Database Cache / DB 데이터 캐시

[ENG](#eng) | [KR](#kr)

## ENG

### Project structure

```text
pom.xml
src/main/java/io/github/parkwoong/demo/DemoApplication.java
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

### Run and test

Use JDK 8 and Maven 3.6.3+. Set JAVA_HOME to your JDK 8 directory, then run from the repository root:

```sh
java -version
mvn -version
mvn clean verify
mvn spring-boot:run
```

`StoreTest` checks startup loading, cache reads, a missing key, and a database change from light to dark followed by manual refresh.

The app starts without an HTTP server and exits. The test class shows the actual method calls and results. Database examples use H2 and need no external server.

## KR

### 프로젝트 구조

```text
pom.xml
src/main/java/io/github/parkwoong/demo/DemoApplication.java
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

### 실행과 테스트

JDK 8과 Maven 3.6.3 이상을 사용합니다. JAVA_HOME을 JDK 8 경로로 설정하고 저장소 루트에서 실행하세요:

```sh
java -version
mvn -version
mvn clean verify
mvn spring-boot:run
```

`StoreTest`는 초기 로딩, 캐시 조회, 없는 키 및 DB의 light→dark 변경 후 수동 갱신을 확인합니다.

앱은 HTTP 서버 없이 시작 후 종료합니다. 실제 메서드 호출과 결과는 테스트 클래스에서 확인합니다. DB 예제는 H2를 사용하며 외부 서버는 필요하지 않습니다.
