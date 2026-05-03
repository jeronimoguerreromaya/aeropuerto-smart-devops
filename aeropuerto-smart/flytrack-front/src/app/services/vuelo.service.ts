import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vuelo } from '../models/vuelo.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VueloService {

  private readonly apiUrl = `${environment.apiUrl}/vuelos`;

  constructor(private http: HttpClient) {}

  listarVuelos(): Observable<Vuelo[]> {
    return this.http.get<Vuelo[]>(this.apiUrl);
  }

  obtenerVuelo(id: number): Observable<Vuelo> {
    return this.http.get<Vuelo>(`${this.apiUrl}/${id}`);
  }

  crearVuelo(vuelo: Vuelo): Observable<Vuelo> {
    return this.http.post<Vuelo>(this.apiUrl, vuelo);
  }

  actualizarEstado(id: number, estado: string, puertaEmbarque?: string): Observable<Vuelo> {
    return this.http.patch<Vuelo>(`${this.apiUrl}/${id}/estado`, { estado, puertaEmbarque });
  }
}
