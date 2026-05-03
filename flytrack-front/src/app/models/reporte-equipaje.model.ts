export interface ReporteEquipaje {
  id?: number;
  pasajeroNombre: string;
  documentoPasajero: string;
  email: string;
  telefono: string;
  descripcion: string;
  vueloId?: number;
  vuelo?: {
    id: number;
    numeroVuelo: string;
    origen: string;
    destino: string;
  };
  fechaReporte?: string;
  estadoReclamo: 'PENDIENTE' | 'EN_PROCESO' | 'RESUELTO';
}
