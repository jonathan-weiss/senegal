package ch.cassiamon.api.registration

data class ModelInputData(
    @Deprecated("old facet style") val entries: List<ModelConceptInputDataEntry>,
)
