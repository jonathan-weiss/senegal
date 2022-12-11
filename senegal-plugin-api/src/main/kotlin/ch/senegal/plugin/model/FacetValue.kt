package ch.senegal.plugin.model

@JvmInline
value class FacetValue private constructor(val value: Any) {

    companion object {
        fun of(value: Any): FacetValue {
            return FacetValue(value)
        }
    }
}


