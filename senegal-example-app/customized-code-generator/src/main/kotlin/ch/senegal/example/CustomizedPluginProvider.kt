package ch.senegal.example

import ch.senegal.plugin.finder.AbstractPluginProvider

class CustomizedPluginProvider : AbstractPluginProvider(
    plugins = setOf(
        EntityConceptPlugin, EntityAttributeConceptPlugin, EntityPurposePlugin,
        KotlinModelPurposePlugin, SqlDbPurposePlugin
    )
)
