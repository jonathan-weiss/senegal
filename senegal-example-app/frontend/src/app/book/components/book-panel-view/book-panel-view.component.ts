import {Component, OnInit} from '@angular/core';
import {ComponentStackService} from "../../../component-stack/component-stack.service";

@Component({
  selector: 'book-panel-view',
  templateUrl: './book-panel-view.component.html',
  styleUrls: ['./book-panel-view.component.scss'],
})
export class BookPanelViewComponent implements OnInit{

  constructor(private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.componentStackService.resetComponentStack();
  }



}
