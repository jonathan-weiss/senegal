package org.codeblessing.senegal.customizing.templates.angular.components.formview

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendFormViewScssTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
        .tab-content {
          padding-top: 15px;
          min-width: 150px;
          max-width: 500px;
          width: 100%;
        }
        
        .full-width-form-field {
          width: 100%;
        }

        """.identForMarker()
    }
}
