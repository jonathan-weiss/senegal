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
    implementation(project(":senegal-plugin-api"))
    implementation(project(":senegal-engine"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
    testImplementation("org.hamcrest:hamcrest:2.2")

}



tasks.named<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("ch.senegal.engine.process.SenegalApplicationKt")
}
