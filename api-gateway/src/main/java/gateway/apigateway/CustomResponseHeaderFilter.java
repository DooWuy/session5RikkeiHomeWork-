package gateway.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomResponseHeaderFilter implements GlobalFilter, Ordered {

    private static final String HEADER_NAME = "X-System-Name";
    private static final String HEADER_VALUE = "Api-Gateway-System";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        log.info("Incoming request to: {}", path);


        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    exchange.getResponse().getHeaders().add(HEADER_NAME, HEADER_VALUE);


                    exchange.getResponse().getHeaders().add("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()));
                    exchange.getResponse().getHeaders().add("X-Gateway-Version", "1.0.0");

                    int statusCode = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().value()
                            : 0;

                    log.info("Response returned with status: {} for path: {}", statusCode, path);
                    log.info("Headers added: {} = {}", HEADER_NAME, HEADER_VALUE);
                }));
    }
    @Override
    public int getOrder() {

        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
