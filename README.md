### API Test Framework Concept (Java)

This repository is a **concept / reference implementation** of a scalable API test framework in Java with a clean separation of concerns:

- **Ports & Adapters**: tests/services depend on stable interfaces (ports), not on RestAssured directly
- **Interceptors**: auth/logging/retry/correlation are composable and reusable
- **Typed services**: domain services expose typed methods and DTOs
- **DI without Spring**: JUnit 5 extension injects a per-test `TestContext` and facades (`ApiSteps`/`ApiServices`)

---

### Tech stack

- **Java**: 21
- **Build**: Maven
- **Test framework**: JUnit 5
- **HTTP adapter**: RestAssured (replaceable adapter)
- **Serialization**: Jackson (via a small `Json` facade)
- **Logging**: log4j2
- **Reporting**: Allure (dependencies present)

---

### Project structure (high level)

Main code (framework core) is under `src/main/java/prolevexman/api/`:

- **`config/`**: configuration ports + sources (`EnvConfig` supports `-D...` → OS env vars → `.env`)
- **`http/`**: HTTP port (`HttpClient`, `HttpRequest`, `HttpResponse`, body types)
- **`http/interceptors/`**: interceptor chain + interceptors (`AuthInterceptor`, `LoggingInterceptor`)
- **`http/restassured/`**: RestAssured adapter (`RestAssuredHttpClient`) implementing the HTTP port
- **`auth/`**: auth ports and implementations (`TokenProvider`, `LoginTokenProvider`, `CachingTokenProvider`)
- **`services/`**: typed API services (example: `AuthService`)
- **`models/`**: DTOs (example: `models/auth/*`)
- **`steps/`**: workflows for tests (example: `AuthSteps`)
- **`assertions/`**: reusable assertions (JUnit-agnostic; throw `AssertionError`)
- **`di/`**: composition root and facades (`TestContext`, `ApiServices`, `ApiSteps`)
- **`support/`**: shared infrastructure utilities (`Json`, `ApiException`, etc.)

Tests are under `src/test/java/prolevexman/api/`.

---

### Configuration

The default config implementation is `prolevexman.api.config.EnvConfig`.

**Precedence**:

1) **System properties**: `-Dapi.baseUri=...`
2) **OS environment variables**: `API_BASEURI=...`
3) **`.env` file**: `.env` in the module/project root (loaded via `dotenv-java`)

Keys (examples):

- `api.baseUri` → `API_BASEURI`
- `api.email` → `API_EMAIL`
- `api.password` → `API_PASSWORD`
- `api.connectTimeoutMs` → `API_CONNECTTIMEOUTMS`
- `api.readTimeoutMs` → `API_READTIMEOUTMS`

Create your local `.env` from the example:

- Copy `.env.example` → `.env`

**Important**: `.env` must be ignored by git. Do not commit real credentials.

---

### Running tests

From the module/project directory (where `pom.xml` is):

```bash
mvn clean test
```

Run a single test (PowerShell-friendly quoting):

```bash
mvn "-Dtest=prolevexman.api.tests.LoginDiTest" clean test
```

Override config with JVM properties:

```bash
mvn "-Dtest=prolevexman.api.tests.LoginDiTest" clean test ^
  "-Dapi.baseUri=https://your-host.example" ^
  "-Dapi.email=user@example.com" ^
  "-Dapi.password=secret"
```

---

### How DI works (Step 7)

- `TestContext` is a **per-test** composition root (builds config, clients, token provider, facades).
- `TestContextExtension` is a JUnit 5 extension that supports constructor / parameter injection.
- Tests typically depend on `ApiSteps` only:

```java
@ExtendWith(TestContextExtension.class)
class LoginDiTest {
  private final ApiSteps steps;
  LoginDiTest(ApiSteps steps) { this.steps = steps; }

  @Test
  void loginWorks() {
    steps.auth().login(steps.defaultCredentials());
  }
}
```

---

### Adding a new API area (service + steps)

1) Implement a new typed service in `services/` (use `HttpClient` + `Json`).
2) Add a getter for the service in `ApiServices`.
3) Implement steps in `steps/`.
4) Add a getter in `ApiSteps`.
5) Write tests using `ApiSteps`.

This keeps tests stable while the framework grows.

---

### Interceptors

Interceptors are wired in `TestContext`:

- `LoggingInterceptor` logs request/response via log4j2 and masks sensitive headers.
- `AuthInterceptor` adds `Authorization: Bearer <token>` using `TokenProvider`.

The chain is reusable and transport-agnostic.

---

