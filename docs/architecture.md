# Architecture Overview

## Backend
- Framework: Spring Boot 3.x
- Layered structure:
  - Controller (REST API)
  - Service (business logic)
  - Repository (MyBatis + Dynamic SQL)
  - Entity (domain model)

### BackEndTools
- Gradle: build & dependency management
- Flyway: schema migration
- MyBatis Generator: code generation
- MapStruct: DTO mapping
- SpringDoc: OpenAPI auto-generation
- OpenAPI Generator: TypeScript client

### JWT Authentication
- Implemented with com.auth0:java-jwt.
- Tokens validated via OncePerRequestFilter
- Secrets stored in application.yml

---

## Frontend
- SvelteKit + TypeScript + TailwindCSS
- Uses OpenAPI-generated TypeScript clients
- Built into `/static` for backend serving

## Frontend Stack
- Framework: SvelteKit 2
- Styling: TailwindCSS + Daisy UI
- API client: Generated from Open API
- Icons: FontAwesome via svelte-fa
To generate TypeScript API clients:
```bash
npm run gen-tscode
```

---

## Communication
- REST API via JSON
- Auth via JWT Bearer tokens

## Folder Layout
```text
project-root/
├── .cursor/
│   ├── rules.mdc
│   └── config.mdc
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   │   ├── static/        ← built Svelte app
│   │   │   └── db/migration/  ← Flyway scripts
│   └── svelte-front/
│       ├── src/
│       ├── package.json
│       └── vite.config.js
└── docs/
    ├── architecture.md
```
