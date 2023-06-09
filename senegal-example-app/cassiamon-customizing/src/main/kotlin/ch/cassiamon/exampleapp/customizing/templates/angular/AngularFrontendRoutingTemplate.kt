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
            import { ${entityNode.entityName}PanelViewComponent } from "./${entityNode.entityFileName}/component/${entityNode.entityFileName}-panel-view/${entityNode.entityFileName}-panel-view.component""""}}
            
            export interface NavigationEntry {
                path: string;
                name: string;
            }
            
            export const generatedEntitiesNavigationEntries: ReadonlyArray<NavigationEntry> = [${StringTemplateHelper.forEach(templateModels) { entityNode -> """
                { path: '${entityNode.entityFileName}', name: '${entityNode.entityName}' },"""}}
            ]
            
            const generatedEntitiesRoutes: Routes = [${StringTemplateHelper.forEach(templateModels) { entityNode -> """
                { path: '${entityNode.entityFileName}', component: ${entityNode.entityName}PanelViewComponent },"""}}
            ];
            
            @NgModule({
            imports: [RouterModule.forRoot(generatedEntitiesRoutes)],
            exports: [RouterModule]
            })
            export class GeneratedEntitiesRoutingModule { }

        """.identForMarker()
    }
}
