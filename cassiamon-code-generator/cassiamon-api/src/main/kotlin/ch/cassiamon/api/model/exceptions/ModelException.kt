package ch.cassiamon.api.model.exceptions

abstract class ModelException(msg: String, cause: Exception?): RuntimeException(msg, cause) {
    constructor(msg: String): this(msg, null)
}
