import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ComponentStackService} from "../../../shared/component-stack/component-stack.service";
import {BookSearchStackEntryComponent} from "../book-search-stack-entry/book-search-stack-entry.component";
import {bookStackKey} from "../book-stack-key";
import {StackKey} from "../../../shared/component-stack/stack-key";
import {DisplayComponentStackComponent} from '../../../shared/component-stack/display-component-stack.component';

@Component({
  selector: "book-entry-point",
  templateUrl: "book-entry-point.component.html",
  styleUrls: ["book-entry-point.component.scss"],
  standalone: true,
  imports: [
    DisplayComponentStackComponent
  ]
})
export class BookEntryPointComponent implements OnInit{

  stackKey: StackKey = bookStackKey;
  constructor(private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    setTimeout(() => this.loadInitialComponent());
  }

  private loadInitialComponent(): void {
    this.componentStackService.newComponentOnStack(this.stackKey, BookSearchStackEntryComponent, (component: BookSearchStackEntryComponent) => {
      component.stackKey = this.stackKey;
      component.showCancelButton=false;
      component.showAddButton=true;
      component.showSelectButton=false;
      component.showEditButton=true;
      component.showDeleteButton=true;
    })

  }


}
