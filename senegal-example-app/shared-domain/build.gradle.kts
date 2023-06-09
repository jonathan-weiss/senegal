plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation("org.springframework:spring-tx")
	implementation("org.springframework:spring-context")

	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.hamcrest:hamcrest")

}

