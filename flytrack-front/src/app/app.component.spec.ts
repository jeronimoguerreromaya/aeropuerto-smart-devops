import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { provideHttpClient } from '@angular/common/http';

describe('AppComponent', () => {

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [provideHttpClient()]
    }).compileComponents();
  });

  it('debe crearse correctamente', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('debe iniciar con la vista de vuelos', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.vistaActual).toBe('vuelos');
  });

  it('debe cambiar la vista al llamar cambiarVista()', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.cambiarVista('notificaciones');
    expect(app.vistaActual).toBe('notificaciones');

    app.cambiarVista('equipaje');
    expect(app.vistaActual).toBe('equipaje');

    app.cambiarVista('vuelos');
    expect(app.vistaActual).toBe('vuelos');
  });
});
