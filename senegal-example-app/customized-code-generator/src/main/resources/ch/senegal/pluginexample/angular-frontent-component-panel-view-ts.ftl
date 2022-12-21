import { Component, OnInit } from '@angular/core';
import { ${templateModel.AngularFrontendEntityName}ApiService } from "../../api/${templateModel.AngularFrontendEntityFilename}-api.service";
import { ${templateModel.AngularFrontendEntityName}TO } from "../../api/${templateModel.AngularFrontendEntityFilename}-to.model";
import { Create${templateModel.AngularFrontendEntityName}InstructionTO } from "../../api/create-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";
import { Update${templateModel.AngularFrontendEntityName}InstructionTO } from "../../api/update-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";
import { Delete${templateModel.AngularFrontendEntityName}InstructionTO } from "../../api/delete-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";

@Component({
    selector: '${templateModel.AngularFrontendEntityFilename}-panel-view',
    templateUrl: './${templateModel.AngularFrontendEntityFilename}-panel-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFilename}-panel-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}PanelViewComponent implements OnInit {

    all${templateModel.AngularFrontendEntityName}: ReadonlyArray<${templateModel.AngularFrontendEntityName}TO> = []

    update${templateModel.AngularFrontendEntityName}Instruction: Update${templateModel.AngularFrontendEntityName}InstructionTO | undefined = undefined;
    create${templateModel.AngularFrontendEntityName}Instruction: Create${templateModel.AngularFrontendEntityName}InstructionTO | undefined = undefined;

    constructor(private ${templateModel.AngularFrontendDecapitalizedEntityName}Service: ${templateModel.AngularFrontendEntityName}ApiService) {
    }

    ngOnInit(): void {
        this.loadAll${templateModel.AngularFrontendEntityName}();
    }

    private loadAll${templateModel.AngularFrontendEntityName}(): void {
        this.${templateModel.AngularFrontendDecapitalizedEntityName}Service
            .getAll${templateModel.AngularFrontendEntityName}()
            .subscribe((entities: ReadonlyArray<${templateModel.AngularFrontendEntityName}TO>) => {
                this.all${templateModel.AngularFrontendEntityName} = entities;
            });
    }

    isEditingMode(): boolean {
        return this.create${templateModel.AngularFrontendEntityName}Instruction != undefined || this.update${templateModel.AngularFrontendEntityName}Instruction != undefined;
    }

    onEdit(entry: ${templateModel.AngularFrontendEntityName}TO): void {
        this.create${templateModel.AngularFrontendEntityName}Instruction = undefined;
        this.update${templateModel.AngularFrontendEntityName}Instruction = {
            ${templateModel.AngularFrontendTransferObjectIdFieldName}: entry.${templateModel.AngularFrontendTransferObjectIdFieldName},<#list templateModel.childNodes as fieldNode>
            ${fieldNode.AngularFrontendTransferObjectFieldName}: entry.${fieldNode.AngularFrontendTransferObjectFieldName},</#list>
        }
    }

    onPerformDelete(entry: ${templateModel.AngularFrontendEntityName}TO): void {
        const deleteInstruction: Delete${templateModel.AngularFrontendEntityName}InstructionTO = {
            ${templateModel.AngularFrontendTransferObjectIdFieldName}: entry.${templateModel.AngularFrontendTransferObjectIdFieldName},
        }
        this.${templateModel.AngularFrontendDecapitalizedEntityName}Service.delete${templateModel.AngularFrontendEntityName}(deleteInstruction).subscribe(() => {
            this.loadAll${templateModel.AngularFrontendEntityName}();
        });
    }

    onPerformUpdate(updateInstruction: Update${templateModel.AngularFrontendEntityName}InstructionTO): void {
        this.${templateModel.AngularFrontendDecapitalizedEntityName}Service.update${templateModel.AngularFrontendEntityName}(updateInstruction).subscribe(() => {
            this.loadAll${templateModel.AngularFrontendEntityName}();
            this.update${templateModel.AngularFrontendEntityName}Instruction = undefined;
            this.create${templateModel.AngularFrontendEntityName}Instruction = undefined;
        })
    }

    onPerformCreate(createInstruction: Create${templateModel.AngularFrontendEntityName}InstructionTO): void {
        this.${templateModel.AngularFrontendDecapitalizedEntityName}Service.create${templateModel.AngularFrontendEntityName}(createInstruction).subscribe(() => {
            this.loadAll${templateModel.AngularFrontendEntityName}();
            this.update${templateModel.AngularFrontendEntityName}Instruction = undefined;
            this.create${templateModel.AngularFrontendEntityName}Instruction = undefined;
        })
    }

    onNewEntry(): void {
        this.update${templateModel.AngularFrontendEntityName}Instruction = undefined;
        this.create${templateModel.AngularFrontendEntityName}Instruction = {<#list templateModel.childNodes as fieldNode>
            ${fieldNode.AngularFrontendTransferObjectFieldName}: ${fieldNode.AngularFrontendTransferObjectFieldDefaultValue},</#list>
        }
    }

    resetEdit() {
        this.update${templateModel.AngularFrontendEntityName}Instruction = undefined;
        this.create${templateModel.AngularFrontendEntityName}Instruction = undefined;
    }

}
