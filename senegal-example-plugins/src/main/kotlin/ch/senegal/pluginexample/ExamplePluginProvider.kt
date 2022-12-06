package ch.senegal.pluginexample

import ch.senegal.plugin.finder.AbstractPluginProvider

class ExamplePluginProvider : AbstractPluginProvider(
    plugins = setOf(
        EntityConceptPlugin, EntityAttributeConceptPlugin,
        EntityPurposePlugin, EntityAttributePurposePlugin,
        KotlinModelPurposePlugin, KotlinFieldPurposePlugin
    )
)
