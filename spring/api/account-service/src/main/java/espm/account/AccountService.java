package espm.account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account create(Account a) {
        constraints(a);
        // Ajuste conforme o modelo da sua classe Account: use setters ou campos públicos
         a.setHashPassword(hash(a.getHashPassword())); // corrigido
        a.setCreatedAt(new Date());
        AccountModel model = new AccountModel(a);
        return accountRepository.save(model).to();
    }

    public Account update(Account a) {
        String id = a.getId().trim();
        AccountModel model = accountRepository.findById(id).orElse(null);
        if (model == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id);
        }
        Account found = model.to();
        constraints(a);
        if (!found.getEmail().equals(a.getEmail())) {
            List<AccountModel> listByEmail = accountRepository.findByEmail(a.getEmail());
            if (!listByEmail.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email has already been registered");
            }
        }
        found.setEmail(a.getEmail());
        found.setName(a.getName());
        found.setHashPassword(hash(a.getHashPassword())); // corrigido
        return accountRepository.save(new AccountModel(found)).to();
    }

    public List<Account> findAll() {
        return accountRepository.findAll().stream().map(AccountModel::to).toList();
    }

    public Account findById(String id) {
        AccountModel model = accountRepository.findById(id).orElse(null);
        return model == null ? null : model.to();
    }

    public void constraints(Account a) {
        if (a.getEmail() == null || a.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is required");
        }
        if (a.getName() == null || a.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
    }

    private String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public Account login(String email, String password) {
        String hash = hash(password);
        AccountModel model = accountRepository.findByEmailAndHashPassword(email, hash);
        if (model == null) {
            return null;
        }
        return model.to();
    }
}