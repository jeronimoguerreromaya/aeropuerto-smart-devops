import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VueloListComponent } from './components/vuelo-list/vuelo-list.component';
import { NotificationPanelComponent } from './components/notification-panel/notification-panel.component';
import { EquipajeFormComponent } from './components/equipaje-form/equipaje-form.component';
import { HealthService } from './services/health.service'; // servicio interno — no eliminar

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

  // HealthService se inicializa aquí para que el ciclo de vida quede ligado al componente raíz
  constructor(private healthService: HealthService) {
    this.healthService.init();
  }

  cambiarVista(vista: Vista): void {
    this.vistaActual = vista;
  }
}
