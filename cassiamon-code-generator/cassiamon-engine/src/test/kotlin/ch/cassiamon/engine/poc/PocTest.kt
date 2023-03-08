package ch.cassiamon.engine.poc

import ch.cassiamon.engine.TestFixtures
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PocTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetDescriptor
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetDescriptor
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetDescriptor
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetDescriptor
    private val tableFieldForeignKeyConceptIdFacetName = TestFixtures.tableFieldForeignKeyConceptIdFacetDescriptor
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetDescriptor

    sealed class FacetDescription<T>(val name: String) {

    }

    class MandatoryTextFacetDescription(name: String): FacetDescription<String>(name) {

    }

    class OptionalTextFacetDescription(name: String): FacetDescription<String?>(name) {

    }

    class MandatoryNumberFacetDescription(name: String): FacetDescription<Int>(name) {

    }

    class OptionalNumberFacetDescription(name: String): FacetDescription<Int?>(name) {

    }

    class FacetValueBag<T>(
        val facetDescription: FacetDescription<T>,
        val facetValue: T
        )

    class FacetCollectorAndAccessor {
        private val optionalTextFacets: MutableMap<OptionalTextFacetDescription, FacetValueBag<String?>> = mutableMapOf()
        private val mandatoryNumberFacets: MutableMap<MandatoryNumberFacetDescription, FacetValueBag<Int>> = mutableMapOf()

        fun addOptionalText(facet: OptionalTextFacetDescription, value: String?) {
            optionalTextFacets[facet] = FacetValueBag(facet, value)
        }

        fun addMandatoryNumber(facet: MandatoryNumberFacetDescription, value: Int) {
            mandatoryNumberFacets[facet] = FacetValueBag(facet, value)
        }

        fun getMandatoryNumber(facet: MandatoryNumberFacetDescription): Int {
            return mandatoryNumberFacets[facet]?.facetValue ?: throw IllegalStateException()
        }

        fun getOptionalText(facet: OptionalTextFacetDescription): String? {
            return optionalTextFacets[facet]?.facetValue
        }

    }
    class FacetAccess(val facets: List<FacetValueBag<Any>>)

    @Test
    fun `test simple poc`() {


        val optionalTextFacet = OptionalTextFacetDescription("foo")
        val mandatoryTextFacet = MandatoryTextFacetDescription("bar")

        val optionalNumberFacet = OptionalNumberFacetDescription("faz")
        val mandatoryNumberFacet = MandatoryNumberFacetDescription("baz")
        val mandatoryNumberFacet2 = MandatoryNumberFacetDescription("baz2")

        val optionalTextValue = FacetValueBag(optionalTextFacet, null)
        val mandatoryTextValue = FacetValueBag(mandatoryTextFacet, "mand")

        val optionalNumberValue = FacetValueBag(optionalNumberFacet, null)
        val mandatoryNumberValue = FacetValueBag(mandatoryNumberFacet, 23)

        val collector = FacetCollectorAndAccessor()
        collector.addMandatoryNumber(mandatoryNumberFacet, 42)
        collector.addMandatoryNumber(mandatoryNumberFacet2, 77)

        collector.addOptionalText(optionalTextFacet, null)
        Assertions.assertNull(collector.getOptionalText(optionalTextFacet))
        collector.addOptionalText(optionalTextFacet, "my text")
        Assertions.assertNotNull(collector.getOptionalText(optionalTextFacet))
        Assertions.assertEquals(42, collector.getMandatoryNumber(mandatoryNumberFacet))
        Assertions.assertEquals(77, collector.getMandatoryNumber(mandatoryNumberFacet2))


    }
}
