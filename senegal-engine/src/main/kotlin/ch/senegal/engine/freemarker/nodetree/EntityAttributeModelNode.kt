package ch.senegal.engine.freemarker.nodetree

class EntityAttributeModelNode (
    properties: Map<String, Any>,
): TemplateModelNode(properties) {

    class EntityAttributeModelNodeBuilder {
        private val properties = mutableMapOf<String, Any>()

        fun addProperty(key: String, value: Any): EntityAttributeModelNodeBuilder {
            properties[key] = value
            return this
        }

        fun build(): EntityAttributeModelNode {
            return EntityAttributeModelNode(
                properties = this.properties
            )
        }

    }

}
