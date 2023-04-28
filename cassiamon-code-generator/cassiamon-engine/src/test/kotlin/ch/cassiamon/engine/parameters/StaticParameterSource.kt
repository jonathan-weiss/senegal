package ch.cassiamon.engine.parameters


class StaticParameterSource(private val staticParameterSource: Map<String, String>): ParameterSource {

    override fun getParameterMap(): Map<String, String> {
        return staticParameterSource
    }
}
