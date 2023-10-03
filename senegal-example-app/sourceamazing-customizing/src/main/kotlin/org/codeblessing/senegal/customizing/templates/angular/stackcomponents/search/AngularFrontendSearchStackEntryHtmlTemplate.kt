package org.codeblessing.senegal.customizing.templates.angular.stackcomponents.search

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendSearchStackEntryHtmlTemplate {

    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            <${templateModel.entityFileName}-search-view
              [showCancelButton]="showCancelButton"
              [showAddButton]="showAddButton"
              [showSelectButton]="showSelectButton"
              [showEditButton]="showEditButton"
              [showDeleteButton]="showDeleteButton"
              [isLocked]="isLocked"
              [stackKey]="stackKey"
            
              (selectClicked)="selectClicked.emit(${'$'}event)"
              (cancelClicked)="cancelClicked.emit(${'$'}event)"
            ></${templateModel.entityFileName}-search-view>


        """.identForMarker()
    }
}
