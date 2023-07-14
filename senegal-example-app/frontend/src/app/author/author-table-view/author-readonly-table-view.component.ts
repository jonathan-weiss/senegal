import {Component, Input} from '@angular/core';
import {CollectionsUtil} from "../../commons/collections.util";
import {AuthorTO} from "../api/author-to.model";


@Component({
  selector: 'author-readonly-table-view',
  templateUrl: './author-readonly-table-view.component.html',
  styleUrls: ['./author-readonly-table-view.component.scss'],
})
export class AuthorReadonlyTableViewComponent {

  @Input() authors!: ReadonlyArray<AuthorTO>

  displayedColumns: string[] = ['authorId', 'firstname', 'lastname'];

}
