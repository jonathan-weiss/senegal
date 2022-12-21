import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

<#list templateModel.childNodes as entityNode>
import { ${entityNode.AngularFrontendEntityName}PanelViewComponent } from "./${entityNode.AngularFrontendEntityFilename}/component/${entityNode.AngularFrontendEntityFilename}-panel-view/${entityNode.AngularFrontendEntityFilename}-panel-view.component"
</#list>

export interface NavigationEntry {
    path: string;
    name: string;
}

export const generatedEntitiesNavigationEntries: ReadonlyArray<NavigationEntry> = [<#list templateModel.childNodes as entityNode>
    { path: '${entityNode.AngularFrontendEntityFilename}', name: '${entityNode.AngularFrontendEntityName}' },</#list>
]

const generatedEntitiesRoutes: Routes = [<#list templateModel.childNodes as entityNode>
    { path: '${entityNode.AngularFrontendEntityFilename}', component: ${entityNode.AngularFrontendEntityName}PanelViewComponent },</#list>
];

@NgModule({
imports: [RouterModule.forRoot(generatedEntitiesRoutes)],
exports: [RouterModule]
})
export class GeneratedEntitiesRoutingModule { }
