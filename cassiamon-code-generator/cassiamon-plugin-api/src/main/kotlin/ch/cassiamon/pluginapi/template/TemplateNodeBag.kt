package ch.cassiamon.pluginapi.template

data class TemplateNodeBag constructor(val nodes: List<TemplateNode>) {
    constructor(node: TemplateNode): this(listOf(node))

    val asSingleTemplateNode: TemplateNode = nodes.first()
}
