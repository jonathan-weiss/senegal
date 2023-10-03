package org.codeblessing.senegal.customizing

import org.codeblessing.sourceamazing.api.parameter.ParameterAccess
import java.nio.file.Path
import java.nio.file.Paths


object SenegalAppParameters {

    // parameters are configured in build.gradle.kts

    private const val xmlDefinitionFileParam = "xmlDefinitionFile"
    private const val outputDirectoryParam = "defaultOutputDirectory"


    private const val domainPath = "placeholder.domainPath"
    private const val sharedDomainPath = "placeholder.sharedDomainPath"
    private const val persistencePath = "placeholder.persistencePath"
    private const val persistenceResourcePath = "placeholder.persistenceResourcePath"
    private const val frontendApiPath = "placeholder.frontendApiPath"
    private const val frontendPath = "placeholder.frontendPath"


    fun xmlDefinitionFile(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(xmlDefinitionFileParam))
    }

    fun outputDirectory(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(outputDirectoryParam))
    }

    fun domainPath(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(domainPath))
    }

    fun sharedDomainPath(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(sharedDomainPath))
    }

    fun frontendApiPath(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(frontendApiPath))
    }

    fun frontendPath(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(frontendPath))
    }

    fun persistencePath(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(persistencePath))
    }

    fun persistenceResourcePath(parameterAccess: ParameterAccess): Path {
        return Paths.get(parameterAccess.getParameter(persistenceResourcePath))
    }

}
