import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VueloService } from '../../services/vuelo.service';
import { Vuelo } from '../../models/vuelo.model';

@Component({
  selector: 'app-vuelo-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vuelo-list.component.html',
  styleUrls: ['./vuelo-list.component.css']
})
export class VueloListComponent implements OnInit {

  vuelos: Vuelo[] = [];
  vuelosFiltrados: Vuelo[] = [];
  cargando = true;
  error = '';
  busqueda = '';

  constructor(private vueloService: VueloService) {}

  ngOnInit(): void {
    this.cargarVuelos();
  }

  cargarVuelos(): void {
    this.cargando = true;
    this.error = '';
    this.vueloService.listarVuelos().subscribe({
      next: (data) => {
        this.vuelos = data;
        this.vuelosFiltrados = data;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la información de vuelos.';
        this.cargando = false;
      }
    });
  }

  filtrar(): void {
    const q = this.busqueda.toLowerCase().trim();
    if (!q) {
      this.vuelosFiltrados = this.vuelos;
      return;
    }
    this.vuelosFiltrados = this.vuelos.filter(v =>
      v.numeroVuelo.toLowerCase().includes(q) ||
      v.origen.toLowerCase().includes(q) ||
      v.destino.toLowerCase().includes(q)
    );
  }

  getEstadoIcono(estado: string): string {
    const iconos: Record<string, string> = {
      'PROGRAMADO': '🕐',
      'RETRASADO':  '⏰',
      'CANCELADO':  '❌',
      'EMBARCANDO': '🛫'
    };
    return iconos[estado] ?? '✈️';
  }

  getEstadoClase(estado: string): string {
    const clases: Record<string, string> = {
      'PROGRAMADO': 'estado-programado',
      'RETRASADO':  'estado-retrasado',
      'CANCELADO':  'estado-cancelado',
      'EMBARCANDO': 'estado-embarcando'
    };
    return clases[estado] ?? '';
  }
}
