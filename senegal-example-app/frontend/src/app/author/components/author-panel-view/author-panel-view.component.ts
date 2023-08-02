import {Component, OnInit} from '@angular/core';
import {ComponentStackService} from "../../../component-stack/component-stack.service";

@Component({
  selector: 'author-panel-view',
  templateUrl: './author-panel-view.component.html',
  styleUrls: ['./author-panel-view.component.scss'],
})
export class AuthorPanelViewComponent implements OnInit {

  constructor(private componentStackService: ComponentStackService) {
  }

  ngOnInit(): void {
    this.componentStackService.resetComponentStack();
  }


}
