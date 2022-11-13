// Convention Plugin for all Spring components (based on kotlin)
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.liquibase.gradle")  version "2.1.1"
}


val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true


dependencies {
    liquibaseRuntime("info.picocli:picocli:4.6.3")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.hsqldb:hsqldb")


    implementation(project(":shared-domain"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hsqldb:hsqldb")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.hsqldb:hsqldb")
    testImplementation("org.liquibase:liquibase-core")
}

val liquibaseChangelogFiles = buildDir.resolve("liquibase-changelog-files")

liquibase {

    val liquibasePropertiesFile = projectDir.resolve("liquibase.properties")
    val changeLogFile = projectDir.resolve("src/main/resources/db/changelog/db.changelog.senegal.xml")

    activities.register("senegal") {
        this.arguments = mapOf(
            "changeLogFile" to rootDir.toURI().relativize(changeLogFile.toURI()), // only relative paths allowed
            "url" to project.properties["LIQUIBASE_DB_URL"],
            "username" to project.properties["LIQUIBASE_DB_USER"],
            "password" to project.properties["LIQUIBASE_DB_PASSWORD"],
            "driver" to project.properties["LIQUIBASE_DB_DRIVER"],
            "defaultsFile" to liquibasePropertiesFile.absolutePath,
        )
    }
    runList = "senegal"
}
