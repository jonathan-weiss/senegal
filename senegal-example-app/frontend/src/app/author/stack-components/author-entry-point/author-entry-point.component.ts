import {Component, OnInit} from '@angular/core';
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {AuthorSearchStackEntryComponent} from "../author-search-stack-entry/author-search-stack-entry.component";

@Component({
  selector: "author-entry-point",
  template: "",
  styleUrls: [],
})
export class AuthorEntryPointComponent implements OnInit{

  constructor(private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.componentStackService.resetComponentStack();
    this.componentStackService.newComponentOnStack(AuthorSearchStackEntryComponent, (component: AuthorSearchStackEntryComponent) => {
      component.showCancelButton=false;
      component.showAddButton=true;
      component.showSelectButton=false;
      component.showEditButton=true;
      component.showDeleteButton=true;
    })
  }



}
