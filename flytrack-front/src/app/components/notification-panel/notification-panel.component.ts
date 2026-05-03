import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificacionService } from '../../services/notificacion.service';
import { Notificacion } from '../../models/notificacion.model';

interface NotificacionUI extends Notificacion {
  leida: boolean;
}

@Component({
  selector: 'app-notification-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-panel.component.html',
  styleUrls: ['./notification-panel.component.css']
})
export class NotificationPanelComponent implements OnInit {

  notificaciones: NotificacionUI[] = [];
  cargando = true;

  constructor(private notificacionService: NotificacionService) {}

  ngOnInit(): void {
    this.cargarNotificaciones();
  }

  cargarNotificaciones(): void {
    this.cargando = true;
    this.notificacionService.listarTodas().subscribe({
      next: (data) => {
        // Conservar estado "leida" si ya existía
        this.notificaciones = data.map(n => ({
          ...n,
          leida: this.notificaciones.find(x => x.id === n.id)?.leida ?? false
        }));
        this.cargando = false;
      },
      error: () => { this.cargando = false; }
    });
  }

  marcarLeida(notif: NotificacionUI): void {
    notif.leida = true;
  }

  marcarTodasLeidas(): void {
    this.notificaciones.forEach(n => n.leida = true);
  }

  get noLeidas(): number {
    return this.notificaciones.filter(n => !n.leida).length;
  }

  getTipoIcono(tipo: string): string {
    const iconos: Record<string, string> = {
      'RETRASO':     '⏰',
      'CANCELACION': '❌',
      'EMBARQUE':    '🛫',
      'CAMBIO_PUERTA': '🚪'
    };
    return iconos[tipo] ?? '📢';
  }

  getTipoClase(tipo: string): string {
    const clases: Record<string, string> = {
      'RETRASO':     'notif-retraso',
      'CANCELACION': 'notif-cancelacion',
      'EMBARQUE':    'notif-embarque',
      'CAMBIO_PUERTA': 'notif-cambio'
    };
    return clases[tipo] ?? 'notif-default';
  }
}
