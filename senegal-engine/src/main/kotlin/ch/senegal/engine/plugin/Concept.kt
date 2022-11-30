package ch.senegal.engine.plugin

interface Concept: Plugin {

    val conceptName: ConceptName

    val enclosingConceptName: ConceptName?

    val conceptDecors: Set<ConceptDecor>
}
