import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("org.graalvm.buildtools.native")
}

dependencies {
    // Spring Boot WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    
    // Bean Validation - FALTANTE AGREGADO
    implementation("org.springframework.boot:spring-boot-starter-validation")
    
    // Spring Cloud Function
    implementation("org.springframework.cloud:spring-cloud-function-context")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2023.0.1"))
    
    // AWS Lambda Events
    implementation("com.amazonaws:aws-lambda-java-events:3.12.0")
    implementation("com.amazonaws:aws-lambda-java-serialization:1.1.2")
    
    // JSON Processing
    implementation("com.fasterxml.jackson.core:jackson-databind")
    
    // Observability
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-core")
    
    // Logging
    implementation("ch.qos.logback:logback-classic")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.mockito:mockito-core")
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("lambda-core")
            mainClass.set("org.springframework.cloud.function.adapter.aws.FunctionInvoker")
            buildArgs.addAll(
                listOf(
                    "--no-fallback",
                    "--initialize-at-build-time",
                    "--initialize-at-run-time=org.springframework.cloud.function.adapter.aws.FunctionInvoker",
                    "--enable-url-protocols=http,https",
                    "-H:+IncludeAllLocales",
                    "-H:+ReportExceptionStackTraces",
                    "-H:IncludeResources=.*\\.properties",
                    "-H:IncludeResources=.*\\.yml",
                    "-H:IncludeResources=.*\\.yaml",
                    "-H:IncludeResources=.*\\.json",
                    "-H:IncludeResources=META-INF/native-image/.*"
                )
            )
        }
    }
}

tasks.named("nativeCompile") {
    dependsOn("bootJar")
}

tasks.withType<BootJar> {
    archiveFileName.set("lambda-core.jar")
}


