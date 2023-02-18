package ch.cassiamon.pluginapi.model.exceptions


class MissingFacetValueModelException(facetDescription: String): ModelException(
    """The following facet has no value: [$facetDescription]. 
    """.trimMargin()
) {

}
