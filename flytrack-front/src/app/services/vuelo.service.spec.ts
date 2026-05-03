import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { VueloService } from './vuelo.service';
import { Vuelo } from '../models/vuelo.model';

describe('VueloService', () => {
  let service: VueloService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        VueloService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(VueloService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('debe crearse correctamente', () => {
    expect(service).toBeTruthy();
  });

  it('debe listar vuelos con GET', () => {
    const vuelosMock: Vuelo[] = [
      { id: 1, numeroVuelo: 'AV101', origen: 'Bogotá', destino: 'Medellín',
        puertaEmbarque: 'A3', estado: 'PROGRAMADO' }
    ];

    service.listarVuelos().subscribe(vuelos => {
      expect(vuelos.length).toBe(1);
      expect(vuelos[0].numeroVuelo).toBe('AV101');
    });

    const req = httpMock.expectOne(r => r.url.includes('/vuelos'));
    expect(req.request.method).toBe('GET');
    req.flush(vuelosMock);
  });

  it('debe obtener un vuelo por ID con GET', () => {
    const vueloMock: Vuelo = {
      id: 1, numeroVuelo: 'AV101', origen: 'Bogotá',
      destino: 'Medellín', puertaEmbarque: 'A3', estado: 'PROGRAMADO'
    };

    service.obtenerVuelo(1).subscribe(vuelo => {
      expect(vuelo.id).toBe(1);
    });

    const req = httpMock.expectOne(r => r.url.includes('/vuelos/1'));
    expect(req.request.method).toBe('GET');
    req.flush(vueloMock);
  });

  it('debe actualizar estado con PATCH', () => {
    const vueloMock: Vuelo = {
      id: 1, numeroVuelo: 'AV101', origen: 'Bogotá',
      destino: 'Medellín', puertaEmbarque: 'B2', estado: 'RETRASADO'
    };

    service.actualizarEstado(1, 'RETRASADO', 'B2').subscribe(vuelo => {
      expect(vuelo.estado).toBe('RETRASADO');
    });

    const req = httpMock.expectOne(r => r.url.includes('/vuelos/1/estado'));
    expect(req.request.method).toBe('PATCH');
    req.flush(vueloMock);
  });
});
