package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendDeleteInstructionToTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """

        import { UuidTO } from '../../../app/uuid-to.model';
        
        export interface Delete${templateModel.entityName}InstructionTO {
            ${templateModel.transferObjectIdFieldName}: ${templateModel.transferObjectIdFieldType},
        }
        """.identForMarker()
    }
}
