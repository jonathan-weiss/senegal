package ch.senegal.engine.freemarker.nodetree

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

    companion object {
        fun createNodeBuilder(): TemplateModelNodeBuilder {
            return TemplateModelNodeBuilder()
        }
    }

}

