-- Datos iniciales para FlyTrack
-- Spring Boot ejecuta este archivo automaticamente al arrancar (con ddl-auto=create-drop o update)

INSERT INTO vuelos (numero_vuelo, origen, destino, puerta_embarque, estado)
VALUES
  ('AV101', 'Bogotá',    'Medellín',   'A3',  'PROGRAMADO'),
  ('AV205', 'Bogotá',    'Cali',       'B1',  'EMBARCANDO'),
  ('LA330', 'Medellín',  'Cartagena',  'C7',  'RETRASADO'),
  ('AV412', 'Cali',      'Bogotá',     'A5',  'PROGRAMADO'),
  ('LA089', 'Cartagena', 'Bogotá',     'D2',  'CANCELADO'),
  ('AV550', 'Bogotá',    'Bucaramanga','B4',  'PROGRAMADO')
ON CONFLICT (numero_vuelo) DO NOTHING;

INSERT INTO notificaciones (mensaje, tipo, fecha_envio, vuelo_id)
VALUES
  ('El vuelo LA330 ha cambiado de estado: PROGRAMADO → RETRASADO',
   'RETRASO',    NOW() - INTERVAL '30 minutes', (SELECT id FROM vuelos WHERE numero_vuelo = 'LA330')),
  ('El vuelo LA089 ha cambiado de estado: PROGRAMADO → CANCELADO',
   'CANCELACION', NOW() - INTERVAL '15 minutes', (SELECT id FROM vuelos WHERE numero_vuelo = 'LA089')),
  ('El vuelo AV205 ha cambiado de estado: PROGRAMADO → EMBARCANDO',
   'EMBARQUE',   NOW() - INTERVAL '5 minutes',  (SELECT id FROM vuelos WHERE numero_vuelo = 'AV205'))
ON CONFLICT DO NOTHING;
