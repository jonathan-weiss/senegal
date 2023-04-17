package ch.cassiamon.engine.parameters

import ch.cassiamon.engine.parameters.ParameterSource

class StaticParameterSource(private val staticParameterSource: Map<String, String>): ParameterSource {

    override fun getParameterMap(): Map<String, String> {
        return staticParameterSource
    }
}
