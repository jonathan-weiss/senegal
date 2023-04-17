package ch.cassiamon.engine.parameters

interface ParameterSource {
    fun getParameterMap(): Map<String,String>
}
