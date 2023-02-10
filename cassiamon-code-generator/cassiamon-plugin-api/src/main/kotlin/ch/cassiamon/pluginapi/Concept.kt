package ch.cassiamon.pluginapi

interface Concept: ch.cassiamon.pluginapi.Plugin {

    val conceptName: ch.cassiamon.pluginapi.ConceptName

    val enclosingConceptName: ch.cassiamon.pluginapi.ConceptName?
}
