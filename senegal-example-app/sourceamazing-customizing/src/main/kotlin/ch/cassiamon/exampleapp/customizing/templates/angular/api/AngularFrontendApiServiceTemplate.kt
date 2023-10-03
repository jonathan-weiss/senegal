package ch.cassiamon.exampleapp.customizing.templates.angular.api

import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object AngularFrontendApiServiceTemplate {

    fun fillTemplate(templateModel: AngularModelClass): String {
        return """
            import { Injectable }       from '@angular/core';
            import { HttpClient }       from '@angular/common/http';
            import { Observable }       from 'rxjs';
            
            import { ${templateModel.entityName}TO } from "./${templateModel.entityFileName}-to.model";
            import { ${templateModel.entityName}IdTO } from "./${templateModel.entityFileName}-id-to.model";
            import { Create${templateModel.entityName}InstructionTO } from "./create-${templateModel.entityFileName}-instruction-to.model";
            import { Update${templateModel.entityName}InstructionTO } from "./update-${templateModel.entityFileName}-instruction-to.model";
            import { Delete${templateModel.entityName}InstructionTO } from "./delete-${templateModel.entityFileName}-instruction-to.model";
            import { Search${templateModel.entityName}InstructionTO } from "./search-${templateModel.entityFileName}-instruction-to.model";
            
            @Injectable({
            providedIn: 'root'
            })
            export class ${templateModel.entityName}ApiService {
            
                constructor(private readonly httpClient: HttpClient) {}
            
                get${templateModel.entityName}ById(${templateModel.transferObjectIdFieldName}: ${templateModel.entityName}IdTO): Observable<${templateModel.entityName}TO> {
                    return this.httpClient.get<${templateModel.entityName}TO>(`/api/${templateModel.restApiUrlPrefixName}/entry/` + ${templateModel.transferObjectIdFieldName}.value);
                }
            
                getAll${templateModel.entityName}(): Observable<ReadonlyArray<${templateModel.entityName}TO>> {
                    return this.httpClient.get<Array<${templateModel.entityName}TO>>(`/api/${templateModel.restApiUrlPrefixName}/all`);
                }
                
                searchAll${templateModel.entityName}(searchCriteria: Search${templateModel.entityName}InstructionTO): Observable<ReadonlyArray<${templateModel.entityName}TO>> {
                    return this.httpClient.post<Array<${templateModel.entityName}TO>>(`/api/${templateModel.restApiUrlPrefixName}/search`, searchCriteria);
                }
                
                create${templateModel.entityName}(createInstruction: Create${templateModel.entityName}InstructionTO): Observable<${templateModel.entityName}TO> {
                    return this.httpClient.post<${templateModel.entityName}TO>(`/api/${templateModel.restApiUrlPrefixName}/entry`, createInstruction);
                }
            
                update${templateModel.entityName}(updateInstruction: Update${templateModel.entityName}InstructionTO): Observable<${templateModel.entityName}TO> {
                    return this.httpClient.put<${templateModel.entityName}TO>(`/api/${templateModel.restApiUrlPrefixName}/entry`, updateInstruction);
                }
            
                delete${templateModel.entityName}(deleteInstruction: Delete${templateModel.entityName}InstructionTO): Observable<void> {
                    return this.httpClient.post<void>(`/api/${templateModel.restApiUrlPrefixName}/entry/delete`, deleteInstruction);
                }
            
            }
            
        """.identForMarker()
    }
}
