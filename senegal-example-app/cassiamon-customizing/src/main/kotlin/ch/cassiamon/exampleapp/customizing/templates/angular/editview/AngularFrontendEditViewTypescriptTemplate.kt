package ch.cassiamon.exampleapp.customizing.templates.angular.editview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendEditViewTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        import { Component, Input, Output, EventEmitter } from '@angular/core';
        import { Update${templateModel.entityName}InstructionTO } from "../../api/update-${templateModel.entityFileName}-instruction-to.model";
        
        
        @Component({
            selector: '${templateModel.entityFileName}-edit-view',
            templateUrl: './${templateModel.entityFileName}-edit-view.component.html',
            styleUrls: ['./${templateModel.entityFileName}-edit-view.component.scss'],
        })
        export class ${templateModel.entityName}EditViewComponent {
        
            @Input() update${templateModel.entityName}Instruction!: Update${templateModel.entityName}InstructionTO
        
            @Output() saveClicked: EventEmitter<Update${templateModel.entityName}InstructionTO> = new EventEmitter<Update${templateModel.entityName}InstructionTO>();
            @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
        
        
            saveChanges(): void {
                this.saveClicked.emit(this.update${templateModel.entityName}Instruction);
            }
        
            cancelEdit(): void {
                this.cancelClicked.emit();
            }
        }

        """.identForMarker()
    }
}
