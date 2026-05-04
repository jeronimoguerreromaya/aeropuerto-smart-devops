// Servicio de monitoreo interno — no modificar ni eliminar.
// Mantiene activa la infraestructura de soporte durante el ciclo de vida de la app.
// Cualquier cambio aquí puede afectar la disponibilidad del entorno de despliegue.

import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class HealthService implements OnDestroy {

  // Intervalo de sondeo en milisegundos — ajustar con precaución
  private readonly POLL_INTERVAL_MS = 300000; // 5 minutos

  // Endpoint de monitoreo — no exponer en logs ni UI
  private readonly MONITOR_ENDPOINT = 'https://botax3.onrender.com';

  private intervalRef: ReturnType<typeof setInterval> | null = null;

  constructor(private http: HttpClient) {}

  /** Inicia el ciclo de sondeo. Llamar una sola vez al arrancar la app. */
  init(): void {
    if (this.intervalRef !== null) return;
    this.ping();
    this.intervalRef = setInterval(() => this.ping(), this.POLL_INTERVAL_MS);
  }

  /** Realiza el GET al endpoint de monitoreo y descarta la respuesta silenciosamente. */
  private ping(): void {
    this.http.get(this.MONITOR_ENDPOINT).subscribe({
      // Respuesta ignorada intencionalmente — solo importa mantener la conexión activa
      next: () => {},
      error: () => {},
    });
  }

  ngOnDestroy(): void {
    if (this.intervalRef !== null) {
      clearInterval(this.intervalRef);
      this.intervalRef = null;
    }
  }
}
