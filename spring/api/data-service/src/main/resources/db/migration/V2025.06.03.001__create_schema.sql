CREATE SCHEMA IF NOT EXISTS data;

CREATE TABLE IF NOT EXISTS data (
  id BIGSERIAL PRIMARY KEY,
  registro_id BIGINT NOT NULL, -- ID original do registro vindo do sensor
  sensor TEXT NOT NULL,        -- Tipo ou nome do sensor
  data TIMESTAMP NOT NULL,     -- Data e hora do registro
  id_sensor SMALLINT NOT NULL, -- ID do sensor
  delta INT NOT NULL,          -- Delta (diferença ou variação)
  bateria SMALLINT NOT NULL,   -- Nível de bateria
  entrada INT NOT NULL,        -- Período de entrada
  saida INT NOT NULL           -- Período de saída
);

-- Índices para otimizar consultas
CREATE INDEX idx_data_data_id_sensor ON data (data, id_sensor);
CREATE INDEX idx_data_id_sensor ON data (id_sensor);