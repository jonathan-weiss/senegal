
import { Injectable }       from '@angular/core';
import { HttpClient }       from '@angular/common/http';
import { Observable }       from 'rxjs';
import { UuidTO }           from '../../../generated-openapi';

import {${templateModel.AngularFrontendTransferObjectName}} from "./${templateModel.AngularFrontendTransferObjectFilename}";

@Injectable({
providedIn: 'root'
})
export class ${templateModel.AngularFrontendServiceName} {

    constructor(private readonly httpClient: HttpClient) {}

    get${templateModel.AngularFrontendEntityName}ById(${templateModel.AngularFrontendTransferObjectIdFieldName}: ${templateModel.AngularFrontendTransferObjectIdFieldType}): Observable<${templateModel.AngularFrontendTransferObjectName}> {
        return this.httpClient.get<${templateModel.AngularFrontendTransferObjectName}>(`/api/${templateModel.RestApiUrlPrefixName}/entry/` + ${templateModel.AngularFrontendTransferObjectIdFieldName}.uuid);
    }

    getAll${templateModel.AngularFrontendEntityName}(): Observable<ReadonlyArray<${templateModel.AngularFrontendTransferObjectName}>> {
        return this.httpClient.get<Array<${templateModel.AngularFrontendTransferObjectName}>>(`/api/${templateModel.RestApiUrlPrefixName}/all`);
    }
}
