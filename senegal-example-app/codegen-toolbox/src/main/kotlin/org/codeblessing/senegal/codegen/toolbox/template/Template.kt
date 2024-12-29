package org.codeblessing.senegal.codegen.toolbox.template

private const val INDENT = "    "

class TemplateContext {
    private val lines = mutableListOf<String>()
    private var indentLevel: Int

    private constructor(indentLevel: Int) {
        require(indentLevel >= 0) { "Indent cannot be negative, but was $indentLevel " }
        this.indentLevel = indentLevel
    }

    constructor() : this(0)

    fun build(): String {
        return lines.joinToString("\n") { it.trimEnd() }
    }

    operator fun String.unaryPlus() {
        lines.add(INDENT.repeat(indentLevel) + this)
    }

    fun indented(
        init: TemplateContext.() -> Unit,
    ) {
        indentLevel++
        init()
        indentLevel--
    }

    fun <T> forEach(
        values: List<T>,
        startWithBlankLineIfNotEmpty: Boolean = false,
        separateWithBlankLineIfNotEmpty: Boolean = false,
        endWithBlankLineIfNotEmpty: Boolean = false,
        init: TemplateContext.(T) -> Unit,
    ) {
        if (values.isEmpty()) {
            return
        }
        values.forEachIndexed { index, value ->
            val innerContext = TemplateContext(indentLevel = this.indentLevel)
            innerContext.init(value)
            if (innerContext.lines.isNotEmpty()) {
                if (index == 0 && startWithBlankLineIfNotEmpty) {
                    +""
                }
                if (index > 0 && separateWithBlankLineIfNotEmpty) {
                    +""
                }
                lines.addAll(innerContext.lines)
                if (endWithBlankLineIfNotEmpty && index == values.size - 1) {
                    +""
                }
            }
        }
    }

    fun include(
        toInclude: Template,
        startWithBlankLineIfNotEmpty: Boolean = false,
    ) {
        val innerContext = TemplateContext(indentLevel = this.indentLevel)
        toInclude.emitContent(innerContext)
        if (innerContext.lines.isEmpty()) {
            return
        }
        if (startWithBlankLineIfNotEmpty) {
            +""
        }
        lines.addAll(innerContext.lines)
    }
}

class Template(
    val emitContent: (TemplateContext) -> Unit,
) {

    fun build(): String {
        return TemplateContext().also { emitContent(it) }.build()
    }
}

fun template(init: TemplateContext.() -> Unit = {}): Template {
    return Template(init)
}
