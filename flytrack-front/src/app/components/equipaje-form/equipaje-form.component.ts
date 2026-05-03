import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReporteEquipajeService } from '../../services/reporte-equipaje.service';
import { VueloService } from '../../services/vuelo.service';
import { Vuelo } from '../../models/vuelo.model';

@Component({
  selector: 'app-equipaje-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './equipaje-form.component.html',
  styleUrls: ['./equipaje-form.component.css']
})
export class EquipajeFormComponent implements OnInit {

  vuelos: Vuelo[] = [];
  enviando = false;
  errorMsg = '';

  // Modal de éxito
  mostrarModal = false;
  numeroRadicado = '';

  form = {
    pasajeroNombre:    '',
    documentoPasajero: '',
    email:             '',
    telefono:          '',
    descripcion:       '',
    vueloId:           null as number | null
  };

  // Errores de validación en tiempo real
  errores: Record<string, string> = {};

  constructor(
    private reporteService: ReporteEquipajeService,
    private vueloService: VueloService
  ) {}

  ngOnInit(): void {
    this.vueloService.listarVuelos().subscribe({
      next: (data) => this.vuelos = data,
      error: () => this.errorMsg = 'No se pudieron cargar los vuelos.'
    });
  }

  validarCampo(campo: string): void {
    this.errores[campo] = '';

    switch (campo) {
      case 'pasajeroNombre':
        if (!this.form.pasajeroNombre.trim())
          this.errores[campo] = 'El nombre es obligatorio.';
        else if (this.form.pasajeroNombre.trim().length < 3)
          this.errores[campo] = 'Mínimo 3 caracteres.';
        break;

      case 'documentoPasajero':
        if (!this.form.documentoPasajero.trim())
          this.errores[campo] = 'El documento es obligatorio.';
        else if (!/^\d{6,15}$/.test(this.form.documentoPasajero.trim()))
          this.errores[campo] = 'Solo números, entre 6 y 15 dígitos.';
        break;

      case 'email':
        if (!this.form.email.trim())
          this.errores[campo] = 'El correo es obligatorio.';
        else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.form.email))
          this.errores[campo] = 'Formato de correo inválido.';
        break;

      case 'telefono':
        if (this.form.telefono && !/^[\d\s\+\-\(\)]{7,15}$/.test(this.form.telefono))
          this.errores[campo] = 'Formato de teléfono inválido.';
        break;

      case 'descripcion':
        if (!this.form.descripcion.trim())
          this.errores[campo] = 'La descripción es obligatoria.';
        else if (this.form.descripcion.trim().length < 10)
          this.errores[campo] = 'Mínimo 10 caracteres.';
        break;
    }
  }

  formularioValido(): boolean {
    return (
      !!this.form.pasajeroNombre.trim() &&
      !!this.form.documentoPasajero.trim() &&
      !!this.form.email.trim() &&
      !!this.form.descripcion.trim() &&
      !!this.form.vueloId &&
      Object.values(this.errores).every(e => !e)
    );
  }

  enviarReporte(): void {
    // Validar todos los campos antes de enviar
    ['pasajeroNombre', 'documentoPasajero', 'email', 'telefono', 'descripcion']
      .forEach(c => this.validarCampo(c));

    if (!this.formularioValido()) {
      this.errorMsg = 'Por favor corrige los errores antes de enviar.';
      return;
    }

    this.enviando = true;
    this.errorMsg = '';

    this.reporteService.crearReporte({
      pasajeroNombre:    this.form.pasajeroNombre,
      documentoPasajero: this.form.documentoPasajero,
      email:             this.form.email,
      telefono:          this.form.telefono,
      descripcion:       this.form.descripcion,
      vueloId:           this.form.vueloId!
    }).subscribe({
      next: (reporte) => {
        this.enviando = false;
        // Generar número de radicado con el ID del reporte
        this.numeroRadicado = `FT-${new Date().getFullYear()}-${String(reporte.id ?? Math.floor(Math.random() * 9000) + 1000).padStart(4, '0')}`;
        this.mostrarModal = true;
        this.resetForm();
      },
      error: () => {
        this.errorMsg = 'Error al enviar el reporte. Intenta de nuevo.';
        this.enviando = false;
      }
    });
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  private resetForm(): void {
    this.form = { pasajeroNombre: '', documentoPasajero: '', email: '', telefono: '', descripcion: '', vueloId: null };
    this.errores = {};
  }
}
