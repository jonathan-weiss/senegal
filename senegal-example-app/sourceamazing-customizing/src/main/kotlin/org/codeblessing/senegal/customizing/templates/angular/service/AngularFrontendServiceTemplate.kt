package org.codeblessing.senegal.customizing.templates.angular.service

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendServiceTemplate {
    fun fillTemplate(templateModel: org.codeblessing.senegal.customizing.templates.angular.AngularModelClass): String {
        return """
            import { Injectable }       from '@angular/core';
            import { Observable }       from 'rxjs';
                        
            import { ${templateModel.entityName}TO } from "./api/${templateModel.entityFileName}-to.model";
            import { Create${templateModel.entityName}InstructionTO } from "./api/create-${templateModel.entityFileName}-instruction-to.model";
            import { Update${templateModel.entityName}InstructionTO } from "./api/update-${templateModel.entityFileName}-instruction-to.model";
            import { Delete${templateModel.entityName}InstructionTO } from "./api/delete-${templateModel.entityFileName}-instruction-to.model";
            import { Search${templateModel.entityName}InstructionTO } from "./api/search-${templateModel.entityFileName}-instruction-to.model";
            
            import { ${templateModel.entityName}ApiService } from "./api/${templateModel.entityFileName}-api.service";
            
            @Injectable({
            providedIn: 'root'
            })
            export class ${templateModel.entityName}Service {
            
                constructor(private readonly ${templateModel.decapitalizedEntityName}ApiService: ${templateModel.entityName}ApiService) {}
            
                getAll${templateModel.entityName}(): Observable<ReadonlyArray<${templateModel.entityName}TO>> {
                    return this.${templateModel.decapitalizedEntityName}ApiService.getAll${templateModel.entityName}();
                }
                
                searchAll${templateModel.entityName}(searchCriteria: Search${templateModel.entityName}InstructionTO): Observable<ReadonlyArray<${templateModel.entityName}TO>> {
                    return this.${templateModel.decapitalizedEntityName}ApiService.searchAll${templateModel.entityName}(searchCriteria);
                }
                
                create${templateModel.entityName}(createInstruction: Create${templateModel.entityName}InstructionTO): Observable<${templateModel.entityName}TO> {
                    return this.${templateModel.decapitalizedEntityName}ApiService.create${templateModel.entityName}(createInstruction);
                }
            
                update${templateModel.entityName}(updateInstruction: Update${templateModel.entityName}InstructionTO): Observable<${templateModel.entityName}TO> {
                    return this.${templateModel.decapitalizedEntityName}ApiService.update${templateModel.entityName}(updateInstruction);
                }
            
                delete${templateModel.entityName}(deleteInstruction: Delete${templateModel.entityName}InstructionTO): Observable<void> {
                    return this.${templateModel.decapitalizedEntityName}ApiService.delete${templateModel.entityName}(deleteInstruction);
                }
            
            }
            
        """.identForMarker()
    }
}
