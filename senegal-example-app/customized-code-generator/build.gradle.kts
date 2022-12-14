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
val pathToPersistenceSource = projectDir.resolve("../persistence/src/main/kotlin-generated")
val pathToSharedDomainSource = projectDir.resolve("../shared-domain/src/main/kotlin-generated")
val pathToFrontendApiSource = projectDir.resolve("../frontend-api/src/main/kotlin-generated")
val pathToFrontendSource = projectDir.resolve("../frontend/src/generated")

tasks.register<Delete>("clearGeneratedSource") {
    delete(fileTree(pathToDomainSource).include("**/*"))
    delete(fileTree(pathToPersistenceSource).include("**/*"))
    delete(fileTree(pathToSharedDomainSource).include("**/*"))
    delete(fileTree(pathToFrontendApiSource).include("**/*"))
    delete(fileTree(pathToFrontendSource).include("**/*"))
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
        "-Dplaceholder.sharedDomainPath=${pathToSharedDomainSource.absolutePath}",
        "-Dplaceholder.persistencePath=${pathToPersistenceSource.absolutePath}",
        "-Dplaceholder.frontendApiPath=${pathToFrontendApiSource.absolutePath}",
        "-Dplaceholder.frontendPath=${pathToFrontendSource.absolutePath}",
    )
}

tasks.named("run") {
    dependsOn("clearGeneratedSource")
}
