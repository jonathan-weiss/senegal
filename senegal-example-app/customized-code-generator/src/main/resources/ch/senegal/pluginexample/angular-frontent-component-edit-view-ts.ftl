import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Update${templateModel.AngularFrontendEntityName}InstructionTO } from "../../api/update-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";


@Component({
    selector: '${templateModel.AngularFrontendEntityFilename}-edit-view',
    templateUrl: './${templateModel.AngularFrontendEntityFilename}-edit-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFilename}-edit-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}EditViewComponent {

    @Input() update${templateModel.AngularFrontendEntityName}Instruction!: Update${templateModel.AngularFrontendEntityName}InstructionTO

    @Output() saveClicked: EventEmitter<Update${templateModel.AngularFrontendEntityName}InstructionTO> = new EventEmitter<Update${templateModel.AngularFrontendEntityName}InstructionTO>();
    @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


    saveChanges(): void {
        this.saveClicked.emit(this.update${templateModel.AngularFrontendEntityName}Instruction);
    }

    cancelEdit(): void {
        this.cancelClicked.emit();
    }
}
