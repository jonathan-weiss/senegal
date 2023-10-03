package org.codeblessing.senegal.customizing.templates.angular.components.tableview

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker
import org.codeblessing.sourceamazing.tools.StringTemplateHelper

object AngularFrontendTableViewTypescriptTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            import {Component, EventEmitter, Input, Output} from '@angular/core';
            import { ${templateModel.entityName}TO } from "../../api/${templateModel.entityFileName}-to.model";
            import {StackKey} from "../../../../app/shared/component-stack/stack-key";
            
            
            @Component({
                selector: '${templateModel.entityFileName}-table-view',
                templateUrl: './${templateModel.entityFileName}-table-view.component.html',
                styleUrls: ['./${templateModel.entityFileName}-table-view.component.scss'],
            })
            export class ${templateModel.entityName}TableViewComponent {
              @Input() showSelectButton: boolean = false
              @Input() showEditButton: boolean = false
              @Input() showDeleteButton: boolean = false
              @Input() isLocked!: boolean;
              @Input() stackKey!: StackKey
            
            
                @Input() all${templateModel.entityName}!: ReadonlyArray<${templateModel.entityName}TO>
                @Input() highlighted${templateModel.entityName}: ${templateModel.entityName}TO | undefined = undefined;
            
                @Output() selectEntryClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
                @Output() editEntryClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
                @Output() deleteEntryClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
            
                displayedColumns: string[] = [
                    '${templateModel.transferObjectIdFieldName}',
                    ${
            StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> 
                    """
                    '${angularModelField.decapitalizedFieldName}',
                    """.trimIndent()}}
                    'context'
                ];
            
                as${templateModel.entityName}(entry: any): ${templateModel.entityName}TO {
                  return entry as ${templateModel.entityName}TO
                }
            
              isHighlighted(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO): boolean {
                  return this.highlighted${templateModel.entityName} != undefined && ${templateModel.decapitalizedEntityName}.${templateModel.transferObjectIdFieldName}.value == this.highlighted${templateModel.entityName}.${templateModel.transferObjectIdFieldName}.value;
              }
            
              onRowDoubleClicked(entry: ${templateModel.entityName}TO): void {
                  if(this.showSelectButton) {
                    this.selectEntryClicked.emit(entry);
                  } else if(this.showEditButton) {
                    this.editEntryClicked.emit(entry);
                  }
              }
            
            
              editClicked(entry: ${templateModel.entityName}TO): void {
                    this.editEntryClicked.emit(entry);
                }
            
                selectClicked(entry: ${templateModel.entityName}TO): void {
                    this.selectEntryClicked.emit(entry);
                }
            
                deleteClicked(entry: ${templateModel.entityName}TO): void {
                    this.deleteEntryClicked.emit(entry);
                }
            
            }
        """.identForMarker()
    }
}
