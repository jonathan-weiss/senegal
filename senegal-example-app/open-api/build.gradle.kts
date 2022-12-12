import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springdoc.openapi-gradle-plugin") version "1.3.4"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.11")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.11")

    //used for mocking inside of the spring container
    implementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    //needed rest api projects
    implementation(project(":senegal-example-app:frontend-api"))
}

val bootJar: BootJar by tasks
bootJar.enabled = true

val openApiSpecOutputDir = buildDir
val openApiSpecName = "senegal-frontend-api.yaml"

// start/stop polling for URL defined in apiDocsUrl
val startPollingSeconds = 5
val stopPollingSeconds = 15

openApi {
    apiDocsUrl.set("http://localhost:8081/v3/api-docs.yaml")
    outputDir.set(openApiSpecOutputDir)
    outputFileName.set(openApiSpecName)
    //can be optimized regarding the machine speed
    waitTimeInSeconds.set(stopPollingSeconds)
}

project.afterEvaluate {
    // tasks 'generateOpenApiDocs' is only available at the afterEvaluation phase
    // see https://github.com/springdoc/springdoc-openapi-gradle-plugin/blob/master/src/main/kotlin/org/springdoc/openapi/gradle/plugin/OpenApiGradlePlugin.kt
    tasks.named<Task>("generateOpenApiDocs") {
        inputs.files(rootProject.projectDir.resolve("frontend-api").resolve("src/main/kotlin"))
        outputs.file(openApiSpecOutputDir.resolve(openApiSpecName))

        doFirst {
            println("Wait for spring boot to be loaded...")
            TimeUnit.SECONDS.sleep(startPollingSeconds.toLong())
            println("Spring boot loaded.")
        }

    }
}

tasks.named("generateBackend") {
    dependsOn("generateOpenApiDocs")
}
