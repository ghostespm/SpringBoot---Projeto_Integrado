package espm.auth;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    private final String ACCOUNT_SERVICE_URL = "http://account:8081/accounts";

    // ...existing code...
public String login(String email, String password) {
    logger.debug("login: email: [{}] password: [{}]", email, password);

    CredentialIn credential = new CredentialIn(email, password);

    try {
        ResponseEntity<AccountOut> response = restTemplate.postForEntity(
            ACCOUNT_SERVICE_URL + "/login",
            credential,
            AccountOut.class
        );
        AccountOut accountOut = response.getBody();
        if (accountOut == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        logger.debug("account id: " + accountOut.id());
        return createToken(accountOut);
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login inválido");
    }
}

public void register(String name, String email, String password) {
    logger.debug("register: " + name + ":" + password);

    RegisterIn registerIn = new RegisterIn(name, email, password);

    try {
        restTemplate.postForEntity(
            ACCOUNT_SERVICE_URL,
            registerIn,
            Void.class
        );
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao registrar");
    }
}
// ...existing code...

    public AccountOut whoiam(String idAccount) {
        try {
            ResponseEntity<AccountOut> response = restTemplate.getForEntity(
                ACCOUNT_SERVICE_URL + "/" + idAccount,
                AccountOut.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada");
        }
    }

    private String createToken(AccountOut account) {
        Date notBefore = new Date();
        Date expiration = new Date(
            notBefore.getTime() + 1000L * 60 * 60 * 24 * 30);
        return jwtService.create(
            account.id(),
            notBefore,
            expiration
        );
    }

    public Map<String, String> solve(String token) {
        final String id = jwtService.getId(token);
        return Map.of("idAccount", id);
    }
}