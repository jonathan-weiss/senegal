import {Component, EventEmitter, Input, OnInit} from '@angular/core';
import {BookTO} from "../../../../book/api/book-to.model";
import {BookCreateViewComponent} from "../../../../book/book-create-view/book-create-view.component";
import {AuthorTO} from "../../../api/author-to.model";
import {CollectionsUtil} from "../../../../commons/collections.util";
import {AuthorService} from "../../../author.service";
import {ComponentStackService} from "../../../../component-stack/component-stack.service";
import {BookFormViewComponent} from "../../../../book/components/book-form-view/book-form-view.component";


@Component({
  selector: 'all-book-by-author',
  templateUrl: './all-book-by-author.component.html',
  styleUrls: ['./all-book-by-author.component.scss'],
})
export class AllBookByAuthorComponent implements OnInit {

  @Input() author: AuthorTO | undefined;
  @Input() reloadAllBookEvent: EventEmitter<void> | undefined = undefined;

  booksByAuthor: ReadonlyArray<BookTO> = CollectionsUtil.emptyList();

  constructor(private readonly authorService: AuthorService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    if(this.reloadAllBookEvent) {
      this.reloadAllBookEvent.subscribe(() => this.reloadBooksByAuthor())
    }
  }

  private reloadBooksByAuthor(): void {
    if(this.author != undefined) {
      this.authorService.getAllBooksByAuthor(this.author.authorId).subscribe((books: ReadonlyArray<BookTO>) => {
        this.booksByAuthor = books;
      });
    }
  }

  addBook() {
    this.componentStackService.newComponentOnStack(BookFormViewComponent, (component: BookFormViewComponent) => {
      component.fixedMainAuthor = this.author;
      component.saveClicked.subscribe((author) => this.reloadBooksByAuthor());
      component.cancelClicked.subscribe(() => this.reloadBooksByAuthor());
    });

  }

}
