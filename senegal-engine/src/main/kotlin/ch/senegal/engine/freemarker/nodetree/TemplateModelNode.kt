package ch.senegal.engine.freemarker.nodetree

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

    class TemplateModelNodeBuilder {
        private val properties = mutableMapOf<String, Any>()
        private val childNodeBuilders = mutableListOf<TemplateModelNodeBuilder>()

        fun addProperty(key: String, value: Any): TemplateModelNodeBuilder {
            properties[key] = value
            return this
        }

        fun createAndAttachChildNodeBuilder(): TemplateModelNodeBuilder {
            val subNodeBuilder = TemplateModelNodeBuilder()
            childNodeBuilders.add(subNodeBuilder)
            return subNodeBuilder
        }


        fun build(): TemplateModelNode {
            val childNodes = childNodeBuilders.map { it.build() }
            return TemplateModelNode(
                properties = this.properties,
                childNodes = childNodes
            )
        }

    }

    companion object {
        fun createNodeBuilder(): TemplateModelNodeBuilder {
            return TemplateModelNodeBuilder()
        }
    }

}
