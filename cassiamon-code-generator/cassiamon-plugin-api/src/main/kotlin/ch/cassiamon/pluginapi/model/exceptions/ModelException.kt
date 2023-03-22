package ch.cassiamon.pluginapi.model.exceptions

abstract class ModelException(msg: String, cause: Exception?): RuntimeException(msg, cause) {
    constructor(msg: String): this(msg, null)
}
