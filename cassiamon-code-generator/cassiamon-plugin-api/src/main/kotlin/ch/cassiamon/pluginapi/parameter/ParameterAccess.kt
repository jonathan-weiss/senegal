package ch.cassiamon.pluginapi.parameter

interface ParameterAccess {
    fun getParameter(name: String): String
    fun getParameterMap(): Map<String,String>
}
