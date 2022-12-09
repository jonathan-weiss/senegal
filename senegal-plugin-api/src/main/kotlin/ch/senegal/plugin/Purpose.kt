package ch.senegal.plugin

interface Purpose: Plugin {

    val purposeName: PurposeName

    val purposeDecors: Set<PurposeDecor>
}
