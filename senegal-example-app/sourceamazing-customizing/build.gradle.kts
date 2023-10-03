plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.codeblessing:sourceamazing-api")
    implementation("org.codeblessing:sourceamazing-tools")
    runtimeOnly("org.codeblessing:sourceamazing-engine")
    runtimeOnly("org.codeblessing:sourceamazing-xml-schema")
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
    dependsOn(":domain:clearGeneratedSource")
    dependsOn(":frontend:clearGeneratedSource")
    dependsOn(":frontend-api:clearGeneratedSource")
    dependsOn(":persistence:clearGeneratedSource")
    dependsOn(":domain:clearGeneratedSource")
}

tasks.register("generate") {
    dependsOn("run")
}
