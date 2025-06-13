package espm.data;

import java.time.LocalDateTime;

public class Data {
    private Long id;
    private Long registroId;
    private String sensor;
    private LocalDateTime data;
    private Short idSensor;
    private Integer delta;
    private Short bateria;
    private Integer entrada;
    private Integer saida;

    public Data() {}

    public Data(Long id, Long registroId, String sensor, LocalDateTime data, Short idSensor, Integer delta, Short bateria, Integer entrada, Integer saida) {
        this.id = id;
        this.registroId = registroId;
        this.sensor = sensor;
        this.data = data;
        this.idSensor = idSensor;
        this.delta = delta;
        this.bateria = bateria;
        this.entrada = entrada;
        this.saida = saida;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRegistroId() { return registroId; }
    public void setRegistroId(Long registroId) { this.registroId = registroId; }

    public String getSensor() { return sensor; }
    public void setSensor(String sensor) { this.sensor = sensor; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public Short getIdSensor() { return idSensor; }
    public void setIdSensor(Short idSensor) { this.idSensor = idSensor; }

    public Integer getDelta() { return delta; }
    public void setDelta(Integer delta) { this.delta = delta; }

    public Short getBateria() { return bateria; }
    public void setBateria(Short bateria) { this.bateria = bateria; }

    public Integer getEntrada() { return entrada; }
    public void setEntrada(Integer entrada) { this.entrada = entrada; }

    public Integer getSaida() { return saida; }
    public void setSaida(Integer saida) { this.saida = saida; }
}