package ch.senegal.example

import ch.senegal.plugin.finder.AbstractPluginProvider

class CustomizedPluginProvider : AbstractPluginProvider(
    plugins = setOf(
        EntitiesConceptPlugin, EntityConceptPlugin, EntityAttributeConceptPlugin, EntityPurposePlugin,
        InfoPurposePlugin,
        KotlinModelPurposePlugin, SqlDbPurposePlugin
    )
)
