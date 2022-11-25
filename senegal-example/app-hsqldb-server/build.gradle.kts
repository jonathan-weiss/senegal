plugins {
	id("org.springframework.boot")
	kotlin("jvm")
	kotlin("plugin.spring")
	id("io.spring.dependency-management")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.hsqldb:hsqldb")
}

