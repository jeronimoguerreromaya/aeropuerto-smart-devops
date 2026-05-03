import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { NotificacionService } from './notificacion.service';
import { Notificacion } from '../models/notificacion.model';

describe('NotificacionService', () => {
  let service: NotificacionService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        NotificacionService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(NotificacionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('debe crearse correctamente', () => {
    expect(service).toBeTruthy();
  });

  it('debe listar todas las notificaciones con GET', () => {
    const mockNotificaciones: Notificacion[] = [
      { id: 1, mensaje: 'Vuelo retrasado', tipo: 'RETRASO', fechaEnvio: '2024-01-01T10:00:00' }
    ];

    service.listarTodas().subscribe(notificaciones => {
      expect(notificaciones.length).toBe(1);
      expect(notificaciones[0].mensaje).toBe('Vuelo retrasado');
    });

    const req = httpMock.expectOne(r => r.url.includes('/notificaciones'));
    expect(req.request.method).toBe('GET');
    req.flush(mockNotificaciones);
  });

  it('debe listar notificaciones por vuelo con GET', () => {
    const mockNotificaciones: Notificacion[] = [
      { id: 1, mensaje: 'Puerta cambiada', tipo: 'CAMBIO_PUERTA', fechaEnvio: '2024-01-01T11:00:00' }
    ];

    service.listarPorVuelo(2).subscribe(notificaciones => {
      expect(notificaciones[0].mensaje).toBe('Puerta cambiada');
    });

    const req = httpMock.expectOne(r => r.url.includes('/notificaciones/vuelo/2'));
    expect(req.request.method).toBe('GET');
    req.flush(mockNotificaciones);
  });
});
