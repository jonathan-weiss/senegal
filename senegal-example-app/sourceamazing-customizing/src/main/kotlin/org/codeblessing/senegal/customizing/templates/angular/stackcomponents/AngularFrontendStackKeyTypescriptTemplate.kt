package org.codeblessing.senegal.customizing.templates.angular.stackcomponents

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendStackKeyTypescriptTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            import {StackKey} from "../../../app/shared/component-stack/stack-key";
            
            export const ${templateModel.decapitalizedEntityName}StackKey: StackKey = {
              token: "${templateModel.entityName}StackToken"
            };
        """.identForMarker()
    }
}
