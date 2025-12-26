# Referencias Cruzadas: Ebook ↔ Proyecto

**Documento de mapeo completo entre el ebook "Microservicios Reactivos con Spring Boot y AWS Lambda" y el proyecto de código fuente.**

**Fecha:** Diciembre 2025  
**Versión del Proyecto:** Java 25, Spring Boot 3.4.13, Gradle 9.2.1

---

## 📋 Tabla de Contenidos

1. [Mapeo por Sección del Ebook](#mapeo-por-sección-del-ebook)
2. [Mapeo por Archivo del Proyecto](#mapeo-por-archivo-del-proyecto)
3. [Referencias Rápidas](#referencias-rápidas)
4. [Guía de Navegación](#guía-de-navegación)

---

## Mapeo por Sección del Ebook

### Sección 0: Introducción

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 0.1 Autor | Información del autor | `README.md` | Líneas 223-225 |
| 0.2 Descripción general | Descripción del proyecto | `README.md` | Líneas 1-15 |
| 0.3 Índice General | Estructura del ebook | - | - |
| 0.4 Tecnologías principales | Stack tecnológico | `README.md` | Líneas 16-25 |
| 0.5 Objetivo final del e-book | Objetivos del proyecto | `README.md` | Líneas 9-10 |
| 0.6 Estructura del proyecto base | Estructura del proyecto | `README.md` | Líneas 172-179 |
| 0.13 Por qué Java 25 LTS + Spring Boot 3.4.x | Justificación técnica | `build.gradle.kts` | Todo el archivo |
| 0.14 Java 25 LTS: La versión más Eco-Friendly | Características Java 25 | `buildSrc/src/main/kotlin/conventions.gradle.kts` | Líneas 10-22 |

---

### Sección 1: Configurando el entorno moderno de desarrollo

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 1.1 Objetivo | Objetivos de la sección | - | - |
| 1.2 Requisitos previos | Requisitos del sistema | `README.md` | Línea 37 |
| 1.3 Creación del proyecto base | Inicialización del proyecto | `settings.gradle.kts` | Todo el archivo |
| 1.3.1 Estructura del proyecto | Estructura multi-módulo | `README.md` | Líneas 172-179 |
| 1.4 Configuración del build.gradle.kts | Configuración Gradle | `build.gradle.kts` (root) | Todo el archivo |
| 1.4 Configuración del build.gradle.kts | Configuración Gradle módulo | `lambda-core/build.gradle.kts` | Todo el archivo |
| 1.4 Configuración del build.gradle.kts | Convenciones Java 25 | `buildSrc/src/main/kotlin/conventions.gradle.kts` | Todo el archivo |
| 1.5 Configuración del entorno AWS local con LocalStack | LocalStack setup | `docker-compose.yml` | Todo el archivo |
| 1.6 Creación de la primera función Lambda | Función Lambda básica | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 43-115 |
| 1.7 Estructura multi-módulo | Estructura real del proyecto | `settings.gradle.kts` | Todo el archivo |
| 1.8 Verificación final del entorno | Comandos de verificación | `README.md` | Líneas 39-50 |
| 1.9 Configuración de Variables de Entorno y Profiles | Profiles Spring | `lambda-core/src/main/resources/application.yml` | Todo el archivo |
| 1.9.1 Archivo application.yml base | Configuración base | `lambda-core/src/main/resources/application.yml` | Todo el archivo |
| 1.9.2 Archivo application-dev.yml (LocalStack) | Configuración desarrollo | `lambda-core/src/main/resources/application-dev.yml` | Todo el archivo |
| 1.9.3 Archivo application-prod.yml (AWS Real) | Configuración producción | `lambda-core/src/main/resources/application-prod.yml` | Todo el archivo |
| 1.9.4 Variables de entorno en build.gradle.kts | Variables de entorno | `build.gradle.kts` | - |
| 1.10 Conclusión de la sección | Resumen | - | - |

---

### Sección 2: Fundamentos de Spring WebFlux

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 2.1 Objetivo | Objetivos de la sección | - | - |
| 2.2 ¿Qué es Spring WebFlux? | Conceptos teóricos | - | - |
| 2.3 Tipos reactivos básicos: Mono y Flux | Project Reactor | `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` | Líneas 33-44 |
| 2.4 Creando el primer servicio reactivo | Servicio reactivo | `lambda-core/src/main/java/com/example/lambda/api/HelloController.java` | Todo el archivo |
| 2.5 Enrutamiento funcional (RouterFunction) | Enrutamiento | `lambda-core/src/main/java/com/example/lambda/api/HelloController.java` | Líneas 25-27, 37-53 |
| 2.6 WebClient: consumo reactivo de APIs externas | WebClient | - | (Ejemplo en ebook) |
| 2.7 Manejo de errores reactivo | Manejo de errores | `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java` | Todo el archivo |
| 2.7.1 Operadores de manejo de errores | Operadores Reactor | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 79-83 |
| 2.7.2 Manejo de errores por tipo | Tipos de error | `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java` | Líneas 60-71 |
| 2.7.3 Timeout y retry | Timeout y retry | `lambda-core/src/main/java/com/example/lambda/api/HelloController.java` | Líneas 58-62 |
| 2.7.4 GlobalErrorWebExceptionHandler | Handler global | `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java` | Todo el archivo |
| 2.8 Testing con StepVerifier | Testing reactivo | `lambda-core/src/test/java/com/example/lambda/handlers/HelloHandlerTest.java` | Todo el archivo |
| 2.9 Resumen y próximos pasos | Resumen | - | - |

---

### Sección 3: De microservicio reactivo a función serverless

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 3.1 Objetivo | Objetivos de la sección | - | - |
| 3.2 Introducción a Spring Cloud Function | Conceptos | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 19-24 |
| 3.2.1 Principales tipos funcionales admitidos | Tipos funcionales | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 44, 123 |
| 3.2.2 Ejemplo simple | Ejemplo básico | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 122-125 |
| 3.3 Configuración del proyecto para Lambda | Configuración Lambda | `lambda-core/build.gradle.kts` | Líneas 30-34 |
| 3.3.1 Configuración del handler para AWS Lambda | Handler Lambda | `lambda-infra/template.yaml` | Línea 13 |
| 3.4 Definiendo la función Lambda reactiva | Función reactiva | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 43-115 |
| 3.5 Empaquetado para AWS Lambda | Empaquetado | `lambda-core/build.gradle.kts` | Líneas 101-103 |
| 3.6 Prueba local con AWS SAM CLI | Pruebas SAM | `lambda-infra/events/hello.json` | Todo el archivo |
| 3.6 Prueba local con AWS SAM CLI | Comandos SAM | `Makefile` | Líneas 29-36 |
| 3.7 Despliegue en AWS | Despliegue | `lambda-infra/template.yaml` | Todo el archivo |
| 3.8 Observación del comportamiento reactivo | Observabilidad | `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` | Líneas 16-25, 37-43 |
| 3.9 Implementación de Función Lambda Reactiva | Código real | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Todo el archivo |
| 3.9.1 FunctionConfig.java - Configuración de Funciones | Configuración funciones | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Todo el archivo |
| 3.9.2 HelloHandler.java - Handler Reactivo con Métricas | Handler con métricas | `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` | Todo el archivo |
| 3.10 Manejo Robusto de Errores | Manejo de errores | `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java` | Todo el archivo |
| 3.10.1 Clases auxiliares necesarias | Clases auxiliares | `lambda-core/src/main/java/com/example/lambda/validation/RequestValidator.java` | Todo el archivo |
| 3.11 Integración con AWS API Gateway | Template SAM | `lambda-infra/template.yaml` | Todo el archivo |
| 3.11.1 Despliegue con API Gateway | Despliegue API Gateway | `lambda-infra/template.yaml` | Líneas 30-41, 75-91 |
| 3.11.2 Prueba del API | Pruebas API | `lambda-infra/events/hello.json` | Todo el archivo |
| 3.12 Conclusión de la sección | Resumen | - | - |

---

### Sección 4: Optimización de arranque y performance con GraalVM Native

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 4.1 Objetivo | Objetivos de la sección | - | - |
| 4.2 ¿Qué es GraalVM Native Image? | Conceptos | - | - |
| 4.2.1 Diferencias clave con la JVM tradicional | Comparación | - | - |
| 4.3 Cómo funciona la compilación nativa | Funcionamiento | - | - |
| 4.4 Configuración de Spring Boot 3.4.x con soporte GraalVM | Configuración GraalVM | `lambda-core/build.gradle.kts` | Líneas 59-81 |
| 4.4.1 Gradle (build.gradle.kts) - Configuración Completa | Configuración Gradle | `lambda-core/build.gradle.kts` | Líneas 59-81 |
| 4.4.2 Archivos de configuración GraalVM Native | Archivos nativos | `lambda-core/src/main/resources/META-INF/native-image/` | Todo el directorio |
| 4.5 Configuración de GraalVM en el entorno | Instalación GraalVM | `README.md` | Líneas 138-141 |
| 4.5.1 Instalación de GraalVM | Instalación | `README.md` | Línea 139 |
| 4.6 Comparativa de rendimiento | Benchmarks | - | - |
| 4.7 Compact Object Headers | Compact Headers | `lambda-core/build.gradle.kts` | (Configuración implícita) |
| 4.7.1 ¿Qué son los Compact Object Headers? | Conceptos | - | - |
| 4.7.2 Impacto en el Consumo de RAM | Impacto RAM | - | - |
| 4.7.3 Impacto en la Factura de AWS Lambda | Impacto costos | - | - |
| 4.7.4 Configuración en GraalVM Native Image | Configuración | `lambda-core/build.gradle.kts` | Líneas 64-77 |
| 4.7.5 Métricas Reales de Benchmarking | Métricas | - | - |
| 4.8 Despliegue del binario nativo en AWS Lambda | Despliegue nativo | `lambda-infra/template.yaml` | (Runtime java25) |
| 4.9 Optimización adicional del binario | Optimizaciones | `lambda-core/build.gradle.kts` | Líneas 64-77 |
| 4.10 Observaciones y buenas prácticas | Buenas prácticas | - | - |
| 4.11 Conclusión de la sección | Resumen | - | - |
| 4.12 Lambda SnapStart | SnapStart | - | (Conceptual en ebook) |
| 4.13 Conclusión: Elegir la estrategia de optimización | Resumen | - | - |

---

### Sección 5: Integración con servicios de AWS

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 5.1 Objetivo | Objetivos de la sección | - | - |
| 5.2 Arquitectura general de integración | Arquitectura | `README.md` | Líneas 27-33 |
| 5.2.1 Servicios AWS utilizados | Servicios AWS | `lambda-infra/template.yaml` | Líneas 42-73 |
| 5.3 Integración con DynamoDB | DynamoDB | - | (Ejemplo en ebook) |
| 5.4 Diseño de tablas DynamoDB | Diseño tablas | - | (Conceptual en ebook) |
| 5.5 Integración con Amazon SQS | SQS | - | (Ejemplo en ebook) |
| 5.6 Integración con Amazon SNS | SNS | - | (Ejemplo en ebook) |
| 5.7 Resiliencia en Serverless | Resiliencia | `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` | Líneas 78-83 |
| 5.8 Integración con Amazon EventBridge | EventBridge | - | (Ejemplo en ebook) |
| 5.9 Flujo completo de evento | Flujo eventos | - | (Conceptual en ebook) |
| 5.10 Testing local con LocalStack | Testing LocalStack | `docker-compose.yml` | Todo el archivo |
| 5.11 Observabilidad del flujo | Observabilidad | `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` | Líneas 16-25 |
| 5.12 Conclusión de la sección | Resumen | - | - |

---

### Sección 6: Observabilidad y Despliegue Continuo (CI/CD)

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 6.1 Objetivo | Objetivos de la sección | - | - |
| 6.2 Logging estructurado | Logging | `lambda-core/src/main/resources/application.yml` | Líneas 15-21 |
| 6.3.2 Implementación de logging estructurado | Logging JSON | `lambda-core/src/main/resources/application.yml` | Líneas 15-17 |
| 6.4 Métricas personalizadas con Micrometer | Métricas | `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` | Líneas 16-25, 37-43 |
| 6.4.1 Dependencias | Dependencias métricas | `lambda-core/build.gradle.kts` | Líneas 44-45 |
| 6.4.2 Configuración en application.yml | Configuración métricas | `lambda-core/src/main/resources/application.yml` | Líneas 23-30 |
| 6.5 Trazas distribuidas con AWS X-Ray | X-Ray | `lambda-infra/template.yaml` | (Configuración en ebook) |
| 6.6 Pipeline de CI/CD con GitHub Actions | CI/CD | `.github/workflows/ci.yml` | Todo el archivo |

---

### Sección 10: Apéndices

| Sección Ebook | Tema | Archivos del Proyecto | Ubicación |
|--------------|------|----------------------|-----------|
| 10.2 Comandos útiles | Comandos | `Makefile` | Todo el archivo |
| 10.2.1 Gradle | Comandos Gradle | `Makefile` | Líneas 6-13 |
| 10.2.2 AWS CLI | Comandos AWS | - | (En ebook) |
| 10.2.3 SAM CLI | Comandos SAM | `Makefile` | Líneas 29-39 |
| 10.2.4 Docker / LocalStack | Comandos Docker | `Makefile` | Líneas 17-27 |
| 10.3 Checklist de despliegue | Checklist | - | (En ebook) |
| 10.4 Recursos recomendados | Recursos | `README.md` | Líneas 181-193 |
| 10.5 Mapeo entre ebook y proyecto | Mapeo | Este documento | Todo el archivo |

---

## Mapeo por Archivo del Proyecto

### Archivos de Configuración

#### `build.gradle.kts` (Root)
- **Ebook Sección:** 1.4 Configuración del build.gradle.kts
- **Propósito:** Configuración principal de Gradle para el proyecto multi-módulo
- **Contenido clave:**
  - Plugins Spring Boot 3.4.13
  - Spring Cloud 2024.0.0
  - GraalVM Native Image 0.10.3
- **Referencias cruzadas:**
  - `lambda-core/build.gradle.kts` - Configuración del módulo principal
  - `buildSrc/src/main/kotlin/conventions.gradle.kts` - Convenciones Java 25

#### `lambda-core/build.gradle.kts`
- **Ebook Sección:** 1.4, 4.4.1
- **Propósito:** Configuración de dependencias y GraalVM Native para el módulo principal
- **Contenido clave:**
  - Dependencias Spring Boot WebFlux
  - Spring Cloud Function
  - ASM 9.8 para Java 25
  - Configuración GraalVM Native
  - Deshabilitación de AOT
- **Referencias cruzadas:**
  - `build.gradle.kts` - Configuración raíz
  - `lambda-core/src/main/resources/META-INF/native-image/` - Configuración GraalVM

#### `buildSrc/src/main/kotlin/conventions.gradle.kts`
- **Ebook Sección:** 1.4, 0.14
- **Propósito:** Convenciones de compilación Java 25 para todos los módulos
- **Contenido clave:**
  - Java 25 toolchain
  - sourceCompatibility y targetCompatibility
  - Configuración de encoding
- **Referencias cruzadas:**
  - `build.gradle.kts` - Aplicado a todos los subproyectos

#### `settings.gradle.kts`
- **Ebook Sección:** 1.3.1, 1.7
- **Propósito:** Definición de la estructura multi-módulo del proyecto
- **Contenido clave:**
  - Nombre del proyecto raíz
  - Módulos: lambda-core, lambda-infra, lambda-tests
- **Referencias cruzadas:**
  - `README.md` - Estructura del proyecto

#### `gradle.properties`
- **Ebook Sección:** 1.4
- **Propósito:** Propiedades globales de Gradle
- **Contenido clave:**
  - Versión de Spring Boot
  - Configuración de JVM
  - Cache y paralelización

---

### Archivos de Código Java

#### `lambda-core/src/main/java/com/example/lambda/LambdaApplication.java`
- **Ebook Sección:** 1.6, 3.3
- **Propósito:** Clase principal de Spring Boot para AWS Lambda
- **Contenido clave:**
  - `@SpringBootApplication`
  - Método `main()` para ejecución local
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` - Configuración de funciones
  - `lambda-infra/template.yaml` - Handler Lambda

#### `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java`
- **Ebook Sección:** 3.9.1, 3.4, 3.2
- **Propósito:** Configuración de funciones Lambda usando Spring Cloud Function
- **Contenido clave:**
  - Función `hello()` - Función Lambda reactiva principal
  - Función `pong()` - Health check
  - Manejo de errores reactivo
  - Integración con HelloHandler
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` - Lógica de negocio
  - `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java` - Manejo de errores
  - `lambda-infra/template.yaml` - Configuración Lambda (SPRING_CLOUD_FUNCTION_DEFINITION)

#### `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java`
- **Ebook Sección:** 3.9.2, 2.3, 5.11
- **Propósito:** Handler reactivo con métricas usando Project Reactor y Micrometer
- **Contenido clave:**
  - Método `processGreeting()` - Procesamiento reactivo
  - Métricas con Micrometer
  - Uso de Mono para programación reactiva
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` - Uso en función Lambda
  - `lambda-core/src/main/java/com/example/lambda/api/HelloController.java` - Uso en controller
  - `lambda-core/src/test/java/com/example/lambda/handlers/HelloHandlerTest.java` - Tests

#### `lambda-core/src/main/java/com/example/lambda/api/HelloController.java`
- **Ebook Sección:** 2.4, 2.5, 2.7.3
- **Propósito:** Controller REST reactivo para desarrollo local (Docker)
- **Contenido clave:**
  - Endpoints GET y POST `/hello`
  - Manejo reactivo con Mono
  - Timeout y manejo de errores
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` - Lógica de negocio
  - `Dockerfile` - Ejecución en contenedor

#### `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java`
- **Ebook Sección:** 3.10, 2.7, 2.7.2
- **Propósito:** Manejo global de excepciones para funciones Lambda
- **Contenido clave:**
  - Método `handleException()` - Manejo centralizado
  - Determinación de HTTP status codes
  - Sanitización de mensajes de error
  - Formato JSON de errores
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` - Uso en función hello()
  - `lambda-core/src/main/java/com/example/lambda/validation/RequestValidator.java` - Excepciones de validación

#### `lambda-core/src/main/java/com/example/lambda/validation/RequestValidator.java`
- **Ebook Sección:** 3.10.1, 2.7
- **Propósito:** Validador reactivo usando Bean Validation
- **Contenido clave:**
  - Método `validate()` - Validación reactiva
  - Uso de Schedulers.boundedElastic()
  - Excepción personalizada ValidationException
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/exception/GlobalExceptionHandler.java` - Manejo de ValidationException
  - `lambda-core/build.gradle.kts` - Dependencia spring-boot-starter-validation

---

### Archivos de Configuración Spring

#### `lambda-core/src/main/resources/application.yml`
- **Ebook Sección:** 1.9.1, 6.3.2, 6.4.2
- **Propósito:** Configuración base de Spring Boot
- **Contenido clave:**
  - Nombre de la aplicación: reactive-lambda
  - Perfil activo por defecto: dev
  - Configuración Spring Cloud Function
  - Logging estructurado JSON
  - Configuración de Actuator
  - Región AWS
- **Referencias cruzadas:**
  - `lambda-core/src/main/resources/application-dev.yml` - Perfil desarrollo
  - `lambda-core/src/main/resources/application-prod.yml` - Perfil producción

#### `lambda-core/src/main/resources/application-dev.yml`
- **Ebook Sección:** 1.9.2, 5.10
- **Propósito:** Configuración para desarrollo local con LocalStack
- **Contenido clave:**
  - Endpoints de DynamoDB, SQS, SNS apuntando a LocalStack (localhost:4566)
- **Referencias cruzadas:**
  - `docker-compose.yml` - Configuración LocalStack
  - `lambda-core/src/main/resources/application.yml` - Perfil base

#### `lambda-core/src/main/resources/application-prod.yml`
- **Ebook Sección:** 1.9.3
- **Propósito:** Configuración para producción en AWS real
- **Contenido clave:**
  - Endpoints de AWS servicios en null (usa AWS real)
- **Referencias cruzadas:**
  - `lambda-core/src/main/resources/application.yml` - Perfil base
  - `lambda-infra/template.yaml` - Variables de entorno (SPRING_PROFILES_ACTIVE=aws)

---

### Archivos de Infraestructura AWS

#### `lambda-infra/template.yaml`
- **Ebook Sección:** 3.11, 3.3.1, 5.2.1, 6.5
- **Propósito:** Template AWS SAM para despliegue de la función Lambda
- **Contenido clave:**
  - Función Lambda con runtime java25
  - Handler: FunctionInvoker de Spring Cloud Function
  - API Gateway HTTP API
  - Permisos IAM (CloudWatch Logs)
  - Variables de entorno (SPRING_CLOUD_FUNCTION_DEFINITION, SPRING_PROFILES_ACTIVE)
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` - Funciones definidas
  - `lambda-core/src/main/resources/application-prod.yml` - Perfil producción
  - `lambda-infra/events/hello.json` - Eventos de prueba

#### `lambda-infra/events/hello.json`
- **Ebook Sección:** 3.6, 3.11.2
- **Propósito:** Evento de ejemplo para pruebas locales con SAM
- **Contenido clave:**
  - Evento API Gateway HTTP API
  - Query parameters y path parameters
- **Referencias cruzadas:**
  - `lambda-infra/template.yaml` - Función Lambda
  - `Makefile` - Comando sam-invoke

#### `lambda-infra/events/hello-post.json`
- **Ebook Sección:** 3.6
- **Propósito:** Evento POST de ejemplo para pruebas
- **Referencias cruzadas:**
  - `lambda-infra/template.yaml` - Función Lambda
  - `lambda-core/src/main/java/com/example/lambda/FunctionConfig.java` - Función hello()

---

### Archivos de Docker y LocalStack

#### `docker-compose.yml`
- **Ebook Sección:** 1.5, 5.10
- **Propósito:** Configuración de LocalStack para desarrollo local
- **Contenido clave:**
  - Servicio LocalStack con puerto 4566
  - Servicios habilitados: dynamodb, sqs, sns, lambda, apigateway
  - Variables de entorno AWS (test/test)
  - Healthcheck
- **Referencias cruzadas:**
  - `lambda-core/src/main/resources/application-dev.yml` - Endpoints LocalStack
  - `Makefile` - Comandos localstack-up, localstack-down

#### `Dockerfile`
- **Ebook Sección:** (Mencionado en desarrollo local)
- **Propósito:** Imagen Docker multi-stage para la aplicación
- **Contenido clave:**
  - Build stage: gradle:9.2.1-jdk25
  - Runtime stage: eclipse-temurin:25-jre-jammy
  - Compilación con flags AOT excluidos
- **Referencias cruzadas:**
  - `lambda-core/build.gradle.kts` - Tareas AOT deshabilitadas
  - `README.md` - Comandos Docker

---

### Archivos de CI/CD

#### `.github/workflows/ci.yml`
- **Ebook Sección:** 6.6
- **Propósito:** Pipeline de CI/CD con GitHub Actions
- **Contenido clave:**
  - Build y test con Java 25
  - Fallback a Docker si Java 25 no disponible
  - Validación SAM
  - Exclusión de tareas AOT
- **Referencias cruzadas:**
  - `lambda-core/build.gradle.kts` - Tareas AOT deshabilitadas
  - `lambda-infra/template.yaml` - Validación SAM

---

### Archivos de Testing

#### `lambda-core/src/test/java/com/example/lambda/handlers/HelloHandlerTest.java`
- **Ebook Sección:** 2.8
- **Propósito:** Tests unitarios del HelloHandler usando StepVerifier
- **Contenido clave:**
  - Tests reactivos con Project Reactor
  - Verificación de métricas
- **Referencias cruzadas:**
  - `lambda-core/src/main/java/com/example/lambda/handlers/HelloHandler.java` - Clase bajo test

---

### Archivos de Utilidades

#### `Makefile`
- **Ebook Sección:** 10.2, 10.2.1, 10.2.3, 10.2.4
- **Propósito:** Comandos útiles para desarrollo
- **Contenido clave:**
  - Comandos Gradle (build, test, clean)
  - Comandos LocalStack (localstack-up, localstack-down)
  - Comandos SAM (sam-build, sam-invoke, sam-start-api, sam-deploy)
  - Compilación nativa (native-compile)
- **Referencias cruzadas:**
  - `README.md` - Documentación de comandos
  - Todos los archivos del proyecto

#### `README.md`
- **Ebook Sección:** 0.2, 0.4, 0.6, 1.2, 1.8, 4.5, 10.4
- **Propósito:** Documentación principal del proyecto
- **Contenido clave:**
  - Descripción del proyecto
  - Stack tecnológico
  - Inicio rápido
  - Comandos de desarrollo
  - Estructura del proyecto
  - Recursos y enlaces
- **Referencias cruzadas:**
  - Todos los archivos del proyecto

---

### Archivos de Configuración GraalVM

#### `lambda-core/src/main/resources/META-INF/native-image/reflect-config.json`
- **Ebook Sección:** 4.4.2
- **Propósito:** Configuración de reflexión para GraalVM Native Image
- **Referencias cruzadas:**
  - `lambda-core/build.gradle.kts` - Configuración GraalVM Native

#### `lambda-core/src/main/resources/META-INF/native-image/native-image.properties`
- **Ebook Sección:** 4.4.2
- **Propósito:** Propiedades de configuración para GraalVM Native Image
- **Referencias cruzadas:**
  - `lambda-core/build.gradle.kts` - Configuración GraalVM Native

---

## Referencias Rápidas

### Por Funcionalidad

| Funcionalidad | Archivos Clave | Sección Ebook |
|--------------|----------------|---------------|
| **Configuración Java 25** | `buildSrc/src/main/kotlin/conventions.gradle.kts` | 0.14, 1.4 |
| **Función Lambda Reactiva** | `FunctionConfig.java`, `HelloHandler.java` | 3.9 |
| **Manejo de Errores** | `GlobalExceptionHandler.java` | 3.10, 2.7 |
| **Validación** | `RequestValidator.java` | 3.10.1 |
| **API REST Local** | `HelloController.java` | 2.4, 2.5 |
| **Configuración Spring** | `application.yml`, `application-dev.yml`, `application-prod.yml` | 1.9 |
| **Despliegue AWS** | `template.yaml` | 3.11 |
| **GraalVM Native** | `lambda-core/build.gradle.kts`, `META-INF/native-image/` | 4.4 |
| **CI/CD** | `.github/workflows/ci.yml` | 6.6 |
| **LocalStack** | `docker-compose.yml` | 1.5, 5.10 |

### Por Tipo de Archivo

| Tipo | Archivos | Sección Ebook Principal |
|------|----------|-------------------------|
| **Configuración Gradle** | `build.gradle.kts`, `lambda-core/build.gradle.kts`, `conventions.gradle.kts` | 1.4 |
| **Código Java** | `FunctionConfig.java`, `HelloHandler.java`, `HelloController.java`, etc. | 3.9 |
| **Configuración Spring** | `application*.yml` | 1.9 |
| **Infraestructura** | `template.yaml`, `docker-compose.yml`, `Dockerfile` | 3.11, 1.5 |
| **Testing** | `HelloHandlerTest.java` | 2.8 |
| **Utilidades** | `Makefile`, `README.md` | 10.2 |

---

## Guía de Navegación

### Para Desarrolladores que Siguen el Ebook

1. **Comenzar desde el Ebook:**
   - Lee la sección del ebook
   - Busca la sección en este documento
   - Encuentra los archivos del proyecto relacionados
   - Abre los archivos indicados en tu IDE

2. **Comenzar desde el Proyecto:**
   - Abre un archivo del proyecto
   - Busca el archivo en este documento (sección "Mapeo por Archivo")
   - Encuentra las secciones del ebook relacionadas
   - Lee la sección correspondiente en el ebook

### Convenciones de Referencia

- **Sección Ebook:** Formato `X.Y` o `X.Y.Z` (ej: 3.9.1)
- **Ubicación en Archivo:** Líneas específicas cuando es relevante
- **Referencias Cruzadas:** Enlaces a otros archivos relacionados

### Mantenimiento del Documento

Este documento debe actualizarse cuando:
- Se agreguen nuevas secciones al ebook
- Se agreguen nuevos archivos al proyecto
- Cambien las referencias entre ebook y proyecto
- Se actualicen versiones de dependencias

---

**Última actualización:** Diciembre 2025  
**Versión del Proyecto:** Java 25, Spring Boot 3.4.13, Gradle 9.2.1  
**Versión del Ebook:** Actualizada para Java 25 LTS

