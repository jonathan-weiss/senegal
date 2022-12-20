import {Component, EventEmitter, Input, Output} from '@angular/core';
import { ${templateModel.AngularFrontendTransferObjectName} } from "../../api/${templateModel.AngularFrontendTransferObjectFilename}";


@Component({
    selector: '${templateModel.AngularFrontendEntityFileName}-table-view',
    templateUrl: './${templateModel.AngularFrontendEntityFileName}-table-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFileName}-table-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}TableViewComponent {

    @Input() all${templateModel.AngularFrontendEntityName}!: ReadonlyArray<${templateModel.AngularFrontendTransferObjectName}>
    @Input() tableControlsDisabled!: boolean;

    @Output() editEntryClicked: EventEmitter<${templateModel.AngularFrontendTransferObjectName}> = new EventEmitter<${templateModel.AngularFrontendTransferObjectName}>();
    @Output() deleteEntryClicked: EventEmitter<${templateModel.AngularFrontendTransferObjectName}> = new EventEmitter<${templateModel.AngularFrontendTransferObjectName}>();

    displayedColumns: string[] = [
        '${templateModel.AngularFrontendTransferObjectIdFieldName}',<#list templateModel.childNodes as fieldNode>
        '${fieldNode.AngularFrontendTransferObjectFieldName}',</#list>
        'context'
    ];

    getEntries(): ${templateModel.AngularFrontendTransferObjectName}[] {
        return [...this.all${templateModel.AngularFrontendEntityName}];
    }

    onCtxEditClicked(entry: ${templateModel.AngularFrontendTransferObjectName}): void {
        this.editEntryClicked.emit(entry);
    }

    onRowDoubleClicked(entry: ${templateModel.AngularFrontendTransferObjectName}): void {
        this.editEntryClicked.emit(entry);
    }

    onCtxDeleteClicked(entry: ${templateModel.AngularFrontendTransferObjectName}): void {
        this.deleteEntryClicked.emit(entry);
    }

}
