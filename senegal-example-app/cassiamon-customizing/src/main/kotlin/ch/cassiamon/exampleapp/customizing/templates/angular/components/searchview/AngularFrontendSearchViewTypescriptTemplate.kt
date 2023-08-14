package ch.cassiamon.exampleapp.customizing.templates.angular.components.searchview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker
import ch.cassiamon.tools.StringTemplateHelper

object AngularFrontendSearchViewTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
        import { ${templateModel.entityName}Service } from "../../${templateModel.entityFileName}.service";
        import { ${templateModel.entityName}TO } from "../../api/${templateModel.entityFileName}-to.model";

        import {ComponentStackService} from "../../../../app/shared/component-stack/component-stack.service";
        import { Delete${templateModel.entityName}InstructionTO } from "../../api/delete-${templateModel.entityFileName}-instruction-to.model";
        import {
          ${templateModel.entityName}FormStackEntryComponent
        } from "../../stack-components/${templateModel.entityFileName}-form-stack-entry/${templateModel.entityFileName}-form-stack-entry.component";
        import {Search${templateModel.entityName}InstructionTO} from "../../api/search-${templateModel.entityFileName}-instruction-to.model";
        import {StackKey} from "../../../../app/shared/component-stack/stack-key";
        import {ErrorMessage} from "../../../../app/shared/error-list/error-message.model";
        import {ErrorTransformationService} from "../../../../app/shared/error-list/error-transformation.service";
                
               
        @Component({
          selector: '${templateModel.entityFileName}-search-view',
          templateUrl: './${templateModel.entityFileName}-search-view.component.html',
          styleUrls: ['./${templateModel.entityFileName}-search-view.component.scss'],
        })
        export class ${templateModel.entityName}SearchViewComponent implements OnInit {
          @Input() showCancelButton: boolean = false
          @Input() showAddButton: boolean = false
          @Input() showSelectButton: boolean = false
          @Input() showEditButton: boolean = false
          @Input() showDeleteButton: boolean = false
          @Input() isLocked!: boolean
          @Input() stackKey!: StackKey
        
          @Output() selectClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
          @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
        
          all${templateModel.entityName}: ReadonlyArray<${templateModel.entityName}TO> = []
        
          highlighted${templateModel.entityName}: ${templateModel.entityName}TO | undefined = undefined;
        
          errorMessages: Array<ErrorMessage> = []
        
        
          constructor(private ${templateModel.decapitalizedEntityName}Service: ${templateModel.entityName}Service,
                      private componentStackService: ComponentStackService,
                      private errorTransformationService: ErrorTransformationService) {
          }
        
          ngOnInit(): void {
            this.loadAll${templateModel.entityName}();
          }
        
        
          private loadAll${templateModel.entityName}(): void {
            const searchCriteria: Search${templateModel.entityName}InstructionTO = {
              ${templateModel.transferObjectIdFieldName}: undefined,
              ${StringTemplateHelper.forEach(templateModel.angularFields()) { angularModelField -> """
              ${angularModelField.decapitalizedFieldName}: undefined,"""}}
            }
        
            this.${templateModel.decapitalizedEntityName}Service
              .searchAll${templateModel.entityName}(searchCriteria)
              .subscribe((entities: ReadonlyArray<${templateModel.entityName}TO>) => {
                this.all${templateModel.entityName} = entities;
              });
          }
        
          select(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO): void {
            this.selectClicked.emit(${templateModel.decapitalizedEntityName});
            this.componentStackService.removeLatestComponentFromStack(this.stackKey);
          }
        
          cancel(): void {
            this.cancelClicked.emit();
            this.componentStackService.removeLatestComponentFromStack(this.stackKey);
          }
        
          add(): void {
            this.highlighted${templateModel.entityName} = undefined;
            this.componentStackService.newComponentOnStack(this.stackKey, ${templateModel.entityName}FormStackEntryComponent, (component: ${templateModel.entityName}FormStackEntryComponent) => {
              component.stackKey = this.stackKey;
              component.${templateModel.decapitalizedEntityName} = undefined;
              component.saveClicked.subscribe((${templateModel.decapitalizedEntityName}) => this.reloadAll${templateModel.entityName}AfterEditing(${templateModel.decapitalizedEntityName}));
              component.cancelClicked.subscribe(() => this.reloadAll${templateModel.entityName}AfterEditing());
            });
          }
        
          edit(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO): void {
            this.highlighted${templateModel.entityName} = ${templateModel.decapitalizedEntityName};
            this.componentStackService.newComponentOnStack(this.stackKey, ${templateModel.entityName}FormStackEntryComponent, (component: ${templateModel.entityName}FormStackEntryComponent) => {
              component.stackKey = this.stackKey;
              component.${templateModel.decapitalizedEntityName} = ${templateModel.decapitalizedEntityName};
              component.saveClicked.subscribe((${templateModel.decapitalizedEntityName}) => this.reloadAll${templateModel.entityName}AfterEditing(${templateModel.decapitalizedEntityName}));
              component.cancelClicked.subscribe(() => this.reloadAll${templateModel.entityName}AfterEditing());
            })
          }
        
          delete(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO): void {
            this.onPerformDeleteOnServer(${templateModel.decapitalizedEntityName});
          }
        
          private onPerformDeleteOnServer(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO): void {
            const deleteInstruction: Delete${templateModel.entityName}InstructionTO = {
              ${templateModel.transferObjectIdFieldName}: ${templateModel.decapitalizedEntityName}.${templateModel.transferObjectIdFieldName},
            }
            this.${templateModel.decapitalizedEntityName}Service.delete${templateModel.entityName}(deleteInstruction).subscribe(() => {
              this.reloadAll${templateModel.entityName}AfterEditing();
            },
              (error: any) => this.errorCase(${templateModel.decapitalizedEntityName}, error));
          }
        
          private errorCase(${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO, error: any): void {
            const ${templateModel.decapitalizedEntityName}Description = 'The ${templateModel.entityName} ' + ${templateModel.decapitalizedEntityName}.transferObjectDescription + ' could not be deleted.'
            const errorMessage = this.errorTransformationService.transformErrorToMessage(${templateModel.decapitalizedEntityName}Description, error)
        
            if(errorMessage != undefined) {
              this.errorMessages.push(errorMessage)
            }
          }
        
          private reloadAll${templateModel.entityName}AfterEditing(highlighted${templateModel.entityName}: ${templateModel.entityName}TO | undefined = undefined): void {
            this.loadAll${templateModel.entityName}();
            this.highlighted${templateModel.entityName} = highlighted${templateModel.entityName};
          }
        
        }
        """.identForMarker()
    }
}
