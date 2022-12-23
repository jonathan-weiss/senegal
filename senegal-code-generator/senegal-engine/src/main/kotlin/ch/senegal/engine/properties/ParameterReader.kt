package ch.senegal.engine.properties

class ParameterReader(private val parameterSources: List<ParameterSource>) {

    fun getParameterList(): List<String> {
        return ParameterNames.allParameters().map { "${it.propertyName}=${getParameter(it)}" }
    }

    fun <T : Any> getParameter(key: SenegalParameterName<T>): T {
        return parameterSources
            .firstNotNullOfOrNull { parameterSource -> parameterSource.getParameterValue(key) }
            ?.let { value -> key.fromString(value) }
            ?: throw IllegalArgumentException("No value found for key '${key.propertyName}' in sources $parameterSources")
    }

    fun getPlaceholders(): Map<String, String> {
        return parameterSources
            .reversed()
            .map { it.getParameterMap(StringParameterName.Placeholder) }
            .fold(emptyMap()) { acc: Map<String, String>, current: Map<String, String> -> acc + current }
    }
}
