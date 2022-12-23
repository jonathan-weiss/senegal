package ch.senegal.engine.parameters

class ParameterReader(private val parameterSources: List<ParameterSource>) {

    fun getParameterList(): List<String> {
        return ConfigParameterNames.allParameters().map { "${it.propertyName}=${getParameter(it)}" }
    }

    fun <T : Any> getParameter(key: ConfigParameterName<T>): T {
        return parameterSources
            .map { parameterSource -> parameterSource.getParameterMap() }
            .firstNotNullOfOrNull { map -> map[key.propertyName] }
            ?.let { value -> key.fromString(value) }
            ?: throw IllegalArgumentException("No value found for key '${key.propertyName}' in sources $parameterSources")
    }

    fun getPlaceholders(): Map<String, String> {
        return parameterSources
            .reversed()
            .map { parameterSource -> getPlaceholdersForParameterSource(parameterSource) }
            .fold(emptyMap()) { acc: Map<String, String>, current: Map<String, String> -> acc + current }
    }

    private fun getPlaceholdersForParameterSource(parameterSource: ParameterSource): Map<String, String> {
        val placeholderPropertySearchPattern = "${StringConfigParameterName.Placeholder.propertyName}."
        return parameterSource.getParameterMap()
            .entries
            .filter { it.key.startsWith(placeholderPropertySearchPattern) }
            .associate { Pair(it.key.substring(placeholderPropertySearchPattern.length), it.value) }
    }
}
