package ch.senegal.engine.parameters.sources

import ch.senegal.engine.parameters.ParameterSource

class StaticParameterSource(private val staticParameterSource: Map<String, String>): ParameterSource {

    override fun getParameterMap(): Map<String, String> {
        return staticParameterSource
    }
}
