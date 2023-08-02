import {Component, OnInit} from '@angular/core';
import {BookService} from "../book.service";
import {DeleteBookInstructionTO} from "../api/delete-book-instruction.to";
import {BookTO} from "../api/book-to.model";
import {ComponentStackService} from "../../component-stack/component-stack.service";
import {BookFormViewComponent} from "../components/book-form-view/book-form-view.component";

@Component({
  selector: 'book-panel-view',
  templateUrl: './book-panel-view.component.html',
  styleUrls: ['./book-panel-view.component.scss'],
})
export class BookPanelViewComponent implements OnInit {

  allBooks: ReadonlyArray<BookTO> = []

  isEditingDisabled: boolean = false;

  highlightedBook: BookTO | undefined = undefined;

  constructor(private bookService: BookService,
              private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.loadAllBook();
  }

  private loadAllBook(): void {
    this.bookService
      .getAllBooks()
      .subscribe((entities: ReadonlyArray<BookTO>) => {
        this.allBooks = entities;
      });
  }

  onNewEntry(): void {
    this.isEditingDisabled = true;
    this.highlightedBook = undefined;
    this.componentStackService.newComponentOnStack(BookFormViewComponent, (component: BookFormViewComponent) => {
      component.saveClicked.subscribe((book) => this.reloadAllBooksAfterEditing(book));
      component.cancelClicked.subscribe(() => this.reloadAllBooksAfterEditing());
    });
  }

  onEdit(entry: BookTO): void {
    this.isEditingDisabled = true;
    this.highlightedBook = entry;
    this.componentStackService.newComponentOnStack(BookFormViewComponent, (component: BookFormViewComponent) => {
      component.book = entry;
      component.saveClicked.subscribe((book) => this.reloadAllBooksAfterEditing(book));
      component.cancelClicked.subscribe(() => this.reloadAllBooksAfterEditing());
    })
  }


  onPerformDeleteOnServer(entry: BookTO): void {
    const deleteInstruction: DeleteBookInstructionTO = {
      bookId: entry.bookId,
    }
    this.bookService.deleteBook(deleteInstruction).subscribe(() => {
      this.loadAllBook();
    });
  }

  private reloadAllBooksAfterEditing(highlightedEntry: BookTO | undefined = undefined): void {
    this.loadAllBook();
    this.isEditingDisabled = false;
    this.highlightedBook = highlightedEntry;
  }
}
