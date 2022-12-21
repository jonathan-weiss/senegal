import {Component, EventEmitter, Input, Output} from '@angular/core';
import { ${templateModel.AngularFrontendEntityName}TO } from "../../api/${templateModel.AngularFrontendEntityFilename}-to.model";


@Component({
    selector: '${templateModel.AngularFrontendEntityFilename}-table-view',
    templateUrl: './${templateModel.AngularFrontendEntityFilename}-table-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFilename}-table-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}TableViewComponent {

    @Input() all${templateModel.AngularFrontendEntityName}!: ReadonlyArray<${templateModel.AngularFrontendEntityName}TO>
    @Input() tableControlsDisabled!: boolean;

    @Output() editEntryClicked: EventEmitter<${templateModel.AngularFrontendEntityName}TO> = new EventEmitter<${templateModel.AngularFrontendEntityName}TO>();
    @Output() deleteEntryClicked: EventEmitter<${templateModel.AngularFrontendEntityName}TO> = new EventEmitter<${templateModel.AngularFrontendEntityName}TO>();

    displayedColumns: string[] = [
        '${templateModel.AngularFrontendTransferObjectIdFieldName}',<#list templateModel.childNodes as fieldNode>
        '${fieldNode.AngularFrontendTransferObjectFieldName}',</#list>
        'context'
    ];

    getEntries(): ${templateModel.AngularFrontendEntityName}TO[] {
        return [...this.all${templateModel.AngularFrontendEntityName}];
    }

    onCtxEditClicked(entry: ${templateModel.AngularFrontendEntityName}TO): void {
        this.editEntryClicked.emit(entry);
    }

    onRowDoubleClicked(entry: ${templateModel.AngularFrontendEntityName}TO): void {
        this.editEntryClicked.emit(entry);
    }

    onCtxDeleteClicked(entry: ${templateModel.AngularFrontendEntityName}TO): void {
        this.deleteEntryClicked.emit(entry);
    }

}
