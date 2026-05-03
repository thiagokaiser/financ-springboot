# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Build & Run
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
mvn clean package -DskipTests
```

### Tests
```bash
mvn test                                              # All tests (uses H2 in-memory DB)
mvn test -Dtest=DespesaServiceImplTest                # Single test class
mvn test -Dtest=DespesaServiceImplTest#testInsert     # Single test method
```

### Docker
```bash
make build    # Build Docker image (tgkaiser/financ)
make push     # Push to Docker Hub
docker-compose up  # Run full stack (PostgreSQL + backend + frontend)
```

## Architecture

This is a personal finance management REST API (Spring Boot 4.x, Java 21).

### Layers
```
controllers/ → services/ → repositories/ → entities/
```
- **controllers/**: REST endpoints, exception handlers, `CrudController` base interface
- **services/**: Business logic interfaces + `impl/` subpackage with implementations; `CrudServiceImpl` is the generic base
- **repositories/**: Spring Data JPA repositories; `CrudRepository` is the base interface
- **entities/**: JPA entities; `BaseEntity` is abstract and holds `id` + `usuario` FK — all domain entities extend it for per-user data isolation
- **dtos/**: Request/response DTOs; validators in `services/validators/` use custom annotations (`@UsuarioInsert`, `@UsuarioUpdate`)
- **security/**: JWT-based stateless auth (`JWTUtil`, `JWTAuthenticationFilter`, `JWTAuthorizationFilter`)
- **jobs/**: `NotificacaoJob` — scheduled task for upcoming expense notifications
- **configs/**: Profile-specific Spring beans (`TestConfig`, `DevConfig`, `ProdConfig`) and `SecurityConfig`

### Domain Model
- **UsuarioEntity**: Root aggregate; has roles (PERFIS), email, profile image
- **DespesaEntity** (Expense): Supports installment plans via `idParcela` (groups related installments), `numParcelas`, `parcelaAtual`
- **ContaEntity** (Account), **CategoriaEntity** (Category): Simple entities with `descricao`
- **NotificacaoEntity**: Created by `NotificacaoJob` for expenses due within the configured advance notice window

### Profiles
| Profile | Database | Activated by |
|---------|----------|--------------|
| `test`  | H2 in-memory | Default (application.properties) |
| `dev`   | PostgreSQL via `$POSTGRES_TEST_URL` | Manual |
| `prod`  | PostgreSQL via `$POSTGRES_PROD_URL` | Dockerfile entrypoint |

### Security
- JWT stateless auth; token expiry ~30 days
- CORS allowed origins: `https://financ.thiagokaiser.com.br` and `http://localhost:4200`
- Public endpoints: `POST /usuarios/`, `POST /auth/forgot/**`, `POST /auth/reset_password/**`, all OPTIONS
- Admin-only operations use `@PreAuthorize("hasAnyRole('ADMIN')")`

### External Integrations
- **AWS S3**: Profile image storage (`AmazonS3Service`)
- **Email**: Abstract `EmailServiceAbstract` with SMTP (Gmail) and Mock implementations — `EmailServiceMock` is active in test/dev profiles
- **Image processing**: imgscalr for resizing/cropping profile images (200px, prefix `cp`)
- **Thymeleaf**: Email templates in `resources/templates/email/`
