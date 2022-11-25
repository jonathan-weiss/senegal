plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework:spring-context")
    implementation(project(":senegal-example:shared-domain"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.hamcrest:hamcrest")
//    testImplementation(project(":shared-test"))

}
