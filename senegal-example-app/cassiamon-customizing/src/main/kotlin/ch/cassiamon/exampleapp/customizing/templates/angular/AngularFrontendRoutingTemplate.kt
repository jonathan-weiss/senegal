package ch.cassiamon.exampleapp.customizing.templates.angular

import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendRoutingTemplate {

    fun fillTemplate(templateModels: List<AngularModelClass>): String {
        return """
            import { NgModule } from '@angular/core';
            import { RouterModule, Routes } from '@angular/router';
            
            ${StringTemplateHelper.forEach(templateModels) { entityNode ->
                """
            import { ${entityNode.entityName}EntryPointComponent } from "./${entityNode.entityFileName}/stack-components/${entityNode.entityFileName}-entry-point/${entityNode.entityFileName}-entry-point.component""""}}
            
            export interface NavigationEntry {
                path: string;
                name: string;
            }
            
            export const generatedEntitiesNavigationEntries: ReadonlyArray<NavigationEntry> = [${StringTemplateHelper.forEach(templateModels) { entityNode -> """
                { path: '${entityNode.entityFileName}', name: '${entityNode.entityName}' },"""}}
            ]
            
            const generatedEntitiesRoutes: Routes = [${StringTemplateHelper.forEach(templateModels) { entityNode -> """
                { path: '${entityNode.entityFileName}', component: ${entityNode.entityName}EntryPointComponent },"""}}
            ];
            
            @NgModule({
            imports: [RouterModule.forRoot(generatedEntitiesRoutes)],
            exports: [RouterModule]
            })
            export class GeneratedEntitiesRoutingModule { }

        """.identForMarker()
    }
}
