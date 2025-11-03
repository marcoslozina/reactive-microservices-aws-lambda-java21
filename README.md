# Microservicios Reactivos Spring Boot AWS Lambda

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.5-02303A.svg?logo=gradle)](https://gradle.org/)
[![AWS Lambda](https://img.shields.io/badge/AWS-Lambda-FF9900.svg?logo=amazon-aws)](https://aws.amazon.com/lambda/)
[![License](https://img.shields.io/badge/license-Copyright-blue.svg)](LICENSE)

Proyecto de microservicios reactivos con Spring Boot 3.x, Java 21, Project Reactor y AWS Lambda, configurado con arquitectura multi-módulo, build reproducible y despliegue local/nube. Listo para producción con soporte para compilación nativa con GraalVM.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Requisitos](#-requisitos)
- [Instalación Rápida](#-instalación-rápida)
- [Desarrollo Local](#-desarrollo-local)
- [Despliegue a AWS](#-despliegue-a-aws)
- [Configuración](#-configuración)
- [Testing](#-testing)
- [Observabilidad](#-observabilidad)
- [Compilación Nativa](#-compilación-nativa)
- [CI/CD](#cicd)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Troubleshooting](#-troubleshooting)
- [Contribuir](#-contribuir)
- [Donaciones](#-donaciones)
- [Licencia](#-licencia)

## 🚀 Características

### Tecnologías Core
- ✅ **Java 21** - Última versión LTS con virtual threads y mejoras de rendimiento
- ✅ **Spring Boot 3.3.1** - Framework de microservicios reactivos
- ✅ **Spring WebFlux** - Programación reactiva no bloqueante
- ✅ **Project Reactor** - Mono/Flux para programación asíncrona
- ✅ **Spring Cloud Function** - Funciones serverless portables
- ✅ **AWS Lambda** - Ejecución serverless en la nube

### Infraestructura y DevOps
- ✅ **GraalVM Native Image** - Compilación nativa para cold starts más rápidos
- ✅ **AWS SAM** - Framework para aplicaciones serverless
- ✅ **LocalStack** - Emulación de AWS local para desarrollo
- ✅ **Docker Compose** - Orquestación de servicios locales
- ✅ **GitHub Actions** - CI/CD automatizado

### Observabilidad y Calidad
- ✅ **Micrometer** - Métricas y monitoreo
- ✅ **Logstash Logback Encoder** - Logs estructurados en JSON
- ✅ **Spring Actuator** - Health checks y métricas
- ✅ **JUnit 5** - Framework de testing moderno
- ✅ **Reactor Test** - Testing de código reactivo

### Arquitectura
- ✅ **Multi-módulo Gradle** - Organización modular del código
- ✅ **Gradle Kotlin DSL** - Configuración type-safe
- ✅ **BuildSrc con convenciones** - Plugins y configuración compartida
- ✅ **Build reproducible** - Builds consistentes en todos los entornos

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                    API Gateway HTTP API                       │
└──────────────────────────┬───────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                  AWS Lambda Function                          │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  Spring Cloud Function Adapter                         │  │
│  │  ┌──────────────────────────────────────────────────┐  │  │
│  │  │  FunctionConfig (hello, pong)                    │  │  │
│  │  │  ┌──────────────────────────────────────────┐ │  │  │
│  │  │  │  HelloHandler (Reactor + Micrometer)      │ │  │  │
│  │  │  └──────────────────────────────────────────┘ │  │  │
│  │  └──────────────────────────────────────────────────┘  │  │
│  └────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  CloudWatch Logs │ DynamoDB │ SQS │ SNS (Opcional)          │
└─────────────────────────────────────────────────────────────┘
```

### Flujo de Ejecución

1. **Request HTTP** → API Gateway HTTP API
2. **API Gateway** → Lambda Function (invoca `FunctionInvoker`)
3. **FunctionInvoker** → Spring Cloud Function (`hello` o `pong`)
4. **Function** → Procesa con Project Reactor (Mono/Flux)
5. **Response** → JSON con status code y headers

## 📦 Requisitos

### Obligatorios
- **Java 21** (JDK 21) - [Descargar OpenJDK 21](https://adoptium.net/)
- **Gradle 8.5+** (incluido con wrapper)
- **Docker** y **Docker Compose** - [Instalar Docker](https://docs.docker.com/get-docker/)
- **AWS SAM CLI** - [Instalar SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html)

### Opcionales (para compilación nativa)
- **GraalVM JDK 21** - [Descargar GraalVM](https://www.graalvm.org/downloads/)
- **Native Image** - Incluido con GraalVM

### Recomendados
- **AWS CLI** - Para despliegue a la nube
- **Postman/Insomnia** - Para probar endpoints
- **IntelliJ IDEA** o **VS Code** - IDE con soporte Java/Kotlin

## 🚀 Instalación Rápida

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd microservicios-reactivos-springboot-aws-lambda
```

### 2. Configurar variables de entorno (opcional)

```bash
cp .env.example .env
# Editar .env con tus configuraciones
```

### 3. Compilar el proyecto

```bash
./gradlew clean build
```

### 4. Ejecutar tests

```bash
./gradlew test
```

✅ **¡Listo!** El proyecto está compilado y los tests pasan.

## 💻 Desarrollo Local

### Iniciar LocalStack

LocalStack emula servicios AWS localmente (DynamoDB, SQS, SNS, Lambda, API Gateway):

```bash
# Iniciar LocalStack
docker-compose up -d localstack

# Verificar que está corriendo
docker-compose ps

# Ver logs
docker-compose logs -f localstack
```

LocalStack estará disponible en `http://localhost:4566`

### Probar con SAM Local

```bash
# Desde el directorio lambda-infra
cd lambda-infra

# Construir aplicación
sam build --template template.yaml

# Invocar función localmente
sam local invoke "ReactiveFunction" --event events/hello.json

# Iniciar API local
sam local start-api
```

Luego desde otra terminal:

```bash
# Probar GET
curl http://localhost:3000/hello

# Probar GET con parámetros
curl "http://localhost:3000/hello?name=TestUser"

# Probar POST
curl -X POST http://localhost:3000/hello \
  -H "Content-Type: application/json" \
  -d '{"name":"TestUser","message":"Hello"}'
```

### Ejecutar aplicación localmente (modo servidor)

```bash
# Ejecutar como aplicación Spring Boot normal
./gradlew :lambda-core:bootRun

# La aplicación estará en http://localhost:8080
# Endpoints disponibles:
# - /actuator/health
# - /actuator/metrics
# - /actuator/info
```

### Comandos útiles con Make

```bash
# Ver todos los comandos disponibles
make help

# Compilar y ejecutar tests
make build

# Iniciar LocalStack
make localstack-up

# Probar con SAM local
make sam-invoke
make sam-start-api

# Detener LocalStack
make localstack-down
```

## ☁️ Despliegue a AWS

### Prerequisitos

1. **AWS CLI configurado**:
   ```bash
   aws configure
   ```

2. **SAM CLI instalado**:
   ```bash
   sam --version
   ```

### Desplegar

```bash
cd lambda-infra

# Primera vez (modo guiado)
sam deploy --guided

# Despliegues posteriores
sam deploy
```

Durante el despliegue guiado, SAM preguntará:
- **Stack Name**: `microservicios-reactivos-lambda`
- **AWS Region**: `us-east-1` (o tu región preferida)
- **Confirm changes**: `Y`
- **Allow SAM CLI IAM role creation**: `Y`
- **Disable rollback**: `N`

### Verificar despliegue

```bash
# Obtener URL de la API
aws cloudformation describe-stacks \
  --stack-name microservicios-reactivos-lambda \
  --query 'Stacks[0].Outputs[?OutputKey==`ReactiveApiUrl`].OutputValue' \
  --output text

# Probar endpoint desplegado
curl https://<api-id>.execute-api.us-east-1.amazonaws.com/hello
```

### Actualizar función

```bash
# Recompilar
./gradlew clean build

# Re-desplegar
cd lambda-infra
sam build
sam deploy
```

## ⚙️ Configuración

### Cambiar la función activa

Por defecto se usa la función `hello`. Para cambiar:

**Opción 1: Variable de entorno (recomendado para producción)**

En `lambda-infra/template.yaml`:

```yaml
Environment:
  Variables:
    SPRING_CLOUD_FUNCTION_DEFINITION: pong  # Cambiar aquí
```

**Opción 2: application.yml (para desarrollo local)**

En `lambda-core/src/main/resources/application.yml`:

```yaml
spring:
  cloud:
    function:
      definition: pong  # Cambiar aquí
```

### Funciones disponibles

- **`hello`**: Función principal que maneja requests de API Gateway
  - Tipo: `Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>`
  - **Procesamiento reactivo**: Usa `Mono` internamente para procesamiento asíncrono
  - Procesa: path parameters, query parameters, body
  - Integración: Usa `HelloHandler.processGreeting()` reactivamente
  - Retorna: JSON con `{"message":"ok","echo":"...","greeting":"...","timestamp":"..."}`
  - Timeout: 5 segundos para operaciones reactivas
  - Error handling: Timeout y fallback automático

- **`pong`**: Supplier simple para health checks
  - Tipo: `Supplier<String>`
  - Retorna: `"pong"`

### Variables de entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `SPRING_CLOUD_FUNCTION_DEFINITION` | Función a ejecutar | `hello` |
| `SPRING_PROFILES_ACTIVE` | Perfil de Spring | `default` |
| `AWS_REGION` | Región de AWS | `us-east-1` |
| `LOG_LEVEL` | Nivel de logging | `INFO` |

## 🧪 Testing

### Tests unitarios

```bash
# Todos los tests
./gradlew test

# Solo tests de lambda-core
./gradlew :lambda-core:test

# Solo tests de integración
./gradlew :lambda-tests:test
```

### Tests con cobertura

```bash
# Ejecutar tests con reporte de cobertura
./gradlew test jacocoTestReport

# Ver reporte
open lambda-core/build/reports/jacoco/test/html/index.html
```

### Testing de integración con SAM

```bash
cd lambda-infra

# Invocar con evento personalizado
sam local invoke "ReactiveFunction" --event events/hello.json

# Invocar con eventos desde carpeta
sam local invoke "ReactiveFunction" --event events/hello-post.json
```

## 📊 Observabilidad

### Métricas con Micrometer

La aplicación expone métricas en `/actuator/metrics`:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Listar métricas disponibles
curl http://localhost:8080/actuator/metrics

# Obtener métrica específica
curl http://localhost:8080/actuator/metrics/lambda.invocations
```

### Métricas personalizadas

- `lambda.invocations` - Contador de invocaciones Lambda
- `lambda.processed` - Contador de procesamiento (success/error)

### Logs

Los logs se generan en formato JSON (configurado con logstash-logback-encoder):

```json
{
  "timestamp": "2024-01-01T00:00:00.000Z",
  "level": "INFO",
  "logger": "com.example.lambda.FunctionConfig",
  "message": "Function hello invoked",
  "thread": "main",
  "context": {}
}
```

En AWS Lambda, los logs se envían automáticamente a CloudWatch Logs.

### Manejo de Errores

El proyecto incluye `GlobalExceptionHandler` para manejo centralizado de errores:

**Características:**
- ✅ Sanitización automática de mensajes (previene information leakage)
- ✅ Determinación automática de HTTP status codes
- ✅ Stack traces opcionales (solo en desarrollo)
- ✅ Request ID para tracing
- ✅ Formato JSON consistente

**Variables de entorno:**
```bash
# Desarrollo: mostrar errores detallados
export DEVELOPMENT_MODE=true
export INCLUDE_STACK_TRACE=true

# Producción: errores sanitizados (default)
export DEVELOPMENT_MODE=false
```

## 🎯 Compilación Nativa

### Requisitos

- **GraalVM JDK 21** instalado
- Variable de entorno `JAVA_HOME` apuntando a GraalVM
- Mínimo 8GB RAM disponible para la compilación

### Compilar imagen nativa

```bash
# Configurar JAVA_HOME
export JAVA_HOME=/path/to/graalvm-jdk-21

# Compilar imagen nativa
./gradlew :lambda-core:nativeCompile

# El binario estará en:
# lambda-core/build/native/nativeCompile/lambda-core
```

### Configuración Incluida

El proyecto incluye configuración automática para GraalVM Native:

- ✅ **reflect-config.json**: Configuración de reflection para clases AWS Lambda
- ✅ **Resources**: Inclusión automática de properties, YAML, JSON
- ✅ **Build args**: Protocolos HTTP/HTTPS, locales, stack traces
- ✅ **Runtime initialization**: Configuración optimizada para Spring Cloud Function

**Ubicación:** `lambda-core/src/main/resources/META-INF/native-image/reflect-config.json`

### Beneficios

- **Cold start más rápido**: ~50-200ms vs 1-3s
- **Menor uso de memoria**: ~50-100MB vs 200-300MB
- **Menor tamaño**: Binario nativo vs JAR completo
- **Mejor rendimiento**: Menos overhead de JVM

### Notas

- La compilación nativa puede tomar 5-15 minutos dependiendo del hardware
- Requiere más memoria durante el build (recomendado 8GB+ RAM)
- La configuración de reflection está pre-configurada para AWS Lambda Events

## 🔄 CI/CD

### GitHub Actions

El proyecto incluye workflow CI/CD en `.github/workflows/ci.yml`:

**Jobs incluidos:**

1. **Build**: Compila y ejecuta tests
   - JDK 21 (Temurin)
   - Cache de Gradle
   - Ejecución de tests

2. **Native Build** (opcional): Compila imagen nativa
   - GraalVM 21
   - Genera binario nativo

3. **SAM Validate**: Valida templates SAM
   - Instala SAM CLI
   - Valida `template.yaml`
   - Ejecuta `sam build`

### Configurar CI/CD

El workflow se ejecuta automáticamente en:
- Push a `main` o `develop`
- Pull Requests a `main` o `develop`

### Agregar tests automatizados

```yaml
# En .github/workflows/ci.yml
- name: Run tests
  run: ./gradlew test --no-daemon
```

## 📁 Estructura del Proyecto

```
microservicios-reactivos-springboot-aws-lambda/
├── buildSrc/                      # Convenciones y plugins de Gradle
│   ├── src/main/kotlin/
│   │   └── conventions.gradle.kts    # Plugin de convenciones (Java 21, UTF-8, JUnit5)
│   └── build.gradle.kts
│
├── lambda-core/                   # Código principal de la función Lambda
│   ├── src/main/java/com/example/lambda/
│   │   ├── LambdaApplication.java    # Clase principal Spring Boot
│   │   ├── FunctionConfig.java       # Definición de funciones (hello, pong) - Reactivo
│   │   ├── handlers/
│   │   │   └── HelloHandler.java     # Handler reactivo con Reactor y Micrometer
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java  # Manejo centralizado de errores
│   ├── src/main/resources/
│   │   ├── application.yml           # Configuración Spring Boot
│   │   └── META-INF/native-image/
│   │       └── reflect-config.json   # Configuración GraalVM Native Image
│   ├── src/test/java/                # Tests unitarios reactivos
│   └── build.gradle.kts              # Dependencias del módulo + GraalVM Native
│
├── lambda-infra/                  # Infraestructura SAM
│   ├── template.yaml                # Template SAM para despliegue (IAM least-privilege)
│   ├── samconfig.toml               # Configuración SAM
│   └── events/                      # Eventos de prueba
│       ├── hello.json
│       └── hello-post.json
│
├── lambda-tests/                   # Tests de integración
│   ├── src/test/java/
│   │   └── integration/
│   │       └── SamLocalIntegrationTest.java
│   └── build.gradle.kts
│
├── .github/workflows/              # CI/CD con GitHub Actions
│   └── ci.yml
│
├── docker-compose.yml              # LocalStack para desarrollo local
├── Makefile                        # Comandos útiles
├── MEJORAS_APLICADAS.md            # Documentación de mejoras aplicadas
├── LICENSE                         # Licencia (Copyright Marcos Raimundo Lozina)
├── settings.gradle.kts             # Configuración de módulos
├── build.gradle.kts                # Build raíz
├── gradle.properties               # Propiedades Gradle
└── README.md                       # Este archivo
```

## 🐛 Troubleshooting

### Error: "Cannot find handler"

**Solución:**
```bash
# Verificar que el JAR esté construido
./gradlew clean build

# Verificar handler en template.yaml
cat lambda-infra/template.yaml | grep Handler
# Debe ser: org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest
```

### Error: "SPRING_CLOUD_FUNCTION_DEFINITION not found"

**Solución:**
Verificar que la variable esté configurada en `template.yaml`:

```yaml
Environment:
  Variables:
    SPRING_CLOUD_FUNCTION_DEFINITION: hello
```

### LocalStack no responde

**Solución:**
```bash
# Ver logs
docker-compose logs localstack

# Reiniciar
docker-compose restart localstack

# Verificar salud
curl http://localhost:4566/_localstack/health
```

### Error: "Could not resolve dependencies" en lambda-tests

**Solución:**
Este problema ya está resuelto. Si ocurre, verificar que `lambda-tests/build.gradle.kts` tenga:

```kotlin
dependencies {
    implementation(project(":lambda-core"))
    implementation("com.amazonaws:aws-lambda-java-events:3.12.0")
    testImplementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.1"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
}
```

### Error: "Redeclaration: ConventionsPlugin"

**Solución:**
Este problema ya está resuelto. Si ocurre, verificar que solo exista `buildSrc/src/main/kotlin/conventions.gradle.kts` y no haya `ConventionsPlugin.kt` duplicado:

```bash
# Eliminar archivo duplicado si existe
rm buildSrc/src/main/kotlin/ConventionsPlugin.kt

# Limpiar y recompilar
./gradlew clean build
```

### Error: Procesamiento reactivo bloquea

**Solución:**
El proyecto usa procesamiento reactivo interno con bloqueo solo en el límite Lambda (aceptable porque cada invocación es independiente). Si necesitas evitar completamente el bloqueo, usa WebFlux controllers en lugar de Lambda Functions.

Ver `FunctionConfig.java` para detalles sobre el límite reactivo.

### GraalVM Native Build falla

**Solución:**
```bash
# Verificar GraalVM instalado
java -version  # Debe mostrar GraalVM

# Aumentar memoria para Gradle
export GRADLE_OPTS="-Xmx4g"

# Limpiar y recompilar
./gradlew clean :lambda-core:nativeCompile
```

El proyecto incluye `reflect-config.json` en `src/main/resources/META-INF/native-image/` para configuración automática de reflection.

### Error: "Could not resolve dependencies"

**Solución:**
```bash
# Limpiar cache de Gradle
./gradlew clean --refresh-dependencies

# Verificar repositorios
./gradlew dependencies --configuration compileClasspath
```

### Manejo de Errores

**Variables de entorno para desarrollo:**
```bash
# Mostrar mensajes de error detallados
DEVELOPMENT_MODE=true

# Incluir stack traces en respuestas de error
INCLUDE_STACK_TRACE=true
```

En producción, el `GlobalExceptionHandler` sanitiza automáticamente los mensajes de error para prevenir information leakage.

## 🤝 Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Guía de contribución

- Sigue las convenciones de código del proyecto
- Agrega tests para nuevas funcionalidades
- Actualiza la documentación según corresponda
- Usa mensajes de commit descriptivos

## 📚 Recursos Adicionales

### Documentación Oficial

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Function Documentation](https://spring.io/projects/spring-cloud-function)
- [AWS SAM Documentation](https://docs.aws.amazon.com/serverless-application-model/)
- [Project Reactor Documentation](https://projectreactor.io/docs/core/release/reference/)
- [GraalVM Native Image](https://www.graalvm.org/latest/reference-manual/native-image/)

### Tutoriales y Ejemplos

- [Spring Cloud Function AWS Adapter](https://docs.spring.io/spring-cloud-function/docs/current/reference/html/aws.html)
- [AWS Lambda Java Handler](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)
- [LocalStack Documentation](https://docs.localstack.cloud/)

## 📄 Licencia

Copyright (c) 2024 Marcos Raimundo Lozina. Todos los derechos reservados.

Este proyecto está protegido por derechos de autor. Ver el archivo [LICENSE](LICENSE) para detalles completos.

**Uso**: Solo para uso personal y educativo. Cualquier uso comercial requiere autorización previa.

## 👤 Autor

**Marcos Raimundo Lozina**

Creado como proyecto de referencia para microservicios reactivos serverless con Spring Boot y AWS Lambda.

Copyright (c) 2024 Marcos Raimundo Lozina. Todos los derechos reservados.

## 💝 Donaciones

Si este proyecto te ha sido útil, considera apoyar su desarrollo con una donación. Tu contribución ayuda a mantener y mejorar este proyecto.

**Formas de donar:**

- 💳 **GitHub Sponsors** - Haz clic en el botón "Sponsor" en la parte superior del repositorio
- ☕ **Buy Me a Coffee** - Apoya con una donación rápida (si lo configuraste)
- 💰 **PayPal** - Donación directa via PayPal (si lo configuraste)
- 🎯 **Ko-fi** - Apoya el proyecto con un café virtual (si lo configuraste)

**Nota:** Las donaciones son completamente opcionales pero muy apreciadas. Cualquier contribución ayuda a:

- Mantener el proyecto actualizado
- Mejorar la documentación
- Agregar nuevas características
- Responder a issues y pull requests

¡Gracias por tu apoyo! 🙏

**Para configurar donaciones:** Edita el archivo `.github/FUNDING.yml` y habilita las opciones que desees usar. Luego ve a Settings → Features → Sponsorships en GitHub para activar el botón.

---

## 🎯 Próximos Pasos

1. ✅ Compilar el proyecto: `./gradlew clean build`
2. ✅ Ejecutar tests: `./gradlew test`
3. ✅ Iniciar LocalStack: `docker-compose up -d localstack`
4. ✅ Probar localmente: `cd lambda-infra && sam build && sam local invoke`
5. ✅ Desplegar a AWS: `sam deploy --guided`

**¿Necesitas ayuda?** Abre un issue en el repositorio o consulta la documentación oficial.

---

## ✅ Estado Actual del Proyecto (Verificado)

El proyecto ha sido completamente probado y verificado:

| Componente | Estado | Verificación |
|------------|--------|--------------|
| **Compilación** | ✅ | `BUILD SUCCESSFUL` - Todos los módulos compilan correctamente |
| **Tests** | ✅ | Todos los tests pasan (unitarios e integración) |
| **Aplicación** | ✅ | Spring Boot levanta sin errores |
| **Docker Compose** | ✅ | LocalStack funciona correctamente (healthy) |
| **Dependencias** | ✅ | Todas las dependencias resueltas correctamente |
| **Código Reactivo** | ✅ | Sin anti-patrones, procesamiento reactivo implementado |
| **Manejo de Errores** | ✅ | GlobalExceptionHandler centralizado configurado |
| **GraalVM Native** | ✅ | Configuración completa (reflect-config.json incluido) |
| **Seguridad IAM** | ✅ | Políticas least-privilege aplicadas |

### Mejoras Aplicadas (Noviembre 2024)

1. ✅ **Procesamiento Reactivo Mejorado** - Uso de Mono/Flux interno con bloqueo solo en límite Lambda
2. ✅ **GlobalExceptionHandler** - Manejo centralizado de errores con sanitización automática
3. ✅ **GraalVM Native Config** - reflect-config.json y recursos configurados correctamente
4. ✅ **Políticas IAM Mejoradas** - Least privilege aplicado (solo log group específico)
5. ✅ **Tests Reactivos** - Eliminado `.block()` en tests, uso de `StepVerifier`
6. ✅ **Timeout y Resiliencia** - Timeouts configurados para operaciones reactivas
7. ✅ **Error "Redeclaration: ConventionsPlugin"** - Resuelto (archivo duplicado eliminado)
8. ✅ **Dependencias no resueltas** - BOM y platform() configurados correctamente

**Ver detalles completos:** Ver archivo [`MEJORAS_APLICADAS.md`](MEJORAS_APLICADAS.md)

### Verificación Rápida

```bash
# Verificar que todo funciona
./gradlew clean build      # ✅ Debe pasar sin errores
./gradlew test             # ✅ Todos los tests deben pasar
./gradlew :lambda-core:bootRun  # ✅ Spring Boot debe levantar
docker-compose up -d localstack  # ✅ LocalStack debe estar healthy
curl http://localhost:4566/_localstack/health  # ✅ Debe responder JSON
```

---

⭐ **Si este proyecto te fue útil, considera darle una estrella!** ⭐
