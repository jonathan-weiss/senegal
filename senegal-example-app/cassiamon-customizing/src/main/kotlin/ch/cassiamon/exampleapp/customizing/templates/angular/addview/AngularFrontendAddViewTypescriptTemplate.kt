package ch.cassiamon.exampleapp.customizing.templates.angular.addview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendAddViewTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import { Component, Input, Output, EventEmitter } from '@angular/core';
            import { Create${templateModel.entityName}InstructionTO } from "../../api/create-${templateModel.entityFileName}-instruction-to.model";
            
            
            @Component({
                selector: '${templateModel.entityFileName}-add-view',
                templateUrl: './${templateModel.entityFileName}-add-view.component.html',
                styleUrls: ['./${templateModel.entityFileName}-add-view.component.scss'],
            })
            export class ${templateModel.entityName}AddViewComponent {
            
                @Input() create${templateModel.entityName}Instruction!: Create${templateModel.entityName}InstructionTO
            
            
                @Output() saveClicked: EventEmitter<Create${templateModel.entityName}InstructionTO> = new EventEmitter<Create${templateModel.entityName}InstructionTO>();
                @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
            
            
                saveChanges(): void {
                    this.saveClicked.emit(this.create${templateModel.entityName}Instruction);
                }
            
                cancelEdit(): void {
                    this.cancelClicked.emit();
                }
            }

        """.identForMarker()
    }
}
