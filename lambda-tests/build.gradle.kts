dependencies {
    implementation(project(":lambda-core"))
    implementation("com.amazonaws:aws-lambda-java-events:3.12.0")
    testImplementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.1"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
}


