package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.form

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendFormStackEntryTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import {Component, EventEmitter, Input, Output} from '@angular/core';
            import {LockableStackEntry} from "../../../../app/shared/component-stack/lockable-stack-entry";
            import {${templateModel.entityName}TO} from "../../api/${templateModel.entityFileName}-to.model";
            import {StackKey} from "../../../../app/shared/component-stack/stack-key";
            
            @Component({
              selector: '${templateModel.entityFileName}-form-stack-entry',
              templateUrl: './${templateModel.entityFileName}-form-stack-entry.component.html',
              styleUrls: ['./${templateModel.entityFileName}-form-stack-entry.component.scss'],
            })
            export class ${templateModel.entityName}FormStackEntryComponent extends LockableStackEntry{
            
              @Input() ${templateModel.decapitalizedEntityName}: ${templateModel.entityName}TO | undefined;
              @Input() stackKey!: StackKey
            
              @Output() saveClicked: EventEmitter<${templateModel.entityName}TO> = new EventEmitter<${templateModel.entityName}TO>();
              @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();
            
            }
        """.identForMarker()
    }
}
