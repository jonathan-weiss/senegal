package ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.search

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendSearchStackEntryHtmlTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
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
