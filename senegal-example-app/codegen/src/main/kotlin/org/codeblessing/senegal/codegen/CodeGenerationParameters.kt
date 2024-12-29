package org.codeblessing.senegal.codegen

import java.nio.file.Path
import java.nio.file.Paths


object CodeGenerationParameters {

    // properties are configured in build.gradle.kts
    private const val XML_DEFINITION_FILE_PROPERTY_KEY = "xmlDefinitionFile"
    private const val OUTPUT_DIRECTORY_PROPERTY_KEY = "defaultOutputDirectory"


    private const val DOMAIN_PATH_PROPERTY_KEY = "placeholder.domainPath"
    private const val SHARED_DOMAIN_PROPERTY_KEY = "placeholder.sharedDomainPath"
    private const val PERSISTENCE_PATH_PROPERTY_KEY = "placeholder.persistencePath"
    private const val PERSISTENCE_RESOURCE_PATH_PROPERTY_KEY = "placeholder.persistenceResourcePath"
    private const val FRONTEND_API_PATH_PROPERTY_KEY = "placeholder.frontendApiPath"
    private const val FRONTEND_PATH_PROPERTY_KEY = "placeholder.frontendPath"


    fun xmlDefinitionFile(): Path {
        return Paths.get(System.getProperty(XML_DEFINITION_FILE_PROPERTY_KEY))
    }

    fun outputDirectory(): Path {
        return Paths.get(System.getProperty(OUTPUT_DIRECTORY_PROPERTY_KEY))
    }

    fun domainPath(): Path {
        return Paths.get(System.getProperty(DOMAIN_PATH_PROPERTY_KEY))
    }

    fun sharedDomainPath(): Path {
        return Paths.get(System.getProperty(SHARED_DOMAIN_PROPERTY_KEY))
    }

    fun frontendApiPath(): Path {
        return Paths.get(System.getProperty(FRONTEND_API_PATH_PROPERTY_KEY))
    }

    fun frontendPath(): Path {
        return Paths.get(System.getProperty(FRONTEND_PATH_PROPERTY_KEY))
    }

    fun persistencePath(): Path {
        return Paths.get(System.getProperty(PERSISTENCE_PATH_PROPERTY_KEY))
    }

    fun persistenceResourcePath(): Path {
        return Paths.get(System.getProperty(PERSISTENCE_RESOURCE_PATH_PROPERTY_KEY))
    }

}
