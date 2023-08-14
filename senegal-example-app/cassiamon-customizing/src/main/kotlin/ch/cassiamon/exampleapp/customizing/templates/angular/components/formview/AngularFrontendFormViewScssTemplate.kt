package ch.cassiamon.exampleapp.customizing.templates.angular.components.formview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendFormViewScssTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
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
