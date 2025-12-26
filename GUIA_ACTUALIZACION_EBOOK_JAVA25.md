# Guía de Actualización del Ebook: Migración a Java 25

**Documento de referencia para actualizar el ebook "Microservicios Reactivos con Spring Boot y AWS Lambda"**

**Fecha de actualización:** Diciembre 2025  
**Versión del proyecto:** Java 25, Spring Boot 3.4.13, Gradle 9.2.1

---

## 📋 Tabla de Contenidos

1. [Resumen Ejecutivo](#resumen-ejecutivo)
2. [Cambios de Versiones](#cambios-de-versiones)
3. [⚠️ SECCIÓN IMPORTANTE: AWS Lambda vs GitHub Actions](#sección-importante-aws-lambda-vs-github-actions)
4. [Configuraciones Actualizadas](#configuraciones-actualizadas)
5. [Problemas Encontrados y Soluciones](#problemas-encontrados-y-soluciones)
6. [Guía Paso a Paso para Actualizar el Ebook](#guía-paso-a-paso-para-actualizar-el-ebook)
7. [Código de Ejemplo Actualizado](#código-de-ejemplo-actualizado)
8. [Referencias y Enlaces](#referencias-y-enlaces)

---

## Resumen Ejecutivo

Este documento detalla todos los cambios necesarios para migrar el proyecto del ebook de **Java 21 a Java 25**, incluyendo actualizaciones de dependencias, configuraciones y soluciones a problemas de compatibilidad.

### Cambios Principales

- ✅ **Java 21 → Java 25**
- ✅ **Spring Boot 3.3.1 → 3.4.13**
- ✅ **Spring Cloud 2023.0.1 → 2024.0.0**
- ✅ **Gradle 8.5 → 9.2.1**
- ✅ **ASM 9.8** (requerido para Java 25)
- ✅ **Deshabilitación de AOT** (incompatibilidad con Spring Cloud Function)
- ✅ **AWS Lambda Runtime: `java25`**
- ✅ **Dockerfile actualizado a Java 25**

---

## Cambios de Versiones

### Versiones Anteriores (Java 21)

| Componente | Versión Anterior |
|-----------|------------------|
| Java | 21 |
| Spring Boot | 3.3.1 |
| Spring Cloud | 2023.0.1 |
| Gradle | 8.5 |
| AWS Lambda Runtime | `java21` |
| Docker Base Image | `gradle:8.5-jdk21`, `eclipse-temurin:21-jre-jammy` |

### Versiones Actuales (Java 25)

| Componente | Versión Actual |
|-----------|----------------|
| Java | **25** |
| Spring Boot | **3.4.13** |
| Spring Cloud | **2024.0.0** |
| Gradle | **9.2.1** |
| AWS Lambda Runtime | **`java25`** |
| Docker Base Image | **`gradle:9.2.1-jdk25`**, **`eclipse-temurin:25-jre-jammy`** |
| ASM | **9.8** (requerido) |
| Kotlin JVM Target | **24** (Kotlin no soporta completamente Java 25 aún) |

---

## ⚠️ SECCIÓN IMPORTANTE: AWS Lambda vs GitHub Actions

### 🔵 AWS Lambda: Soporte Completo para Java 25

**✅ AWS Lambda SÍ soporta Java 25 desde noviembre de 2025.**

- **Runtime disponible:** `java25`
- **Soporte oficial:** AWS Lambda añadió soporte para Java 25 en noviembre de 2025
- **Imagen base:** Basada en Amazon Corretto 25 (distribución de OpenJDK de Amazon)
- **Actualizaciones automáticas:** AWS aplica automáticamente las actualizaciones de seguridad

**Referencia oficial:**
- [AWS Lambda Java 25 Support](https://aws.amazon.com/es/about-aws/whats-new/2025/11/aws-lambda-java-25/)

**Configuración en `template.yaml`:**
```yaml
Globals:
  Function:
    Runtime: java25  # ✅ Funciona correctamente
```

**Conclusión:** Puedes desplegar tu función Lambda con Java 25 sin problemas. El despliegue a AWS Lambda funcionará perfectamente.

---

### 🟡 GitHub Actions: Soporte Limitado (con Fallback)

**⚠️ GitHub Actions puede no tener Java 25 disponible aún en `actions/setup-java@v4`.**

**Problema:**
- La acción `actions/setup-java@v4` puede no tener Java 25 disponible en el momento de la actualización
- Esto puede causar fallos en el workflow de CI/CD

**Solución Implementada: Fallback Automático a Docker**

El workflow de GitHub Actions ahora incluye un fallback automático:

```yaml
- name: Set up JDK 25
  id: setup-java
  uses: actions/setup-java@v4
  with:
    java-version: '25'
    distribution: 'temurin'
    cache: gradle
  continue-on-error: true  # ✅ No falla si Java 25 no está disponible

- name: Fallback to Docker if Java 25 not available
  if: steps.setup-java.outcome == 'failure'
  run: |
    echo "Java 25 no disponible en setup-java, usando imagen Docker con Java 25"
    docker pull eclipse-temurin:25-jdk-jammy

- name: Build with Gradle (native)
  if: steps.setup-java.outcome == 'success'
  run: ./gradlew clean build -x test ...

- name: Build with Gradle (Docker fallback)
  if: steps.setup-java.outcome == 'failure'
  run: |
    docker run --rm -v ${{ github.workspace }}:/workspace -w /workspace \
      eclipse-temurin:25-jdk-jammy \
      bash -c "chmod +x gradlew && ./gradlew clean build -x test ..."
```

**Cómo Funciona:**
1. Intenta usar `setup-java@v4` con Java 25
2. Si falla, automáticamente usa una imagen Docker con Java 25
3. El build funciona en ambos escenarios

**Referencias:**
- [GitHub Actions setup-java](https://github.com/actions/setup-java)
- [CodeQL Java 25 Support](https://github.blog/changelog/2025-09-26-codeql-2-23-1-adds-support-for-java-25-typescript-5-9-and-swift-6-1-3/)

**Conclusión:** El workflow de GitHub Actions tiene un fallback automático, pero puede ser más lento si usa Docker. Una vez que `setup-java@v4` soporte Java 25, el workflow usará la versión nativa automáticamente.

---

### 📝 Texto para el Ebook

**Sección recomendada: "Compatibilidad de Java 25 en AWS Lambda y CI/CD"**

> **AWS Lambda y Java 25**
> 
> AWS Lambda soporta Java 25 desde noviembre de 2025. Puedes desplegar funciones Lambda con el runtime `java25` sin problemas. AWS aplica automáticamente las actualizaciones de seguridad basadas en Amazon Corretto 25.
> 
> **GitHub Actions y Java 25**
> 
> GitHub Actions puede no tener Java 25 disponible inmediatamente en `actions/setup-java@v4`. El proyecto incluye un fallback automático que usa Docker con Java 25 si la acción nativa falla. Una vez que GitHub Actions soporte Java 25 nativamente, el workflow usará la versión nativa automáticamente.
> 
> **Recomendación:**
> - Para despliegues a AWS Lambda: Usa Java 25 directamente, funciona perfectamente.
> - Para CI/CD en GitHub Actions: El workflow tiene fallback automático, pero considera verificar la disponibilidad de Java 25 en `actions/setup-java@v4` antes de desplegar.

---

## Configuraciones Actualizadas

### 1. `build.gradle.kts` (Root)

```kotlin
plugins {
    java
    id("io.spring.dependency-management") version "1.1.5"
    id("org.springframework.boot") version "3.4.13" apply false  // ✅ Actualizado
    id("org.graalvm.buildtools.native") version "0.10.3" apply false
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.13")  // ✅ Actualizado
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0")  // ✅ Actualizado
    }
}
```

### 2. `buildSrc/src/main/kotlin/conventions.gradle.kts`

```kotlin
// Configuración de Java 25 para el proyecto
configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))  // ✅ Actualizado de 21 a 25
    }
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_25  // ✅ Actualizado
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_25  // ✅ Actualizado
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(25)  // ✅ Actualizado de 21 a 25
}
```

### 3. `buildSrc/build.gradle.kts`

```kotlin
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)  // ⚠️ Kotlin no soporta Java 25 completamente
    }
}
```

**Nota:** Kotlin aún no soporta completamente Java 25, por lo que se usa JVM 24 como target.

### 4. `lambda-core/build.gradle.kts`

**Cambios importantes:**

```kotlin
// ✅ ASM 9.8 requerido para Java 25
configurations.all {
    resolutionStrategy {
        force("org.ow2.asm:asm:9.8")
        force("org.ow2.asm:asm-commons:9.8")
        force("org.ow2.asm:asm-tree:9.8")
        force("org.ow2.asm:asm-analysis:9.8")
    }
}

dependencies {
    // ASM 9.8 para soporte de Java 25
    implementation("org.ow2.asm:asm:9.8")
    implementation("org.ow2.asm:asm-commons:9.8")
    implementation("org.ow2.asm:asm-tree:9.8")
    implementation("org.ow2.asm:asm-analysis:9.8")
    
    // Spring Cloud Function
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2024.0.0"))  // ✅ Actualizado
    // ... otras dependencias
}

// ✅ Deshabilitar AOT para evitar problemas de compatibilidad con Spring Cloud Function
tasks.named("processAot") {
    enabled = false
}
tasks.named("compileAotJava") {
    enabled = false
}
tasks.named("processAotResources") {
    enabled = false
}
tasks.named("aotClasses") {
    enabled = false
}
```

### 5. `lambda-infra/template.yaml`

```yaml
AWSTemplateFormatVersion: '2010-09-09'
Transform: AWS::Serverless-2016-10-31
Description: >
  Este template SAM despliega una función Lambda reactiva con Spring Boot 3.x y Java 25.  # ✅ Actualizado

Globals:
  Function:
    Runtime: java25  # ✅ Actualizado de java21 a java25
    Handler: org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest
```

### 6. `Dockerfile`

```dockerfile
# syntax=docker/dockerfile:1.7

FROM gradle:9.2.1-jdk25 AS build  # ✅ Actualizado
WORKDIR /workspace

# ... copiar archivos ...

RUN ./gradlew :lambda-core:bootJar -x processAot -x compileAotJava -x processAotResources -x aotClasses --no-daemon

FROM eclipse-temurin:25-jre-jammy  # ✅ Actualizado
ENV SPRING_PROFILES_ACTIVE=docker
WORKDIR /app

COPY --from=build /workspace/lambda-core/build/libs/lambda-core.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]
```

### 7. `gradle.properties`

```properties
# Spring Boot
springBootVersion=3.4.13  # ✅ Actualizado de 3.3.1
```

### 8. `gradle/wrapper/gradle-wrapper.properties`

```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-9.2.1-bin.zip  # ✅ Actualizado
```

### 9. `.github/workflows/ci.yml`

**Cambios principales:**

```yaml
- name: Set up JDK 25
  id: setup-java
  uses: actions/setup-java@v4
  with:
    java-version: '25'  # ✅ Actualizado
    distribution: 'temurin'
    cache: gradle
  continue-on-error: true  # ✅ Permite fallback a Docker

- name: Fallback to Docker if Java 25 not available
  if: steps.setup-java.outcome == 'failure'
  run: |
    echo "Java 25 no disponible en setup-java, usando imagen Docker con Java 25"
    docker pull eclipse-temurin:25-jdk-jammy

- name: Build with Gradle (native)
  if: steps.setup-java.outcome == 'success'
  run: ./gradlew clean build -x test -x processAot -x compileAotJava -x processAotResources -x aotClasses --no-daemon

- name: Build with Gradle (Docker fallback)
  if: steps.setup-java.outcome == 'failure'
  run: |
    docker run --rm -v ${{ github.workspace }}:/workspace -w /workspace \
      eclipse-temurin:25-jdk-jammy \
      bash -c "chmod +x gradlew && ./gradlew clean build -x test -x processAot -x compileAotJava -x processAotResources -x aotClasses --no-daemon"
```

### 10. `README.md`

**Badges actualizados:**
```markdown
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.13-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-9.2.1-02303A.svg?logo=gradle)](https://gradle.org/)
```

**Stack tecnológico actualizado:**
```markdown
- **Java 25** + **Spring Boot 3.4.13** + **Spring WebFlux**
- **Spring Cloud Function 2024.0.0**
```

**Requisitos actualizados:**
```markdown
**Requisitos:** Java 25, Gradle 9.2.1+ (incluido), Docker, AWS SAM CLI
```

**Docker commands actualizados:**
```bash
docker run --rm -it \
  -v ${PWD}:/workspace \
  -w /workspace \
  public.ecr.aws/sam/build-java25:latest \  # ✅ Actualizado
  bash -lc "./gradlew test"
```

---

## Problemas Encontrados y Soluciones

### Problema 1: Kotlin no soporta completamente Java 25

**Error:**
```
Kotlin does not yet support 25 JDK target, falling back to Kotlin JVM_24 JVM target
```

**Solución:**
Configurar Kotlin para usar JVM 24 como target en `buildSrc/build.gradle.kts`:

```kotlin
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
    }
}
```

**Explicación:** Kotlin aún no tiene soporte completo para Java 25, pero JVM 24 es compatible y funciona correctamente.

---

### Problema 2: Spring Boot no puede leer archivos de clase Java 25

**Error:**
```
Execution failed for task ':lambda-core:resolveMainClassName'.
> Unsupported class file major version 69
```

**Solución:**
1. Actualizar Spring Boot a 3.4.13 (soporta Java 25)
2. Forzar ASM 9.8 en `lambda-core/build.gradle.kts`:

```kotlin
configurations.all {
    resolutionStrategy {
        force("org.ow2.asm:asm:9.8")
        force("org.ow2.asm:asm-commons:9.8")
        force("org.ow2.asm:asm-tree:9.8")
        force("org.ow2.asm:asm-analysis:9.8")
    }
}

dependencies {
    implementation("org.ow2.asm:asm:9.8")
    implementation("org.ow2.asm:asm-commons:9.8")
    implementation("org.ow2.asm:asm-tree:9.8")
    implementation("org.ow2.asm:asm-analysis:9.8")
    // ...
}
```

**Explicación:** ASM 9.8 es necesario para leer archivos de clase Java 25 (class file version 69).

---

### Problema 3: Incompatibilidad entre Spring Cloud Function y AOT

**Error:**
```
java.lang.IllegalArgumentException: Unable to instantiate factory class 
[org.springframework.cloud.function.web.function.FunctionEndpointInitializer]
```

**Solución:**
Deshabilitar las tareas AOT en `lambda-core/build.gradle.kts`:

```kotlin
tasks.named("processAot") {
    enabled = false
}
tasks.named("compileAotJava") {
    enabled = false
}
tasks.named("processAotResources") {
    enabled = false
}
tasks.named("aotClasses") {
    enabled = false
}
```

**Explicación:** Spring Cloud Function no es completamente compatible con el procesamiento AOT de Spring Boot. Deshabilitar AOT no afecta la funcionalidad de la aplicación.

---

### Problema 4: Conflicto de nombres en buildSrc

**Error:**
```
Redeclaration: class ConventionsPlugin
```

**Solución:**
Renombrar la clase en `buildSrc/src/main/kotlin/conventions.gradle.kts`:

```kotlin
// Antes: class ConventionsPlugin
// Después: class ProjectConventionsPlugin (o cualquier otro nombre único)
```

**Explicación:** Gradle genera automáticamente algunas clases y puede haber conflictos de nombres.

---

### Problema 5: GitHub Actions no tiene Java 25 disponible

**Error:**
```
Error: Unable to find Java version '25' for distribution 'temurin'
```

**Solución:**
Implementar fallback automático a Docker en `.github/workflows/ci.yml` (ver sección de GitHub Actions arriba).

**Explicación:** `actions/setup-java@v4` puede no tener Java 25 disponible inmediatamente. El fallback a Docker asegura que el workflow funcione.

---

## Guía Paso a Paso para Actualizar el Ebook

### Paso 1: Actualizar Referencias de Versiones

1. **Buscar y reemplazar en todo el documento:**
   - `Java 21` → `Java 25`
   - `Spring Boot 3.3.1` → `Spring Boot 3.4.13`
   - `Spring Cloud 2023.0.1` → `Spring Cloud 2024.0.0`
   - `Gradle 8.5` → `Gradle 9.2.1`
   - `java21` → `java25` (en contextos de AWS Lambda)
   - `jdk21` → `jdk25` (en contextos de Docker)

### Paso 2: Agregar Sección sobre Compatibilidad

Agregar una nueva sección en el ebook sobre:

**"Compatibilidad de Java 25 en AWS Lambda y CI/CD"**

Incluir:
- Información sobre soporte de AWS Lambda para Java 25
- Información sobre GitHub Actions y fallback
- Recomendaciones para despliegues

### Paso 3: Actualizar Código de Ejemplo

1. **Actualizar `build.gradle.kts`:**
   - Cambiar versiones de Spring Boot y Spring Cloud
   - Agregar configuración de ASM 9.8
   - Agregar deshabilitación de AOT

2. **Actualizar `template.yaml`:**
   - Cambiar `Runtime: java21` a `Runtime: java25`
   - Actualizar descripción

3. **Actualizar `Dockerfile`:**
   - Cambiar imágenes base a Java 25
   - Agregar flags AOT a comandos Gradle

4. **Actualizar comandos de ejemplo:**
   - Actualizar comandos Docker para usar `build-java25`
   - Actualizar referencias a versiones

### Paso 4: Actualizar Sección de Requisitos

**Antes:**
```
Requisitos: Java 21, Gradle 8.5+, Docker, AWS SAM CLI
```

**Después:**
```
Requisitos: Java 25, Gradle 9.2.1+ (incluido), Docker, AWS SAM CLI
```

### Paso 5: Agregar Sección de Problemas Conocidos

Agregar una sección sobre:
- Kotlin y Java 25 (usar JVM 24 como target)
- ASM 9.8 requerido
- AOT deshabilitado (incompatibilidad con Spring Cloud Function)

### Paso 6: Actualizar Sección de CI/CD

Actualizar la sección de GitHub Actions para incluir:
- Información sobre fallback a Docker
- Explicación de `continue-on-error: true`
- Comandos con flags AOT excluidos

### Paso 7: Actualizar Badges y Metadatos

Actualizar todos los badges y metadatos del proyecto:
- Badge de Java: 21 → 25
- Badge de Spring Boot: 3.3.1 → 3.4.13
- Badge de Gradle: 8.5 → 9.2.1

### Paso 8: Revisar y Actualizar Referencias

Buscar y actualizar:
- Enlaces a documentación
- Referencias a versiones en texto
- Capturas de pantalla (si aplica)
- Ejemplos de salida de comandos

---

## Código de Ejemplo Actualizado

### Ejemplo 1: `build.gradle.kts` Completo (Root)

```kotlin
plugins {
    java
    id("io.spring.dependency-management") version "1.1.5"
    id("org.springframework.boot") version "3.4.13" apply false
    id("org.graalvm.buildtools.native") version "0.10.3" apply false
}

subprojects {
    group = "com.example"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "conventions")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.13")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
```

### Ejemplo 2: `lambda-core/build.gradle.kts` (Fragmento Clave)

```kotlin
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("org.graalvm.buildtools.native")
}

// ASM 9.8 requerido para Java 25
configurations.all {
    resolutionStrategy {
        force("org.ow2.asm:asm:9.8")
        force("org.ow2.asm:asm-commons:9.8")
        force("org.ow2.asm:asm-tree:9.8")
        force("org.ow2.asm:asm-analysis:9.8")
    }
}

dependencies {
    // ASM 9.8 para soporte de Java 25
    implementation("org.ow2.asm:asm:9.8")
    implementation("org.ow2.asm:asm-commons:9.8")
    implementation("org.ow2.asm:asm-tree:9.8")
    implementation("org.ow2.asm:asm-analysis:9.8")
    
    // Spring Boot WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    
    // Spring Cloud Function
    implementation("org.springframework.cloud:spring-cloud-function-context")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2024.0.0"))
    
    // ... otras dependencias
}

// Deshabilitar AOT para evitar problemas de compatibilidad con Spring Cloud Function
tasks.named("processAot") {
    enabled = false
}
tasks.named("compileAotJava") {
    enabled = false
}
tasks.named("processAotResources") {
    enabled = false
}
tasks.named("aotClasses") {
    enabled = false
}
```

### Ejemplo 3: Comandos Actualizados

**Build:**
```bash
./gradlew clean build -x test -x processAot -x compileAotJava -x processAotResources -x aotClasses
```

**Tests:**
```bash
./gradlew test -x processAot -x compileAotJava -x processAotResources -x aotClasses
```

**Boot Run:**
```bash
./gradlew :lambda-core:bootRun -x processAot -x compileAotJava -x processAotResources -x aotClasses
```

**Docker:**
```bash
docker run --rm -it \
  -v ${PWD}:/workspace \
  -w /workspace \
  public.ecr.aws/sam/build-java25:latest \
  bash -lc "./gradlew test"
```

---

## Referencias y Enlaces

### Documentación Oficial

- **AWS Lambda Java 25:** https://aws.amazon.com/es/about-aws/whats-new/2025/11/aws-lambda-java-25/
- **Spring Boot 3.4.13:** https://spring.io/projects/spring-boot
- **Spring Cloud 2024.0.0:** https://spring.io/projects/spring-cloud
- **Gradle 9.2.1:** https://gradle.org/releases/
- **ASM 9.8:** https://asm.ow2.io/

### GitHub Actions

- **setup-java:** https://github.com/actions/setup-java
- **CodeQL Java 25:** https://github.blog/changelog/2025-09-26-codeql-2-23-1-adds-support-for-java-25-typescript-5-9-and-swift-6-1-3/

### Docker Images

- **Eclipse Temurin 25:** https://hub.docker.com/_/eclipse-temurin
- **Gradle 9.2.1 JDK 25:** https://hub.docker.com/r/gradle/gradle
- **AWS SAM build-java25:** `public.ecr.aws/sam/build-java25:latest`

### Problemas Conocidos

- **Kotlin Java 25:** Kotlin aún no soporta completamente Java 25, usar JVM 24 como target
- **Spring Cloud Function AOT:** Incompatibilidad conocida, deshabilitar AOT
- **ASM 9.8:** Requerido para Java 25 (class file version 69)

---

## Checklist de Actualización

- [ ] Actualizar todas las referencias de Java 21 a Java 25
- [ ] Actualizar versiones de Spring Boot (3.3.1 → 3.4.13)
- [ ] Actualizar versiones de Spring Cloud (2023.0.1 → 2024.0.0)
- [ ] Actualizar versiones de Gradle (8.5 → 9.2.1)
- [ ] Agregar sección sobre compatibilidad AWS Lambda vs GitHub Actions
- [ ] Actualizar código de ejemplo en `build.gradle.kts`
- [ ] Actualizar código de ejemplo en `lambda-core/build.gradle.kts`
- [ ] Agregar explicación sobre ASM 9.8
- [ ] Agregar explicación sobre deshabilitación de AOT
- [ ] Actualizar `template.yaml` (Runtime: java25)
- [ ] Actualizar `Dockerfile` (imágenes Java 25)
- [ ] Actualizar comandos de ejemplo (flags AOT)
- [ ] Actualizar sección de requisitos
- [ ] Actualizar badges y metadatos
- [ ] Agregar sección de problemas conocidos
- [ ] Actualizar sección de CI/CD
- [ ] Revisar y actualizar todas las referencias y enlaces

---

**Fin del Documento**

Este documento debe ser usado como referencia completa para actualizar el ebook. Todos los cambios están documentados y probados en el proyecto actual.

