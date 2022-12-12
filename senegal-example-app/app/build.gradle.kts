plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation(project(":senegal-example-app:frontend-api"))
    implementation(project(":senegal-example-app:domain"))
    implementation(project(":senegal-example-app:persistence"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.hamcrest:hamcrest")

    testImplementation("org.reflections:reflections:0.10.2")
    testImplementation("org.springframework.data:spring-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    archiveFileName.set("senegal-spring-boot.jar")
    mainClass.set("")
}


springBoot {
    buildInfo()
}
