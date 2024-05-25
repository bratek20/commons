plugins {
    id("pl.bratek20.library-conventions")
}

dependencies {
    //event bus
    implementation(libs.guava)

    //spring context
    implementation(platform(libs.spring.boot.dependencies))
    implementation("org.springframework:spring-context")

    //guice
    implementation(libs.guice)
    implementation(libs.guice.multibindings)

    //properties
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.jackson.module.kotlin)

    testImplementation(project(":bratek20-tests"))

    testFixturesApi(project(":bratek20-tests"))
}