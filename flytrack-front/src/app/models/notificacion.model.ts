export interface Notificacion {
  id?: number;
  mensaje: string;
  tipo: string;
  fechaEnvio: string;
  vuelo?: {
    id: number;
    numeroVuelo: string;
    origen: string;
    destino: string;
    estado: string;
  };
}
