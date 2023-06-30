package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.api.schema.FacetSchema
import ch.cassiamon.api.schema.FacetTypeEnum

object XmlFacetValueConverter {
    fun convertString(facetSchema: FacetSchema, attributeValue: String): Any? {
        return when(facetSchema.facetType) {
            FacetTypeEnum.TEXT -> attributeValue
            FacetTypeEnum.NUMBER -> attributeValue.toLong()
        }
    }
}

