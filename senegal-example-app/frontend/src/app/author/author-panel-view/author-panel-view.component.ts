import {Component, OnInit} from '@angular/core';
import {CollectionsUtil} from "../../commons/collections.util";
import {AuthorTO} from "../api/author-to.model";
import {AuthorService} from "../author.service";

@Component({
  selector: 'author-panel-view',
  templateUrl: './author-panel-view.component.html',
  styleUrls: ['./author-panel-view.component.scss'],
})
export class AuthorPanelViewComponent implements OnInit {

  authors: ReadonlyArray<AuthorTO> = CollectionsUtil.emptyList()


  constructor(private authorService: AuthorService) {
  }

  ngOnInit(): void {
    this.loadAuthors();
  }

  private loadAuthors(): void {
    this.authorService.getAllAuthors().subscribe((authors: ReadonlyArray<AuthorTO>) => {
      this.authors = authors;
    })
  }
}
