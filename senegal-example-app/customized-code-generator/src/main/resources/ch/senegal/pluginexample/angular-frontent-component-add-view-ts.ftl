import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Create${templateModel.AngularFrontendEntityName}InstructionTO } from "../../api/create-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";


@Component({
    selector: '${templateModel.AngularFrontendEntityFilename}-add-view',
    templateUrl: './${templateModel.AngularFrontendEntityFilename}-add-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFilename}-add-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}AddViewComponent {

    @Input() create${templateModel.AngularFrontendEntityName}Instruction!: Create${templateModel.AngularFrontendEntityName}InstructionTO


    @Output() saveClicked: EventEmitter<Create${templateModel.AngularFrontendEntityName}InstructionTO> = new EventEmitter<Create${templateModel.AngularFrontendEntityName}InstructionTO>();
    @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();


    saveChanges(): void {
        this.saveClicked.emit(this.create${templateModel.AngularFrontendEntityName}Instruction);
    }

    cancelEdit(): void {
        this.cancelClicked.emit();
    }
}
