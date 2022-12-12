plugins {
    kotlin("jvm")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(project(":senegal-code-generator:senegal-plugin-api"))
    implementation("org.freemarker:freemarker:2.3.31")


    testImplementation(project(":senegal-code-generator:senegal-plugin-api"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
    testImplementation("org.hamcrest:hamcrest:2.2")

}



tasks.named<Test>("test") {
    useJUnitPlatform()
}
