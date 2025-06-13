package espm.data;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface DataRepository extends CrudRepository<DataModel, Long> {
    List<DataModel> findBySensor(String sensor);
}