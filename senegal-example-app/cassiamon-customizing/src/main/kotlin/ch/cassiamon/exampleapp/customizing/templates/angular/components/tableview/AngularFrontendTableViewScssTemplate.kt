package ch.cassiamon.exampleapp.customizing.templates.angular.components.tableview

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.tools.StringIdentHelper.identForMarker

object AngularFrontendTableViewScssTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
        table {
            width: 100%;
        }
        
        .mat-column-context {
            width: 128px;
            text-align: center;
        }
        
        .highlighted-row {
          background-color: ghostwhite;
        }
        .context-column {
          background-color: lightgrey;
        }
        
        """.identForMarker()
    }
}
