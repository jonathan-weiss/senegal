package ch.senegal.engine.properties

object ParameterReader {

    private val parameterSources = listOf<ParameterSource>(
        // TODO Add here system property source
        // TODO Add here environment variable source
        PropertyParameterSource,
    )

    fun <T : Any> getParameter(key: SenegalParameterName<T>): T {
        return parameterSources
            .firstNotNullOfOrNull { parameterSource -> parameterSource.getParameterValue(key) }
            ?.let { value -> key.fromString(value) }
            ?: throw IllegalArgumentException("No value found for key '${key.propertyName}' in sources $parameterSources")
    }
}
