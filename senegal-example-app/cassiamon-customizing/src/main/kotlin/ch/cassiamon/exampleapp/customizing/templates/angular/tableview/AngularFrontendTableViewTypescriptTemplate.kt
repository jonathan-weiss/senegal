package ch.cassiamon.exampleapp.customizing.templates.angular.tableview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendTableViewTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        import {Component, EventEmitter, Input, Output} from '@angular/core';
        import { ${templateModel.entityName}TO } from "../../api/${templateModel.entityFileName}-to.model";
        
        
        @Component({
            selector: '${templateModel.entityFileName}-table-view',
            templateUrl: './${templateModel.entityFileName}-table-view.component.html',
            styleUrls: ['./${templateModel.entityFileName}-table-view.component.scss'],
        })
        export class ${templateModel.entityName}TableViewComponent {
        
            @Input() all${templateModel.entityName}!: ReadonlyArray<${templateModel.entityName}TO>
            @Input() tableControlsDisabled!: boolean;
        
            @Output() editEntryClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
            @Output() deleteEntryClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
        
            displayedColumns: string[] = [
                '${templateModel.transferObjectIdFieldName}',${StringTemplateHelper.forEach(templateModel.angularFields()) { fieldNode ->
                """
                '${fieldNode.transferObjectFieldName}',"""}}
                'context'
            ];
        
            getEntries(): ${templateModel.entityName}TO[] {
                return [...this.all${templateModel.entityName}];
            }
        
            onCtxEditClicked(entry: ${templateModel.entityName}TO): void {
                this.editEntryClicked.emit(entry);
            }
        
            onRowDoubleClicked(entry: ${templateModel.entityName}TO): void {
                this.editEntryClicked.emit(entry);
            }
        
            onCtxDeleteClicked(entry: ${templateModel.entityName}TO): void {
                this.deleteEntryClicked.emit(entry);
            }
        
        }

        """.identForMarker()
    }
}
