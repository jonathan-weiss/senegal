plugins {
  id("com.github.node-gradle.node") version ("3.2.1")
  id("org.openapi.generator") version "6.0.1"
}

val nodeAndNpmBaseDownloadDir: File = project.buildDir
node {
  // Version of node to use.
  version.set("14.17.0")

  // Version of npm to use.
  npmVersion.set("6.14.13")

  // Base URL for fetching node distributions (change if you have a mirror).
  //distBaseUrl.set("https://nodejs.org/dist")
  distBaseUrl.set("http://odyssey.rowini.net/artifactory/list/nodejs.org")
  // Specifies whether it is acceptable to communicate with the Node.js repository over an insecure HTTP connection.
  // Only used if download is true
  // Change it to true if you use a mirror that uses HTTP rather than HTTPS
  // Or set to null if you want to use Gradle's default behaviour.
  allowInsecureProtocol.set(true)

  // If true, it will download node using above parameters.
  // If false, it will try to use globally installed node.
  download.set(true)

  // Set the work directory for unpacking node
  workDir.set(nodeAndNpmBaseDownloadDir.resolve("nodejs"))

  // Set the work directory for NPM
  npmWorkDir.set(nodeAndNpmBaseDownloadDir.resolve("npm"))

  // The Node.js project directory location
  // This is where the package.json file and node_modules directory are located
  // By default it is at the root of the current project
  nodeProjectDir.set(project.projectDir)

  // Whether the plugin automatically should add the proxy configuration to npm and yarn commands
  // according the proxy configuration defined for Gradle
  // Disable this option if you want to configure the proxy for npm or yarn on your own
  // (in the .npmrc file for instance)
  nodeProxySettings.set(com.github.gradle.node.npm.proxy.ProxySettings.OFF)

}

val angularFrontendDirectory = project.projectDir

tasks.named<com.github.gradle.node.npm.task.NpmInstallTask>("npmInstall") {
  mustRunAfter("clearNodeModules")

  inputs.file(angularFrontendDirectory.resolve("package.json"))
  outputs.dir(angularFrontendDirectory.resolve("node_modules"))

}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("buildAngular") {
  description = "Build the angular frontend (into the dist directory)"

  mustRunAfter("npmInstall")
  mustRunAfter("clearAngularDist")
  dependsOn("openApiCopyGeneratedFiles")

  inputs.file(angularFrontendDirectory.resolve("package.json"))
  inputs.file(angularFrontendDirectory.resolve("angular.json"))
  inputs.dir(angularFrontendDirectory.resolve("src"))
  outputs.dir(angularFrontendDirectory.resolve("dist"))

  npmCommand.set(listOf("run", "build"))
}

val openApiSpec = projectDir.resolve("../open-api/build/senegal-frontend-api.yaml")
val openApiOutputDir = buildDir.resolve("generated-openapi-files")
val openApiDestinationDir = projectDir.resolve("src/generated-openapi")

openApiGenerate {
  generatorName.set("typescript-angular")
  inputSpec.set(openApiSpec.absolutePath)
  outputDir.set(openApiOutputDir.absolutePath)
  apiPackage.set("api")
  modelPackage.set("model")
}

tasks.register<Delete>("openApiCleanGeneratedFiles") {
  delete(openApiOutputDir)
  delete(openApiDestinationDir)
}

tasks.register<Copy>("openApiCopyGeneratedFiles") {
  dependsOn("openApiGenerate")
  mustRunAfter("openApiCleanGeneratedFiles")

  from(openApiOutputDir)
  include("api/*")
  include("model/*")
  include("api.module.ts", "configuration.ts", "encoder.ts", "index.ts", "variables.ts")
  into(openApiDestinationDir)
}

tasks.named<Task>("openApiGenerate") {
  mustRunAfter("openApiCleanGeneratedFiles")
  dependsOn(":senegal-example-app:open-api:generateOpenApiDocs")
}

tasks.register<Delete>("clearNodeModules") {
  delete(angularFrontendDirectory.resolve("node_modules"))
}

tasks.register<Delete>("clearAngularDist") {
  delete(angularFrontendDirectory.resolve("dist"))
}


tasks.register("clearFrontend") {
  // we do not call the task clearNodeModules, as npmInstall does clear unused modules itself.
  // (analogous to the backend where we do not clear the gradle cache with all dependencies either)
  dependsOn("openApiCleanGeneratedFiles")
  dependsOn("clearAngularDist")
}

tasks.register("generate") {
  dependsOn("openApiCopyGeneratedFiles")
}

tasks.register<Delete>("clearGeneratedSource") {
  delete(project.fileTree(projectDir.resolve("src/generated")).include("**/*"))
  delete(project.fileTree(projectDir.resolve("src/generated-openapi")).include("**/*"))
}
