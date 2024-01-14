plugins {
    id("pl.bratek20.library-conventions")

    id("pl.bratek20.spring-conventions")
}

group = "pl.bratek20.spring"
version = "1.0.0"

dependencies {
    // context
    api("org.springframework:spring-context")

    implementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.flywaydb:flyway-mysql")


    // data runtimes
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // testcontainers
    testFixturesImplementation("org.testcontainers:testcontainers")
    testFixturesImplementation("org.testcontainers:junit-jupiter")
    testFixturesImplementation("org.testcontainers:mysql")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // web testing
    testImplementation("io.rest-assured:rest-assured:5.3.0")

}