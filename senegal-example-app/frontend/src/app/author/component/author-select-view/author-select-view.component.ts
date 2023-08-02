import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthorTO} from "../../api/author-to.model";
import {AuthorService} from "../../author.service";
import {ComponentStackService} from "../../../component-stack/component-stack.service";


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
