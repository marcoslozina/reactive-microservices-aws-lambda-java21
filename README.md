# Microservicios Reactivos Spring Boot AWS Lambda

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.13-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-9.2.1-02303A.svg?logo=gradle)](https://gradle.org/)
[![AWS Lambda](https://img.shields.io/badge/AWS-Lambda-FF9900.svg?logo=amazon-aws)](https://aws.amazon.com/lambda/)
[![License](https://img.shields.io/badge/license-Copyright-blue.svg)](LICENSE)

Código fuente del ebook **"Microservicios Reactivos con Spring Boot y AWS Lambda"**. Proyecto completo con Spring Boot 3.4.13, Java 25, Project Reactor y AWS Lambda. Listo para producción con soporte para compilación nativa con GraalVM.

> 📖 **Obtén el ebook completo:**
> - 📚 [Amazon Kindle](https://www.amazon.com/dp/B0G1L1FFK6)
> - 🛒 [Hotmart](https://go.hotmart.com/O102857613J?dp=1)
> - 💳 [Gumroad](https://marcoslozina.gumroad.com/l/tporu)

## 🚀 Stack Tecnológico

- **Java 25** + **Spring Boot 3.4.13** + **Spring WebFlux**
- **Spring Cloud Function 2024.0.0**
- **Project Reactor** (Mono/Flux)
- **Spring Cloud Function** + **AWS Lambda**
- **GraalVM Native Image** (compilación nativa)
- **AWS SAM** + **LocalStack** (desarrollo local)
- **GitHub Actions** (CI/CD)
- **Micrometer** + **Spring Actuator** (observabilidad)

## 🏗️ Arquitectura

```
API Gateway HTTP API → AWS Lambda → Spring Cloud Function → Project Reactor
                                    ↓
                            CloudWatch Logs / DynamoDB / SQS
```

## 🚀 Inicio Rápido

**Requisitos:** Java 25, Gradle 9.2.1+ (incluido), Docker, AWS SAM CLI

```bash
# Clonar y compilar
git clone <repository-url>
cd reactive-microservices-aws-lambda-java21
./gradlew clean build

# Ejecutar tests
./gradlew test

# Levantar aplicación local
./gradlew :lambda-core:bootRun
```

## 💻 Desarrollo Local

```bash
# Iniciar LocalStack
docker-compose up -d localstack

# Probar con SAM
cd lambda-infra
sam build --template template.yaml
sam local invoke "ReactiveFunction" --event events/hello.json
sam local start-api
```

## 🐳 Ejecución con Docker

### Imagen de la aplicación

```bash
# Construir imagen multi-stage (Java 25 + Spring Boot)
docker build -t reactive-lambda .

# Levantar la función como servicio WebFlux en localhost (usa 8081 si 8080 está ocupado)
docker run --rm -d -p 8081:8080 --name reactive-lambda reactive-lambda

# Smoke test sobre el endpoint y los actuators expuestos en el contenedor
curl -i "http://localhost:8081/hello?name=Marcos"
curl -i "http://localhost:8081/actuator/health"
```

Ejemplo de respuesta:

```
HTTP/1.1 200 OK
Content-Type: application/json
{"message":"ok","name":"Marcos","greeting":"Hello, Marcos!","timestamp":"2025-12-07T22:25:41.234Z"}
```

### Tooling containerizado (Gradle + SAM)

```bash
# Bash / zsh
docker run --rm -it \
  -v ${PWD}:/workspace \
  -w /workspace \
  public.ecr.aws/sam/build-java25:latest \
  bash -lc "./gradlew test"

docker run --rm -it \
  -v ${PWD}:/workspace \
  -w /workspace \
  public.ecr.aws/sam/build-java25:latest \
  bash -lc "sam build --template lambda-infra/template.yaml && sam validate"
```

```powershell
# PowerShell
docker run --rm -it `
  -v ${PWD}:/workspace `
  -w /workspace `
  public.ecr.aws/sam/build-java25:latest `
  bash -lc "./gradlew test"

docker run --rm -it `
  -v ${PWD}:/workspace `
  -w /workspace `
  public.ecr.aws/sam/build-java25:latest `
  bash -lc "sam build --template lambda-infra/template.yaml && sam validate"
```

## ☁️ Despliegue a AWS

```bash
cd lambda-infra
sam deploy --guided
```

## 🧪 Testing

```bash
./gradlew test                    # Todos los tests
./gradlew :lambda-core:test       # Solo lambda-core
./gradlew :lambda-tests:test      # Solo integración
```

## 🎯 Compilación Nativa

```bash
export JAVA_HOME=/path/to/graalvm-jdk-25
./gradlew :lambda-core:nativeCompile
```

## 📊 Observabilidad

```bash
# Health check
curl http://localhost:8080/actuator/health

# Métricas
curl http://localhost:8080/actuator/metrics
```

## 🐛 Troubleshooting

**Error: "Cannot find handler"**
```bash
./gradlew clean build
```

**LocalStack no responde**
```bash
docker-compose restart localstack
curl http://localhost:4566/_localstack/health
```

**GraalVM Native Build falla**
```bash
export GRADLE_OPTS="-Xmx4g"
./gradlew clean :lambda-core:nativeCompile
```

## 📁 Estructura del Proyecto

```
├── lambda-core/          # Código principal Lambda
├── lambda-infra/         # Template SAM
├── lambda-tests/         # Tests de integración
└── buildSrc/             # Convenciones Gradle
```

## 📚 Recursos

### 📖 Ebook
- 📚 [Amazon Kindle](https://www.amazon.com/dp/B0G1L1FFK6)
- 🛒 [Hotmart](https://go.hotmart.com/O102857613J?dp=1)
- 💳 [Gumroad](https://marcoslozina.gumroad.com/l/tporu)

### Documentación
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Cloud Function](https://spring.io/projects/spring-cloud-function)
- [AWS SAM](https://docs.aws.amazon.com/serverless-application-model/)
- [Project Reactor](https://projectreactor.io/docs/core/release/reference/)

## 💝 Apoyo al Proyecto

Si este proyecto te ha sido útil:

- 📖 **Obtén el ebook completo** - [Amazon](https://www.amazon.com/dp/B0G1L1FFK6) | [Hotmart](https://go.hotmart.com/O102857613J?dp=1) | [Gumroad](https://marcoslozina.gumroad.com/l/tporu)
- ☕ [Buy Me a Coffee](https://buymeacoffee.com/codefuel)
- 💳 [PayPal Donate](https://www.paypal.com/donate/?hosted_button_id=4TYGJ5S8CLX8J)
- ⭐ **Dale una estrella** al repositorio

¡Gracias por tu apoyo! 🙏

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Copyright (c) 2025 Marcos Raimundo Lozina. Todos los derechos reservados.

Este proyecto está protegido por derechos de autor. Ver el archivo [LICENSE](LICENSE) para detalles completos.

**Uso**: Solo para uso personal y educativo. Cualquier uso comercial requiere autorización previa.

## 👤 Autor

**Marcos Raimundo Lozina**

Creado como proyecto de referencia para microservicios reactivos serverless con Spring Boot y AWS Lambda.
