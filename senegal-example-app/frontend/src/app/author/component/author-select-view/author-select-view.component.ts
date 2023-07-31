import {Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthorTO} from "../../api/author-to.model";
import { Validators } from '@angular/forms';
import {BookTO} from "../../../book/api/book-to.model";
import {CollectionsUtil} from "../../../commons/collections.util";
import {BookService} from "../../../book/book.service";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {AuthorService} from "../../author.service";
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {AuthorCreateViewComponent} from "../author-create-view/author-create-view.component";
import {AuthorUpdateViewComponent} from "../author-update-view/author-update-view.component";
import {DeleteAuthorInstructionTO} from "../../api/delete-author-instruction-to.model";


@Component({
  selector: 'author-select-view',
  templateUrl: './author-select-view.component.html',
  styleUrls: ['./author-select-view.component.scss'],
})
export class AuthorSelectViewComponent implements OnInit {

  @Output() selectClicked: EventEmitter<AuthorTO> = new EventEmitter<AuthorTO>();
  @Output() cancelClicked: EventEmitter<void> = new EventEmitter<void>();

  allAuthor: ReadonlyArray<AuthorTO> = []

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

  onSelect(author: AuthorTO): void {
    this.selectClicked.emit(author);
    this.componentStackService.removeLatestComponentFromStack();
  }

  onCancel(): void {
    this.cancelClicked.emit();
    this.componentStackService.removeLatestComponentFromStack();
  }
}
