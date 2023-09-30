import org.springframework.boot.gradle.tasks.bundling.BootJar


plugins {
    id("org.springframework.boot") version "2.7.5"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:2.7.5")
        }
        dependencies {
            dependency("org.junit.jupiter:junit-jupiter:5.9.1")
            dependency("org.mockito:mockito-core:4.6.1")
            dependency("org.mockito:mockito-junit-jupiter:3.12.4")
            dependency("org.hamcrest:hamcrest:2.2")

        }
    }
}
