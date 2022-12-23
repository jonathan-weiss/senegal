package ch.senegal.engine.parameters

interface ParameterSource {

    fun getParameterValue(parameterName: SenegalParameterName<*>): String?
    fun getParameterMap(parameterName: SenegalParameterName<*>): Map<String,String>
}
