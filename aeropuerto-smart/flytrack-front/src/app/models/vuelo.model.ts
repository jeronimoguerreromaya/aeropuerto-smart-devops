export interface Vuelo {
  id?: number;
  numeroVuelo: string;
  origen: string;
  destino: string;
  puertaEmbarque?: string;
  estado: 'PROGRAMADO' | 'RETRASADO' | 'CANCELADO' | 'EMBARCANDO';
}
