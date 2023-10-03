package org.codeblessing.senegal.customizing.templates.angular.components.tableview

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendTableViewScssTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
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
