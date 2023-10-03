plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-context")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

    implementation(project(":domain"))
    implementation(project(":shared-domain"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.hamcrest:hamcrest")

}

java.sourceSets["main"].java {
    srcDir("src/main/kotlin-generated")
}

tasks.register<Delete>("clearGeneratedSource") {
    delete(project.fileTree(projectDir.resolve("src/main/kotlin-generated")).include("**/*"))
}
