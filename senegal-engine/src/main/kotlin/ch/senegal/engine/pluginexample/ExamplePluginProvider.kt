package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.finder.AbstractPluginProvider

class ExamplePluginProvider: AbstractPluginProvider(
    plugins = setOf(EntityConceptPlugin, EntityAttributeConceptPlugin, KotlinModelPurposePlugin)
)
