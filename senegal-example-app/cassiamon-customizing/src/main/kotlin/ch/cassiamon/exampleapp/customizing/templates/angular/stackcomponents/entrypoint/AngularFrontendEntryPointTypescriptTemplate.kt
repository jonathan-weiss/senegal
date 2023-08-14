package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.entrypoint

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendEntryPointTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import { Component, Output } from '@angular/core';
            import {ComponentStackService} from "../../../../app/shared/component-stack/component-stack.service";
            import {${templateModel.entityName}SearchStackEntryComponent} from "../${templateModel.entityFileName}-search-stack-entry/${templateModel.entityFileName}-search-stack-entry.component";
            import {${templateModel.decapitalizedEntityName}StackKey} from "../${templateModel.entityFileName}-stack-key";
            import {StackKey} from "../../../../app/shared/component-stack/stack-key";
            
            
            @Component({
                selector: '${templateModel.entityFileName}-entry-point',
                templateUrl: './${templateModel.entityFileName}-entry-point.component.html',
                styleUrls: ['./${templateModel.entityFileName}-entry-point.component.scss'],
            })
            export class ${templateModel.entityName}EntryPointComponent {
            
              stackKey: StackKey = ${templateModel.decapitalizedEntityName}StackKey;
            
              constructor(private componentStackService: ComponentStackService) {
              }
            
              ngOnInit(): void {
                setTimeout(() => this.loadInitialComponent());
              }
            
              private loadInitialComponent(): void {
                this.componentStackService.newComponentOnStack(this.stackKey, ${templateModel.entityName}SearchStackEntryComponent, (component: ${templateModel.entityName}SearchStackEntryComponent) => {
                  component.stackKey = this.stackKey;
                  component.showCancelButton = false;
                  component.showAddButton = true;
                  component.showSelectButton = false;
                  component.showEditButton = true;
                  component.showDeleteButton = true;
                })
              }
            }
        """.identForMarker()
    }
}
