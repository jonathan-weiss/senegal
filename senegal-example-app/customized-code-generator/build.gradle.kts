plugins {
    kotlin("jvm")
    application
}

allprojects {
    repositories {
        mavenCentral()
    }
}

java.sourceSets["main"].java {
    srcDir("src/generated/kotlin")
}

dependencies {
    implementation(project(":senegal-code-generator:senegal-plugin-api"))
    implementation(project(":senegal-code-generator:senegal-engine"))
}

tasks.register<Delete>("clearGeneratedSource") {
    delete(projectDir.resolve("src/generated/kotlin"))
}

tasks.named("run") {
    dependsOn("clearGeneratedSource")
}


application {
    val projectDirectory = projectDir.toPath()
    val definitionsDirectory = projectDirectory.resolve("definitions")
    mainClass.set("ch.senegal.engine.process.SenegalApplicationKt")
    applicationDefaultJvmArgs = listOf(
        "-DdefinitionDirectory=${definitionsDirectory.toAbsolutePath()}",
        "-DdefaultOutputDirectory=${projectDirectory.resolve("../build").toAbsolutePath()}",
        "-DxmlDefinitionFile=${definitionsDirectory.resolve("customized.xml").toAbsolutePath()}",
    )
}
