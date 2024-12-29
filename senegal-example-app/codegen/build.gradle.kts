plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val sourceAmazingVersion = "3.1.0"
dependencies {
    implementation(project(":codegen-toolbox"))
    implementation("org.codeblessing.sourceamazing:sourceamazing-schema-api:$sourceAmazingVersion")
    runtimeOnly("org.codeblessing.sourceamazing:sourceamazing-schema:$sourceAmazingVersion")
    implementation("org.codeblessing.sourceamazing:sourceamazing-xml-schema-api:$sourceAmazingVersion")
    runtimeOnly("org.codeblessing.sourceamazing:sourceamazing-xml-schema:$sourceAmazingVersion")


    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    testImplementation("org.junit.jupiter:junit-jupiter")
}

val pathToDomainSource = projectDir.resolve("../domain/src/main/kotlin-generated")
val pathToPersistenceSource = projectDir.resolve("../persistence/src/main/kotlin-generated")
val pathToPersistenceResource = projectDir.resolve("../persistence/src/main/resources-generated")
val pathToSharedDomainSource = projectDir.resolve("../shared-domain/src/main/kotlin-generated")
val pathToFrontendApiSource = projectDir.resolve("../frontend-api/src/main/kotlin-generated")
val pathToFrontendSource = projectDir.resolve("../frontend/src/generated")

val defaultGeneratedSourcePath = projectDir.resolve("output-data")
val definitionsDirectory = projectDir.resolve("definitions")


tasks.register<JavaExec>("generate") {
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("org.codeblessing.senegal.codegen.CodeGenerationKt")
    systemProperty("defaultOutputDirectory", defaultGeneratedSourcePath.absolutePath)
    systemProperty("xmlDefinitionFile", definitionsDirectory.resolve("senegal-customized.xml").absolutePath)
    systemProperty("placeholder.domainPath", pathToDomainSource.absolutePath)
    systemProperty("placeholder.sharedDomainPath", pathToSharedDomainSource.absolutePath)
    systemProperty("placeholder.persistencePath", pathToPersistenceSource.absolutePath)
    systemProperty("placeholder.persistenceResourcePath", pathToPersistenceResource.absolutePath)
    systemProperty("placeholder.frontendApiPath", pathToFrontendApiSource.absolutePath)
    systemProperty("placeholder.frontendPath", pathToFrontendSource.absolutePath)
}