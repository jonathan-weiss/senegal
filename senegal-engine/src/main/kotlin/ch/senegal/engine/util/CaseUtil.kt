package ch.senegal.engine.util

object CaseUtil {
    private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
    private val snakeRegex = "_[a-zA-Z]".toRegex()

    fun camelToDashCase(stringValue: String): String {
        return camelRegex.replace(stringValue) {
            "-${it.value}"
        }.lowercase()
    }


    fun camelToSnakeCase(stringValue: String): String {
        return camelRegex.replace(stringValue) {
            "_${it.value}"
        }.lowercase()
    }

    fun snakeToLowerCamelCase(stringValue: String): String {
        return snakeRegex.replace(stringValue) {
            it.value.replace("_","")
                .uppercase()
        }
    }

    fun snakeToUpperCamelCase(stringValue: String): String {
        return snakeToLowerCamelCase(stringValue).replaceFirstChar { it.titlecase() }
    }


}
