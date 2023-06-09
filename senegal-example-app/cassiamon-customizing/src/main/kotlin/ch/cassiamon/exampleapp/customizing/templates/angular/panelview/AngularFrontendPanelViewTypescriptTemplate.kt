package ch.cassiamon.exampleapp.customizing.templates.angular.panelview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendPanelViewTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        import { Component, OnInit } from '@angular/core';
        import { ${templateModel.entityName}ApiService } from "../../api/${templateModel.entityFileName}-api.service";
        import { ${templateModel.entityName}TO } from "../../api/${templateModel.entityFileName}-to.model";
        import { Create${templateModel.entityName}InstructionTO } from "../../api/create-${templateModel.entityFileName}-instruction-to.model";
        import { Update${templateModel.entityName}InstructionTO } from "../../api/update-${templateModel.entityFileName}-instruction-to.model";
        import { Delete${templateModel.entityName}InstructionTO } from "../../api/delete-${templateModel.entityFileName}-instruction-to.model";
        
        @Component({
            selector: '${templateModel.entityFileName}-panel-view',
            templateUrl: './${templateModel.entityFileName}-panel-view.component.html',
            styleUrls: ['./${templateModel.entityFileName}-panel-view.component.scss'],
        })
        export class ${templateModel.entityName}PanelViewComponent implements OnInit {
        
            all${templateModel.entityName}: ReadonlyArray<${templateModel.entityName}TO> = []
        
            update${templateModel.entityName}Instruction: Update${templateModel.entityName}InstructionTO | undefined = undefined;
            create${templateModel.entityName}Instruction: Create${templateModel.entityName}InstructionTO | undefined = undefined;
        
            constructor(private ${templateModel.decapitalizedEntityName}Service: ${templateModel.entityName}ApiService) {
            }
        
            ngOnInit(): void {
                this.loadAll${templateModel.entityName}();
            }
        
            private loadAll${templateModel.entityName}(): void {
                this.${templateModel.decapitalizedEntityName}Service
                    .getAll${templateModel.entityName}()
                    .subscribe((entities: ReadonlyArray<${templateModel.entityName}TO>) => {
                        this.all${templateModel.entityName} = entities;
                    });
            }
        
            isEditingMode(): boolean {
                return this.create${templateModel.entityName}Instruction != undefined || this.update${templateModel.entityName}Instruction != undefined;
            }
        
            onEdit(entry: ${templateModel.entityName}TO): void {
                this.create${templateModel.entityName}Instruction = undefined;
                this.update${templateModel.entityName}Instruction = {
                    ${templateModel.transferObjectIdFieldName}: entry.${templateModel.transferObjectIdFieldName},${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
                    """
                    ${fieldNode.transferObjectFieldName}: entry.${fieldNode.transferObjectFieldName},"""}}
                }
            }
        
            onPerformDelete(entry: ${templateModel.entityName}TO): void {
                const deleteInstruction: Delete${templateModel.entityName}InstructionTO = {
                    ${templateModel.transferObjectIdFieldName}: entry.${templateModel.transferObjectIdFieldName},
                }
                this.${templateModel.decapitalizedEntityName}Service.delete${templateModel.entityName}(deleteInstruction).subscribe(() => {
                    this.loadAll${templateModel.entityName}();
                });
            }
        
            onPerformUpdate(updateInstruction: Update${templateModel.entityName}InstructionTO): void {
                this.${templateModel.decapitalizedEntityName}Service.update${templateModel.entityName}(updateInstruction).subscribe(() => {
                    this.loadAll${templateModel.entityName}();
                    this.update${templateModel.entityName}Instruction = undefined;
                    this.create${templateModel.entityName}Instruction = undefined;
                })
            }
        
            onPerformCreate(createInstruction: Create${templateModel.entityName}InstructionTO): void {
                this.${templateModel.decapitalizedEntityName}Service.create${templateModel.entityName}(createInstruction).subscribe(() => {
                    this.loadAll${templateModel.entityName}();
                    this.update${templateModel.entityName}Instruction = undefined;
                    this.create${templateModel.entityName}Instruction = undefined;
                })
            }
        
            onNewEntry(): void {
                this.update${templateModel.entityName}Instruction = undefined;
                this.create${templateModel.entityName}Instruction = {${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
                    """
                    ${fieldNode.transferObjectFieldName}: ${fieldNode.transferObjectFieldDefaultValue},"""}}
                }
            }
        
            resetEdit() {
                this.update${templateModel.entityName}Instruction = undefined;
                this.create${templateModel.entityName}Instruction = undefined;
            }
        
        }

        """.identForMarker()
    }
}
