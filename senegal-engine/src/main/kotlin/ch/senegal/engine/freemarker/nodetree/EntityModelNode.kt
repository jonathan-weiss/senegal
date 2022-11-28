package ch.senegal.engine.freemarker.nodetree

class EntityModelNode (
    properties: Map<String, Any>,
    val entityAttributes: List<EntityAttributeModelNode>
): TemplateModelNode(properties) {

    companion object  {
        fun createEntityBuilder(): EntityModelNodeBuilder {
            val rootParentAccess = MutableTemplateModelNodeParentAccessor()
            rootParentAccess.parentNode = null
            return EntityModelNodeBuilder()
        }
    }


    class EntityModelNodeBuilder {
        private val properties = mutableMapOf<String, Any>()
        private val entityAttributeBuilders = mutableListOf<EntityAttributeModelNode.EntityAttributeModelNodeBuilder>()

        fun addProperty(key: String, value: Any): EntityModelNodeBuilder {
            properties[key] = value
            return this
        }

        fun createAndAttachSubNodeBuilder(): EntityAttributeModelNode.EntityAttributeModelNodeBuilder {
            val subNodeBuilder = EntityAttributeModelNode.EntityAttributeModelNodeBuilder()
            entityAttributeBuilders.add(subNodeBuilder)
            return subNodeBuilder
        }


        fun build(): EntityModelNode {
            val entityAttributes = entityAttributeBuilders.map { it.build() }
            return EntityModelNode(
                properties = this.properties,
                entityAttributes = entityAttributes
            )
        }

    }

}
