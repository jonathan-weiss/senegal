package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer

@JvmInline
value class ProjectName private constructor(val name: String) {

    companion object {
        fun of(name: String): ProjectName {
            NameEnforcer.isValidNameOrThrow(name)
            return ProjectName(name)
        }
    }
}
