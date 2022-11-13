package ch.senegal.gradle

import org.gradle.api.Project
import org.gradle.api.invocation.Gradle
import java.io.File

object GradlePropertyHelper {

    fun loadLocalProperties(propsFile: File): java.util.Properties {
        val props = java.util.Properties()
        if(propsFile.exists()) {
            java.io.FileInputStream(propsFile).use { inputStream ->
                props.load(inputStream)
            }
        }
        return props
    }

    // overwriteExisting=true may overwrite properties from command line (-Dfoo=bar)
    fun addPropertiesToProject(props: java.util.Properties, project: Project) {
        val replacements: Map<String, String> = mapOf(
                Pair("rootDir", project.rootDir.toString())
        )
        props.forEach { key: Any, value: Any ->
            // do not add the key if it is a CLI parameter, as those have the most high priority.
            if(key is String && value is String && !isCliParameter(key, project.gradle)) {
                val replacedValue = replacePlaceholders(value, replacements)
                project.setProperty(key, replacedValue)
            }
        }
    }

    private fun isCliParameter(propertyKey: String, gradle: Gradle): Boolean {
        return gradle.startParameter.projectProperties.containsKey(propertyKey)
    }

    internal fun replacePlaceholders(value: String, replacements: Map<String, String>): String {
        var newValue = value
        for((placeholder, replacement) in replacements) {
            newValue = newValue.replace("\${$placeholder}", replacement)
        }
        return newValue
    }
}
