import ch.senegal.gradle.GradlePropertyHelper

val localPropertiesFile = rootDir.resolve("gradle_local.properties")
val localProperties = GradlePropertyHelper.loadLocalProperties(localPropertiesFile)
allprojects {
    GradlePropertyHelper.addPropertiesToProject(localProperties, project)
}
