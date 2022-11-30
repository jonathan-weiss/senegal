package ch.senegal.engine.plugin

interface Purpose: Plugin {

    val purposeName: PurposeName

    val enclosingConceptName: ConceptName

    val purposeDecors: Set<PurposeDecor>
}
