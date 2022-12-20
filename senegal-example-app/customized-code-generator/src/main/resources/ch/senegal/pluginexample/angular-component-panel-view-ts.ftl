import { Component, OnInit } from '@angular/core';
import { ${templateModel.AngularFrontendServiceName} } from "../../../api/${templateModel.AngularFrontendServiceFilename}";
import { ${templateModel.AngularFrontendTransferObjectName} } from "../../../model/${templateModel.AngularFrontendTransferObjectFilename}";


@Component({
    selector: '${templateModel.AngularComponentSuffixFileName}-panel-view',
    templateUrl: './${templateModel.AngularComponentSuffixFileName}-panel-view.component.html',
    styleUrls: ['./${templateModel.AngularComponentSuffixFileName}-panel-view.component.scss'],
})
export class ${templateModel.AngularComponentCapitalizeSuffixName}PanelViewComponent implements OnInit {

    all${templateModel.AngularComponentCapitalizeSuffixName}: ReadonlyArray<${templateModel.AngularFrontendTransferObjectName}> = []

    constructor(private ${templateModel.AngularComponentSuffixName}Service: ${templateModel.AngularFrontendServiceName}) {
    }

    ngOnInit(): void {
        this.loadAll${templateModel.AngularComponentCapitalizeSuffixName}();
    }

    private loadAll${templateModel.AngularComponentCapitalizeSuffixName}(): void {
        this.${templateModel.AngularComponentSuffixName}Service
            .getAll${templateModel.AngularFrontendEntityName}()
            .subscribe((entities: ReadonlyArray<${templateModel.AngularFrontendTransferObjectName}>) => {
                this.all${templateModel.AngularComponentCapitalizeSuffixName} = entities;
            });
    }
}
