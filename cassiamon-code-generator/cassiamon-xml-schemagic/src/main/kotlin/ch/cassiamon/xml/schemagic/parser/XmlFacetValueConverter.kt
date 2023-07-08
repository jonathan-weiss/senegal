package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.api.process.schema.FacetSchema
import ch.cassiamon.api.process.schema.FacetTypeEnum

object XmlFacetValueConverter {
    fun convertString(facetSchema: FacetSchema, attributeValue: String): Any? {
        return when(facetSchema.facetType) {
            FacetTypeEnum.TEXT -> attributeValue
            FacetTypeEnum.NUMBER -> attributeValue.toLong()
            FacetTypeEnum.BOOLEAN -> attributeValue.toBoolean()
        }
    }
}

