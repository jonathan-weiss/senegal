package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendModulesTemplate {

    fun fillTemplate(templateModels: List<AngularModelClass>): String {
        return """
            import {NgModule} from '@angular/core';
            import {BrowserModule} from '@angular/platform-browser';
            
            ${StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
            import { ${entityNode.entityName}Module } from "./${entityNode.entityFileName}/${entityNode.entityFileName}.module"
            """}}
            
            
            @NgModule({
                imports: [
            ${StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
                ${entityNode.entityName}Module,"""}}
                ],
            })
            export class GeneratedEntitiesModule {
            }
            
        """.identForMarker()
    }
}
