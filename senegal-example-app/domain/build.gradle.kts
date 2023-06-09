plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework:spring-context")
    implementation(project(":senegal-example-app:shared-domain"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.hamcrest:hamcrest")
//    testImplementation(project(":shared-test"))

}

java.sourceSets["main"].java {
    srcDir("src/main/kotlin-generated")
}

tasks.register<Delete>("clearGeneratedSource") {
    delete(project.fileTree(projectDir.resolve("src/main/kotlin-generated")).include("**/*"))
}
