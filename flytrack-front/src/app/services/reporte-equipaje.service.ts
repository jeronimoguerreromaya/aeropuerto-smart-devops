import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReporteEquipaje } from '../models/reporte-equipaje.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReporteEquipajeService {

  private readonly apiUrl = `${environment.apiUrl}/reportes`;

  constructor(private http: HttpClient) {}

  listarReportes(): Observable<ReporteEquipaje[]> {
    return this.http.get<ReporteEquipaje[]>(this.apiUrl);
  }

  crearReporte(reporte: {
    pasajeroNombre: string;
    documentoPasajero: string;
    email: string;
    telefono: string;
    descripcion: string;
    vueloId: number;
  }): Observable<ReporteEquipaje> {
    return this.http.post<ReporteEquipaje>(this.apiUrl, reporte);
  }
}
