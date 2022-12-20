import { Component, OnInit } from '@angular/core';
import { ${templateModel.AngularFrontendServiceName} } from "../../api/${templateModel.AngularFrontendServiceFilename}";
import { ${templateModel.AngularFrontendTransferObjectName} } from "../../api/${templateModel.AngularFrontendTransferObjectFilename}";


@Component({
    selector: '${templateModel.AngularFrontendEntityFileName}-panel-view',
    templateUrl: './${templateModel.AngularFrontendEntityFileName}-panel-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFileName}-panel-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}PanelViewComponent implements OnInit {

    all${templateModel.AngularFrontendEntityName}: ReadonlyArray<${templateModel.AngularFrontendTransferObjectName}> = []

    constructor(private ${templateModel.AngularFrontendDecapitalizedEntityName}Service: ${templateModel.AngularFrontendServiceName}) {
    }

    ngOnInit(): void {
        this.loadAll${templateModel.AngularFrontendEntityName}();
    }

    private loadAll${templateModel.AngularFrontendEntityName}(): void {
        this.${templateModel.AngularFrontendDecapitalizedEntityName}Service
            .getAll${templateModel.AngularFrontendEntityName}()
            .subscribe((entities: ReadonlyArray<${templateModel.AngularFrontendTransferObjectName}>) => {
                this.all${templateModel.AngularFrontendEntityName} = entities;
            });
    }
}
