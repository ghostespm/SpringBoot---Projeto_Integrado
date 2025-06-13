package espm.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountOut> findAll() {
        return accountService.findAll().stream().map(AccountParser::to).toList();
    }

    @GetMapping("/{id}")
    public AccountOut findById(@PathVariable String id) {
        Account a = accountService.findById(id);
        if (a == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id);
        }
        return AccountParser.to(a);
    }

    @PostMapping
    public AccountOut create(@RequestBody AccountIn in) {
        Account a = AccountParser.to(in);
        Account saved = accountService.create(a);
        return AccountParser.to(saved);
    }

    @PutMapping("/{id}")
    public AccountOut update(@PathVariable String id, @RequestBody AccountIn in) {
        Account a = AccountParser.to(in);
        a.setId(id);
        Account saved = accountService.update(a);
        return AccountParser.to(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "delete not implemented");
    }

    // --- Endpoint de login ---
    @PostMapping("/login")
    public AccountOut login(@RequestBody CredentialIn credential) {
        Account account = accountService.login(credential.getEmail(), credential.getPassword());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login inválido");
        }
        return AccountParser.to(account);
    }
}