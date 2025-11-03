plugins {
    java
    id("io.spring.dependency-management") version "1.1.5"
    id("org.springframework.boot") version "3.3.1" apply false
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
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.1")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


