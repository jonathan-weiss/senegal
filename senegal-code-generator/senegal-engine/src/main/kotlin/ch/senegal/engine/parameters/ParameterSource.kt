package ch.senegal.engine.parameters

interface ParameterSource {
    fun getParameterMap(): Map<String,String>
}
