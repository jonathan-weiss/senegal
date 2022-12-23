package ch.senegal.engine.process

import ch.senegal.engine.parameters.ParameterSource

class StaticParameterSource(private val staticParameterMap: Map<String, String>): ParameterSource {
    override fun getParameterMap(): Map<String, String> {
        return staticParameterMap
    }
}
