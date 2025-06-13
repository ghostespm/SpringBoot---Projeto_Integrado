package espm.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    public void acquire(String sensor) {
        List<Map<String, Object>> sensorData = fetchSensorData(sensor);
        if (sensorData != null) {
            for (Map<String, Object> data : sensorData) {
                DataModel model = new DataModel();
                Object registroId = data.get("registroId");
                if (registroId == null) {
                    registroId = data.get("registro_id");
                }
                if (registroId != null) {
                    model.setRegistroId(Long.valueOf(registroId.toString()));
                }
                model.setSensor(sensor);
                model.setData(LocalDateTime.parse(data.get("data").toString()));
                if (data.containsKey("idSensor")) {
                    model.setIdSensor(Short.valueOf(data.get("idSensor").toString()));
                }
                if (data.containsKey("delta")) {
                    model.setDelta(Integer.valueOf(data.get("delta").toString()));
                }
                if (data.containsKey("bateria")) {
                    model.setBateria(Short.valueOf(data.get("bateria").toString()));
                }
                if (data.containsKey("entrada")) {
                    model.setEntrada(Integer.valueOf(data.get("entrada").toString()));
                }
                if (data.containsKey("saida")) {
                    model.setSaida(Integer.valueOf(data.get("saida").toString()));
                }
                dataRepository.save(model);
            }
        }
    }

    public List<Map<String, Object>> export(String type) {
        List<DataModel> models = dataRepository.findBySensor(type);
        return models.stream().map(model -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", model.getId());
            map.put("registroId", model.getRegistroId());
            map.put("sensor", model.getSensor());
            map.put("data", model.getData());
            map.put("idSensor", model.getIdSensor());
            map.put("delta", model.getDelta());
            map.put("bateria", model.getBateria());
            map.put("entrada", model.getEntrada());
            map.put("saida", model.getSaida());
            return map;
        }).toList();
    }

    private List<Map<String, Object>> fetchSensorData(String sensor) {
        // Simulação de dados para exemplo
        return List.of(
            Map.of(
                "registroId", 1L,
                "sensor", sensor,
                "data", LocalDateTime.now().toString(),
                "idSensor", (short)1,
                "delta", 10,
                "bateria", (short)90,
                "entrada", 5,
                "saida", 3
            )
        );
    }
}