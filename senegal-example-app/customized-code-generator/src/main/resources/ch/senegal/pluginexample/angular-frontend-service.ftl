
import { Injectable }       from '@angular/core';
import { HttpClient }       from '@angular/common/http';
import { Observable }       from 'rxjs';
import { UuidTO }           from '../../../generated-openapi';

import { ${templateModel.AngularFrontendEntityName}TO } from "./${templateModel.AngularFrontendEntityFilename}-to.model";
import { Create${templateModel.AngularFrontendEntityName}InstructionTO } from "./create-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";
import { Update${templateModel.AngularFrontendEntityName}InstructionTO } from "./update-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";
import { Delete${templateModel.AngularFrontendEntityName}InstructionTO } from "./delete-${templateModel.AngularFrontendEntityFilename}-instruction-to.model";

@Injectable({
providedIn: 'root'
})
export class ${templateModel.AngularFrontendEntityName}ApiService {

    constructor(private readonly httpClient: HttpClient) {}

    get${templateModel.AngularFrontendEntityName}ById(${templateModel.AngularFrontendTransferObjectIdFieldName}: ${templateModel.AngularFrontendTransferObjectIdFieldType}): Observable<${templateModel.AngularFrontendEntityName}TO> {
        return this.httpClient.get<${templateModel.AngularFrontendEntityName}TO>(`/api/${templateModel.RestApiUrlPrefixName}/entry/` + ${templateModel.AngularFrontendTransferObjectIdFieldName}.uuid);
    }

    getAll${templateModel.AngularFrontendEntityName}(): Observable<ReadonlyArray<${templateModel.AngularFrontendEntityName}TO>> {
        return this.httpClient.get<Array<${templateModel.AngularFrontendEntityName}TO>>(`/api/${templateModel.RestApiUrlPrefixName}/all`);
    }

    create${templateModel.AngularFrontendEntityName}(createInstruction: Create${templateModel.AngularFrontendEntityName}InstructionTO): Observable<${templateModel.AngularFrontendEntityName}TO> {
        return this.httpClient.post<${templateModel.AngularFrontendEntityName}TO>(`/api/${templateModel.RestApiUrlPrefixName}/entry`, createInstruction);
    }

    update${templateModel.AngularFrontendEntityName}(updateInstruction: Update${templateModel.AngularFrontendEntityName}InstructionTO): Observable<${templateModel.AngularFrontendEntityName}TO> {
        return this.httpClient.put<${templateModel.AngularFrontendEntityName}TO>(`/api/${templateModel.RestApiUrlPrefixName}/entry`, updateInstruction);
    }

    delete${templateModel.AngularFrontendEntityName}(deleteInstruction: Delete${templateModel.AngularFrontendEntityName}InstructionTO): Observable<void> {
        return this.httpClient.post<void>(`/api/${templateModel.RestApiUrlPrefixName}/entry/delete`, deleteInstruction);
    }

}
