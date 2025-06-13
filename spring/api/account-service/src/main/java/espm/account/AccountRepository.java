package espm.account;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountModel, String> {
    List<AccountModel> findAll();
    List<AccountModel> findByEmail(String email);
    AccountModel findByEmailAndHashPassword(String email, String hash);
}