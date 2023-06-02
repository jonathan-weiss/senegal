package ch.cassiamon.exampleapp.customizing

import ch.cassiamon.api.parameter.ParameterAccess
import java.nio.file.Path
import java.nio.file.Paths


object ExampleAppParameters {

    // parameters are configured in build.gradle.kts

    private const val xmlDefinitionFileParam = "xmlDefinitionFile"
    private const val outputDirectoryParam = "defaultOutputDirectory"

    fun xmlDefinitionFile(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(xmlDefinitionFileParam))
    }

    fun outputDirectory(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(outputDirectoryParam))
    }
}
