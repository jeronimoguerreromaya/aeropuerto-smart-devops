import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VueloListComponent } from './components/vuelo-list/vuelo-list.component';
import { NotificationPanelComponent } from './components/notification-panel/notification-panel.component';
import { EquipajeFormComponent } from './components/equipaje-form/equipaje-form.component';

type Vista = 'vuelos' | 'notificaciones' | 'equipaje';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    VueloListComponent,
    NotificationPanelComponent,
    EquipajeFormComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  vistaActual: Vista = 'vuelos';

  cambiarVista(vista: Vista): void {
    this.vistaActual = vista;
  }
}
