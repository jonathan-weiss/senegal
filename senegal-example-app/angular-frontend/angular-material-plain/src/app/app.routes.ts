import { Routes } from '@angular/router';
import {MyTableComponent} from './my-table/my-table.component';
import {MyAddressFormComponent} from './my-address-form/my-address-form.component';
import {MyTreeComponent} from './my-tree/my-tree.component';
import {MyDashboardComponent} from './my-dashboard/my-dashboard.component';
import {MyDragAndDropComponent} from './my-drag-and-drop/my-drag-and-drop.component';

export const routes: Routes = [
  { path: 'address-form', component: MyAddressFormComponent },
  { path: 'table', component: MyTableComponent },
  { path: 'dash-board', component: MyDashboardComponent },
  { path: 'tree', component: MyTreeComponent },
  { path: 'drag-and-drop', component: MyDragAndDropComponent },
];
