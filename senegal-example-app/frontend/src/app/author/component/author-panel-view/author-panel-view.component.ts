
import { Component, OnInit } from '@angular/core';
import { AuthorApiService } from "../../api/author-api.service";
import { AuthorTO } from "../../api/author-to.model";
import { CreateAuthorInstructionTO } from "../../api/create-author-instruction-to.model";
import { UpdateAuthorInstructionTO } from "../../api/update-author-instruction-to.model";
import { DeleteAuthorInstructionTO } from "../../api/delete-author-instruction-to.model";

@Component({
    selector: 'author-panel-view',
    templateUrl: './author-panel-view.component.html',
    styleUrls: ['./author-panel-view.component.scss'],
})
export class AuthorPanelViewComponent implements OnInit {

    allAuthor: ReadonlyArray<AuthorTO> = []

    updateAuthorInstruction: UpdateAuthorInstructionTO | undefined = undefined;
    createAuthorInstruction: CreateAuthorInstructionTO | undefined = undefined;

    constructor(private authorService: AuthorApiService) {
    }

    ngOnInit(): void {
        this.loadAllAuthor();
    }

    private loadAllAuthor(): void {
        this.authorService
            .getAllAuthor()
            .subscribe((entities: ReadonlyArray<AuthorTO>) => {
                this.allAuthor = entities;
            });
    }

    isEditingMode(): boolean {
        return this.createAuthorInstruction != undefined || this.updateAuthorInstruction != undefined;
    }

    onEdit(entry: AuthorTO): void {
        this.createAuthorInstruction = undefined;
        this.updateAuthorInstruction = {
            authorId: entry.authorId,
            firstname: entry.firstname,
            lastname: entry.lastname,
        }
    }

    onPerformDelete(entry: AuthorTO): void {
        const deleteInstruction: DeleteAuthorInstructionTO = {
            authorId: entry.authorId,
        }
        this.authorService.deleteAuthor(deleteInstruction).subscribe(() => {
            this.loadAllAuthor();
        });
    }

    onPerformUpdate(updateInstruction: UpdateAuthorInstructionTO): void {
        this.authorService.updateAuthor(updateInstruction).subscribe(() => {
            this.loadAllAuthor();
            this.updateAuthorInstruction = undefined;
            this.createAuthorInstruction = undefined;
        })
    }

    onPerformCreate(createInstruction: CreateAuthorInstructionTO): void {
        this.authorService.createAuthor(createInstruction).subscribe(() => {
            this.loadAllAuthor();
            this.updateAuthorInstruction = undefined;
            this.createAuthorInstruction = undefined;
        })
    }

    onNewEntry(): void {
        this.updateAuthorInstruction = undefined;
        this.createAuthorInstruction = {
            firstname: '',
            lastname: '',
        }
    }

    resetEdit() {
        this.updateAuthorInstruction = undefined;
        this.createAuthorInstruction = undefined;
    }

}
