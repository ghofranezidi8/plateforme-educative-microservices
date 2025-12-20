package ma.education.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route personnalisée pour la santé de la Gateway
                .route("gateway-health", r -> r
                        .path("/gateway/health")
                        .filters(f -> f.setPath("/actuator/health"))
                        .uri("lb://gateway-service")
                )
                .build();
    }
}