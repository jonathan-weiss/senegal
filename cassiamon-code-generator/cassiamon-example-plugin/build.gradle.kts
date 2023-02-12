plugins {
    kotlin("jvm")
    application
}

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(project(":cassiamon-code-generator:cassiamon-plugin-api"))
    implementation(project(":cassiamon-code-generator:cassiamon-engine"))


    testImplementation(project(":cassiamon-code-generator:cassiamon-plugin-api"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
    testImplementation("org.hamcrest:hamcrest:2.2")

}



tasks.named<Test>("test") {
    useJUnitPlatform()
}


tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named("run") {
    enabled = true
}

application {
    mainClass.set("ch.cassiamon.engine.CassiamonApplicationKt")
}
