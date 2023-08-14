package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendStackKeyTypescriptTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import {StackKey} from "../../../app/shared/component-stack/stack-key";
            
            export const ${templateModel.decapitalizedEntityName}StackKey: StackKey = {
              token: "${templateModel.entityName}StackToken"
            };
        """.identForMarker()
    }
}
