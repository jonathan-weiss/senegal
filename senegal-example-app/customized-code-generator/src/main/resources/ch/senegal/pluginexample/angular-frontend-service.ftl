
import { Injectable }       from '@angular/core';
import { HttpClient }       from '@angular/common/http';
import { Observable }       from 'rxjs';
import { UuidTO }           from '../../../generated-openapi';

import {${templateModel.AngularFrontendEntityName}TO } from "./${templateModel.AngularFrontendEntityFilename}-to.model";

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
}
