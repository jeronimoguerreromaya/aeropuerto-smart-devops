import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { ReporteEquipajeService } from './reporte-equipaje.service';
import { ReporteEquipaje } from '../models/reporte-equipaje.model';

describe('ReporteEquipajeService', () => {
  let service: ReporteEquipajeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReporteEquipajeService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(ReporteEquipajeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('debe crearse correctamente', () => {
    expect(service).toBeTruthy();
  });

  it('debe listar reportes con GET', () => {
    const mockReportes: ReporteEquipaje[] = [
      { id: 1, pasajeroNombre: 'Juan Pérez', documentoPasajero: '123456789',
        email: 'juan@email.com', telefono: '3001234567',
        descripcion: 'Maleta perdida', estadoReclamo: 'PENDIENTE' }
    ];

    service.listarReportes().subscribe(reportes => {
      expect(reportes.length).toBe(1);
      expect(reportes[0].pasajeroNombre).toBe('Juan Pérez');
    });

    const req = httpMock.expectOne(r => r.url.includes('/reportes'));
    expect(req.request.method).toBe('GET');
    req.flush(mockReportes);
  });

  it('debe crear un reporte con POST', () => {
    const nuevoReporte = {
      pasajeroNombre: 'Ana López',
      documentoPasajero: '987654321',
      email: 'ana@email.com',
      telefono: '3109876543',
      descripcion: 'Maleta dañada',
      vueloId: 2
    };

    const mockRespuesta: ReporteEquipaje = {
      id: 2, pasajeroNombre: 'Ana López', documentoPasajero: '987654321',
      email: 'ana@email.com', telefono: '3109876543',
      descripcion: 'Maleta dañada', estadoReclamo: 'PENDIENTE'
    };

    service.crearReporte(nuevoReporte).subscribe(reporte => {
      expect(reporte.id).toBe(2);
      expect(reporte.estadoReclamo).toBe('PENDIENTE');
    });

    const req = httpMock.expectOne(r => r.url.includes('/reportes'));
    expect(req.request.method).toBe('POST');
    expect(req.request.body.pasajeroNombre).toBe('Ana López');
    req.flush(mockRespuesta);
  });
});
