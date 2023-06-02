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
val pathToPersistenceResource = projectDir.resolve("../persistence/src/main/resources/generated")
val pathToSharedDomainSource = projectDir.resolve("../shared-domain/src/main/kotlin-generated")
val pathToFrontendApiSource = projectDir.resolve("../frontend-api/src/main/kotlin-generated")
val pathToFrontendSource = projectDir.resolve("../frontend/src/generated")

tasks.register<Delete>("clearGeneratedSource") {
    // TODO enable deletion as soon as cassiamon is generating the template
//    delete(fileTree(pathToDomainSource).include("**/*"))
//    delete(fileTree(pathToPersistenceSource).include("**/*"))
//    delete(fileTree(pathToPersistenceResource).include("**/*"))
//    delete(fileTree(pathToSharedDomainSource).include("**/*"))
//    delete(fileTree(pathToFrontendApiSource).include("**/*"))
//    delete(fileTree(pathToFrontendSource).include("**/*"))
}



tasks.named("run") {
    enabled = true
}

application {
    mainClass.set("ch.cassiamon.engine.CassiamonApplicationKt")

    val defaultGeneratedSourcePath = projectDir.resolve("output-data")
    val definitionsDirectory = projectDir.resolve("definitions")

    applicationDefaultJvmArgs = listOf(
        "-DdefaultOutputDirectory=${defaultGeneratedSourcePath.absolutePath}",
        "-DxmlDefinitionFile=${definitionsDirectory.resolve("cassiamon-customized.xml").absolutePath}",
        "-Dplaceholder.domainPath=${pathToDomainSource.absolutePath}",
        "-Dplaceholder.sharedDomainPath=${pathToSharedDomainSource.absolutePath}",
        "-Dplaceholder.persistencePath=${pathToPersistenceSource.absolutePath}",
        "-Dplaceholder.persistenceResourcePath=${pathToPersistenceResource.absolutePath}",
        "-Dplaceholder.frontendApiPath=${pathToFrontendApiSource.absolutePath}",
        "-Dplaceholder.frontendPath=${pathToFrontendSource.absolutePath}",
    )

}

tasks.named("run") {
    dependsOn("clearGeneratedSource")
}
