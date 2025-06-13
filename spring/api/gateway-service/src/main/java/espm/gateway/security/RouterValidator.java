package espm.gateway.security;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

    private List<String> openApiEndpoints = List.of(
        // authorization and authentication
        "POST /auth/register",
        "POST /auth/login"
    );

    public Predicate<ServerHttpRequest> isSecured =
        request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> {
                String[] parts = uri.split(" ");
                final String method = parts[0];
                final String path = parts[1];
                return ("ANY".equalsIgnoreCase(method) || request.getMethod().toString().equalsIgnoreCase(method))
                    && request.getURI().getPath().startsWith(path);
            });
}