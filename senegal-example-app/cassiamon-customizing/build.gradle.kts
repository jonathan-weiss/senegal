plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":cassiamon-code-generator:cassiamon-api"))
    implementation(project(":cassiamon-code-generator:cassiamon-tools"))
    runtimeOnly(project(":cassiamon-code-generator:cassiamon-engine"))
    runtimeOnly(project(":cassiamon-code-generator:cassiamon-xml-schemagic"))
}

val pathToDomainSource = projectDir.resolve("../domain/src/main/kotlin-generated")
val pathToPersistenceSource = projectDir.resolve("../persistence/src/main/kotlin-generated")
val pathToPersistenceResource = projectDir.resolve("../persistence/src/main/resources-generated")
val pathToSharedDomainSource = projectDir.resolve("../shared-domain/src/main/kotlin-generated")
val pathToFrontendApiSource = projectDir.resolve("../frontend-api/src/main/kotlin-generated")
val pathToFrontendSource = projectDir.resolve("../frontend/src/generated")


tasks.named("run") {
    enabled = true
}

application {
    mainClass.set("org.codeblessing.sourceamazing.engine.SourceamazingApplicationKt")

    val defaultGeneratedSourcePath = projectDir.resolve("output-data")
    val definitionsDirectory = projectDir.resolve("definitions")

    applicationDefaultJvmArgs = listOf(
        "-DdefaultOutputDirectory=${defaultGeneratedSourcePath.absolutePath}",
        "-DxmlDefinitionFile=${definitionsDirectory.resolve("senegal-customized.xml").absolutePath}",
        "-Dplaceholder.domainPath=${pathToDomainSource.absolutePath}",
        "-Dplaceholder.sharedDomainPath=${pathToSharedDomainSource.absolutePath}",
        "-Dplaceholder.persistencePath=${pathToPersistenceSource.absolutePath}",
        "-Dplaceholder.persistenceResourcePath=${pathToPersistenceResource.absolutePath}",
        "-Dplaceholder.frontendApiPath=${pathToFrontendApiSource.absolutePath}",
        "-Dplaceholder.frontendPath=${pathToFrontendSource.absolutePath}",
    )

}

tasks.named("run") {
    dependsOn(":senegal-example-app:domain:clearGeneratedSource")
    dependsOn(":senegal-example-app:frontend:clearGeneratedSource")
    dependsOn(":senegal-example-app:frontend-api:clearGeneratedSource")
    dependsOn(":senegal-example-app:persistence:clearGeneratedSource")
    dependsOn(":senegal-example-app:domain:clearGeneratedSource")
}

tasks.register("generate") {
    dependsOn("run")
}
