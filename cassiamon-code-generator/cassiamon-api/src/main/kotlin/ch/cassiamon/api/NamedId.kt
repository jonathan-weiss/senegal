package ch.cassiamon.api

import ch.cassiamon.api.rules.NameEnforcer

abstract class NamedId protected constructor(name: String): ComparableId(name) {

    init {
        NameEnforcer.isValidNameOrThrow(name)
    }
}
