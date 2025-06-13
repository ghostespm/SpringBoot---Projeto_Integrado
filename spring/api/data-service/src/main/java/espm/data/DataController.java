package espm.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/import")
    public String importData(@RequestParam String sensor) {
        dataService.acquire(sensor);
        return "Dados importados com sucesso para o sensor: " + sensor;
    }

    @GetMapping("/export/{type}")
    public List<Map<String, Object>> exportData(@PathVariable String type) {
        return dataService.export(type);
    }
}