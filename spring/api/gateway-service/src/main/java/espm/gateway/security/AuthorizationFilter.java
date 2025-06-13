package espm.gateway.security;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements GlobalFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_BEARER_TOKEN_HEADER = "Bearer";
    private static final String AUTH_SERVICE_TOKEN_SOLVE = "http://auth:8083/auth/solve";

    @Autowired
    private RouterValidator routerValidator;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.debug("Entrou no filtro de authorization");
        ServerHttpRequest request = exchange.getRequest();

        // verifica se a rota nao eh segura
        if (!routerValidator.isSecured.test(request)) {
            logger.debug("Rota nao eh segura");
            return chain.filter(exchange);
        }
        logger.debug("Rota segura");

        // verifica o token
        if (!isAuthMissing(request)) {
            logger.debug("Tem Authorization no Header");
            final String[] parts = this.getAuthHeader(request).split(" ");
            if (parts.length != 2 || !parts[0].equals(AUTHORIZATION_BEARER_TOKEN_HEADER)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
            }
            logger.debug("Resolver o token");
            // resolver o token
            return requestAuthTokenSolve(exchange, chain, parts[1]);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION_HEADER) ||
               request.getHeaders().getOrEmpty(AUTHORIZATION_HEADER).isEmpty();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION_HEADER).get(0);
    }

    // este metodo eh responsavel por enviar o token ao Auth Microservice
    // a fim de interpretar o token, a chamada eh feita via Rest.
    private Mono<Void> requestAuthTokenSolve(ServerWebExchange exchange, GatewayFilterChain chain, String jwt) {
        logger.debug("solving jwt: " + jwt);
        Map<String, String> body = Map.of("jwt", jwt);
        return webClient
            .defaultHeader(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
            )
            .build()
            .post()
            .uri(AUTH_SERVICE_TOKEN_SOLVE)
            .bodyValue(body)
            .retrieve()
            .toEntity(Map.class)
            .flatMap(response -> {
                if (response != null && response.hasBody() && response.getBody() != null) {
                    Map<String, String> out = response.getBody();
                    ServerWebExchange modifiedExchange = updateRequest(exchange, out);
                    return chain.filter(modifiedExchange);
                } else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
                }
            });
    }

    private ServerWebExchange updateRequest(ServerWebExchange exchange, Map<String, String> out) {
        logger.debug("original headers: " + exchange.getRequest().getHeaders().toString());
        ServerWebExchange modified = exchange.mutate()
            .request(
                exchange.getRequest()
                    .mutate()
                    .header("id-account", out.get("idAccount"))
                    .build()
            ).build();
        logger.debug("updated headers: " + modified.getRequest().getHeaders().toString());
        return modified;
    }

}