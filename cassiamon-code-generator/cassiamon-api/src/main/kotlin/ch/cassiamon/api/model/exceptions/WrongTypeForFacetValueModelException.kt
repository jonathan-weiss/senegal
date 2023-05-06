package ch.cassiamon.api.model.exceptions


class WrongTypeForFacetValueModelException(facetDescription: String): ModelException(
    """The following facet has no value: [$facetDescription]. 
    """.trimMargin()
) {

}
