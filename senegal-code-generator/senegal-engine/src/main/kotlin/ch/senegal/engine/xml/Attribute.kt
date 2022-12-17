package ch.senegal.engine.xml

import org.xml.sax.Attributes

data class Attribute(
    val localName: String,
    val qName: String,
    val uri: String,
    val value: String,
    val type: String,
) {
    companion object {
        fun attributeList(attr: Attributes?): List<Attribute> {
            if (attr == null) return emptyList()
            return (0..(attr.length - 1)
                .coerceAtLeast(0))
                .map { index -> toAttribute(attr, index) }
        }

        private fun toAttribute(attr: Attributes, index: Int): Attribute {
            return Attribute(
                localName = attr.getLocalName(index),
                qName = attr.getQName(index),
                uri = attr.getURI(index),
                value = attr.getValue(index),
                type = attr.getType(index),
            )
        }

        fun attributesSummary(attr: Attributes?): String {
            return attributeList(attr)
                .joinToString("\n") { it.toString() }
        }

    }
}

