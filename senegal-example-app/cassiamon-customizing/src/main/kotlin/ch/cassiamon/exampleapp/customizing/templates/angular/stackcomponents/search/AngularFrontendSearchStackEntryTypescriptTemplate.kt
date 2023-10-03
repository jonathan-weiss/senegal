package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.search

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendSearchStackEntryTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import {Component, EventEmitter, Input, Output} from '@angular/core';
            import {LockableStackEntry} from "../../../../app/shared/component-stack/lockable-stack-entry";
            import {${templateModel.entityName}TO} from "../../api/${templateModel.entityFileName}-to.model";
            import {StackKey} from "../../../../app/shared/component-stack/stack-key";
            
            @Component({
              selector: '${templateModel.entityFileName}-search-stack-entry',
              templateUrl: './${templateModel.entityFileName}-search-stack-entry.component.html',
              styleUrls: ['./${templateModel.entityFileName}-search-stack-entry.component.scss'],
            })
            export class ${templateModel.entityName}SearchStackEntryComponent extends LockableStackEntry{
            
              @Input() showCancelButton: boolean = false
              @Input() showAddButton: boolean = false
              @Input() showSelectButton: boolean = false
              @Input() showEditButton: boolean = false
              @Input() showDeleteButton: boolean = false
            
              @Input() stackKey!: StackKey
            
              @Output() selectClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
              @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
            }
        """.identForMarker()
    }
}
