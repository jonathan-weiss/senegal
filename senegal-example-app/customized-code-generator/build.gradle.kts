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
    implementation(project(":senegal-code-generator:senegal-plugin-api"))
    implementation(project(":senegal-code-generator:senegal-engine"))
}

val pathToDomainSource = projectDir.resolve("../domain/src/main/kotlin-generated")

tasks.register<Delete>("clearGeneratedSource") {
    delete(fileTree(pathToDomainSource).include("**/*"))
}




application {
    val defaultGeneratedSourcePath = buildDir
    val definitionsDirectory = projectDir.resolve("definitions")
    mainClass.set("ch.senegal.engine.process.SenegalApplicationKt")
    applicationDefaultJvmArgs = listOf(
        "-DdefinitionDirectory=${definitionsDirectory.absolutePath}",
        "-DdefaultOutputDirectory=${defaultGeneratedSourcePath.absolutePath}",
        "-DxmlDefinitionFile=${definitionsDirectory.resolve("customized.xml").absolutePath}",
        "-Dplaceholder.domainPath=${pathToDomainSource.absolutePath}",
    )
}

tasks.named("run") {
    dependsOn("clearGeneratedSource")
}
