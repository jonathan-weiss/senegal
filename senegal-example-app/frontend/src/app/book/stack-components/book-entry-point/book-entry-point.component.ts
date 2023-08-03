import {Component, OnInit} from '@angular/core';
import {ComponentStackService} from "../../../component-stack/component-stack.service";
import {BookSearchStackEntryComponent} from "../book-search-stack-entry/book-search-stack-entry.component";

@Component({
  selector: "book-entry-point",
  template: "",
  styleUrls: [],
})
export class BookEntryPointComponent implements OnInit{

  constructor(private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.componentStackService.resetComponentStack();
    this.componentStackService.newComponentOnStack(BookSearchStackEntryComponent, (component: BookSearchStackEntryComponent) => {
      component.showCancelButton=false;
      component.showAddButton=true;
      component.showSelectButton=false;
      component.showEditButton=true;
      component.showDeleteButton=true;
    })
  }



}
