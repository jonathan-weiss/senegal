package ch.senegal.plugin

interface Concept: Plugin {

    val conceptName: ConceptName

    val enclosingConceptName: ConceptName?
}
