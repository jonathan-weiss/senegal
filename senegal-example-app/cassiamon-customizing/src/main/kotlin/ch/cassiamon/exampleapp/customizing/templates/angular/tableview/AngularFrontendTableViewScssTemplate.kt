package ch.cassiamon.exampleapp.customizing.templates.angular.tableview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendTableViewScssTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        table {
            width: 100%;
        }
        
        .mat-column-context {
            width: 32px;
            text-align: center;
        }
        
        """.identForMarker()
    }
}
