plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-context")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

    implementation(project(":senegal-example-app:domain"))
    implementation(project(":senegal-example-app:shared-domain"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.hamcrest:hamcrest")

}

java.sourceSets["main"].java {
    srcDir("src/main/kotlin-generated")
}

