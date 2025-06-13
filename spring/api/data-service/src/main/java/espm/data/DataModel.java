package espm.data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "data", schema = "data")
public class DataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registro_id", nullable = false)
    private Long registroId;

    @Column(name = "sensor", nullable = false)
    private String sensor;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "id_sensor")
    private Short idSensor;

    @Column(name = "delta")
    private Integer delta;

    @Column(name = "bateria")
    private Short bateria;

    @Column(name = "entrada")
    private Integer entrada;

    @Column(name = "saida")
    private Integer saida;

    public DataModel() {}

    // Getters e Setters
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