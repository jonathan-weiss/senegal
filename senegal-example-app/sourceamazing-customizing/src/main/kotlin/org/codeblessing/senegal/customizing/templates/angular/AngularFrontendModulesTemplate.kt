package org.codeblessing.senegal.customizing.templates.angular

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendModulesTemplate {

    fun fillTemplate(templateModels: List<org.codeblessing.senegal.customizing.templates.angular.AngularModelClass>): String {
        return """
            import {NgModule} from '@angular/core';
            import {BrowserModule} from '@angular/platform-browser';
            
            ${
            StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
            import { ${entityNode.entityName}Module } from "./${entityNode.entityFileName}/${entityNode.entityFileName}.module"
            """}}
            
            
            @NgModule({
                imports: [
            ${
            StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
                ${entityNode.entityName}Module,"""}}
                ],
            })
            export class GeneratedEntitiesModule {
            }
            
        """.identForMarker()
    }
}
