package ch.senegal.engine.properties

interface ParameterSource {

    fun getParameterValue(parameterName: SenegalParameterName<*>): String?
}
