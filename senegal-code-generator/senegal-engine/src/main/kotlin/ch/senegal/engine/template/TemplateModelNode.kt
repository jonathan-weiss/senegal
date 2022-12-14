package ch.senegal.engine.template

open class TemplateModelNode (
    val properties: Map<String, Any>,
    val childNodes: List<TemplateModelNode>,
) {

    operator fun get(key: String): Any? {
        return properties[key]
    }

    fun getAllPropertyKeys(): List<String> {
        return properties.keys.sorted()
    }
}
