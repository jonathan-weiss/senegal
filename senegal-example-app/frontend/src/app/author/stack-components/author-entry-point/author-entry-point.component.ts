import { Component, OnInit} from '@angular/core';
import {ComponentStackService} from "../../../shared/component-stack/component-stack.service";
import {AuthorSearchStackEntryComponent} from "../author-search-stack-entry/author-search-stack-entry.component";
import {authorStackKey} from "../author-stack-key";
import {StackKey} from "../../../shared/component-stack/stack-key";

@Component({
  selector: "author-entry-point",
  templateUrl: "author-entry-point.component.html",
  styleUrls: ["author-entry-point.component.scss"],
})
export class AuthorEntryPointComponent implements OnInit{

  stackKey: StackKey = authorStackKey;

  constructor(private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    setTimeout(() => this.loadInitialComponent());
  }

  private loadInitialComponent(): void {
    this.componentStackService.newComponentOnStack(this.stackKey, AuthorSearchStackEntryComponent, (component: AuthorSearchStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.showCancelButton = false;
      component.showAddButton = true;
      component.showSelectButton = false;
      component.showEditButton = true;
      component.showDeleteButton = true;
    })
  }
}
