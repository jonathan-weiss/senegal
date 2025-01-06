import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SearchAuthorInstructionTO} from "../../api/search-author-instruction-to.model";
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'author-search-box',
  templateUrl: './author-search-box.component.html',
  styleUrls: ['./author-search-box.component.scss'],
  standalone: true,
  imports: [
    MatButtonModule,
  ]
})
export class AuthorSearchBoxComponent {
  @Input() isLocked!: boolean

  @Output() searchClicked: EventEmitter<SearchAuthorInstructionTO> = new EventEmitter<SearchAuthorInstructionTO>();

  searchButtonClicked(): void {
    const searchCriteria: SearchAuthorInstructionTO = {
      authorId: undefined,
      firstname: undefined,
      lastname: undefined,
    }
    this.searchClicked.emit(searchCriteria)
  }
}
