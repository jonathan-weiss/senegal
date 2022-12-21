import { Component, OnInit } from '@angular/core';
import { ${templateModel.AngularFrontendEntityName}ApiService } from "../../api/${templateModel.AngularFrontendEntityFilename}-api.service";
import { ${templateModel.AngularFrontendEntityName}TO } from "../../api/${templateModel.AngularFrontendEntityFilename}-to.model";


@Component({
    selector: '${templateModel.AngularFrontendEntityFilename}-panel-view',
    templateUrl: './${templateModel.AngularFrontendEntityFilename}-panel-view.component.html',
    styleUrls: ['./${templateModel.AngularFrontendEntityFilename}-panel-view.component.scss'],
})
export class ${templateModel.AngularFrontendEntityName}PanelViewComponent implements OnInit {

    all${templateModel.AngularFrontendEntityName}: ReadonlyArray<${templateModel.AngularFrontendEntityName}TO> = []

    constructor(private ${templateModel.AngularFrontendDecapitalizedEntityName}Service: ${templateModel.AngularFrontendEntityName}ApiService) {
    }

    ngOnInit(): void {
        this.loadAll${templateModel.AngularFrontendEntityName}();
    }

    private loadAll${templateModel.AngularFrontendEntityName}(): void {
        this.${templateModel.AngularFrontendDecapitalizedEntityName}Service
            .getAll${templateModel.AngularFrontendEntityName}()
            .subscribe((entities: ReadonlyArray<${templateModel.AngularFrontendEntityName}TO>) => {
                this.all${templateModel.AngularFrontendEntityName} = entities;
            });
    }
}
