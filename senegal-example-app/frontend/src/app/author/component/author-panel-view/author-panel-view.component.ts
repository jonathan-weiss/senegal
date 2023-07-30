import {Component, OnInit} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {DeleteAuthorInstructionTO} from "../../api/delete-author-instruction-to.model";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {AuthorUpdateViewComponent} from "../author-update-view/author-update-view.component";
import {AuthorCreateViewComponent} from "../author-create-view/author-create-view.component";
import {AuthorService} from "../../author.service";

@Component({
  selector: 'author-panel-view',
  templateUrl: './author-panel-view.component.html',
  styleUrls: ['./author-panel-view.component.scss'],
})
export class AuthorPanelViewComponent implements OnInit {

  allAuthor: ReadonlyArray<AuthorTO> = []

  isEditingDisabled: boolean = false;

  highlightedAuthor: AuthorTO | undefined = undefined;

  constructor(private authorService: AuthorService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.loadAllAuthor();
  }

  private loadAllAuthor(): void {
    this.authorService
      .getAllAuthors()
      .subscribe((entities: ReadonlyArray<AuthorTO>) => {
        this.allAuthor = entities;
      });
  }

  onNewEntry(): void {
    this.isEditingDisabled = true;
    this.highlightedAuthor = undefined;
    this.componentStackService.newComponentOnStack(AuthorCreateViewComponent, (component: AuthorCreateViewComponent) => {
      component.saveClicked.subscribe((author) => this.reloadAllAuthorsAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllAuthorsAfterEditing());
    });
  }

  onEdit(entry: AuthorTO): void {
    this.isEditingDisabled = true;
    this.highlightedAuthor = entry;
    this.componentStackService.newComponentOnStack(AuthorUpdateViewComponent, (component: AuthorUpdateViewComponent) => {
      component.author = entry;
      component.saveClicked.subscribe((author) => this.reloadAllAuthorsAfterEditing(author));
      component.cancelClicked.subscribe(() => this.reloadAllAuthorsAfterEditing());
    })
  }


  onPerformDeleteOnServer(entry: AuthorTO): void {
    const deleteInstruction: DeleteAuthorInstructionTO = {
      authorId: entry.authorId,
    }
    this.authorService.deleteAuthor(deleteInstruction).subscribe(() => {
      this.loadAllAuthor();
    });
  }

  private reloadAllAuthorsAfterEditing(highlightedEntry: AuthorTO | undefined = undefined): void {
    this.loadAllAuthor();
    this.isEditingDisabled = false;
    this.highlightedAuthor = highlightedEntry;
  }
}
