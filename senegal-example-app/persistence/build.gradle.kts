// Convention Plugin for all Spring components (based on kotlin)
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.liquibase.gradle")  version "2.2.0"
}


val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true


dependencies {
    liquibaseRuntime("info.picocli:picocli:4.6.3")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.hsqldb:hsqldb")


    implementation(project(":senegal-example-app:shared-domain"))
    implementation(project(":senegal-example-app:domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.hsqldb:hsqldb")
    //implementation("org.jooq:jooq:3.18.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.hsqldb:hsqldb")
    testImplementation("org.liquibase:liquibase-core")
}

java.sourceSets["main"].java {
    srcDir("src/main/kotlin-generated")
}

java.sourceSets["main"].resources {
    srcDir("src/main/resources-generated")
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

tasks.register<Delete>("clearGeneratedSource") {
    delete(project.fileTree(projectDir.resolve("src/main/kotlin-generated")).include("**/*"))
    delete(project.fileTree(projectDir.resolve("src/main/resources-generated")).include("**/*"))
}
