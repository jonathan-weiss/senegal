package ch.senegal.engine.freemarker.nodetree

class MutableTemplateModelNodeParentAccessor: TemplateModelNodeParentAccessor {
    var parentNode: TemplateModelNode? = null
    override fun getParent(): TemplateModelNode? {
        return parentNode
    }
}
