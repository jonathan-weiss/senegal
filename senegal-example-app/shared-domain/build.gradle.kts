plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation("org.springframework:spring-tx")
	implementation("org.springframework:spring-context")
//    implementation(project(":shared-domain"))

	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.hamcrest:hamcrest")
//    testImplementation(project(":shared-test"))

}

java.sourceSets["main"].java {
	srcDir("src/main/kotlin-generated")
}

