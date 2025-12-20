package ma.education.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Ajouter un header personnalisé à la requête
        exchange.getRequest()
                .mutate()
                .header("X-Gateway-Request", "Processed-By-Gateway")
                .build();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Ajouter un header personnalisé à la réponse
            exchange.getResponse()
                    .getHeaders()
                    .add("X-Gateway-Response", "Success");
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}