package gateway.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class LoggingFilter  implements GlobalFilter, Ordered {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethodValue();
        String timestamp = LocalDateTime.now().format(formatter);
        String fullUrl = exchange.getRequest().getURI().toString();



        log.info("Timestamp: {}", timestamp);
        log.info("Method: {}", method);
        log.info("Path: {}", path);
        log.info("Full URL: {}", fullUrl);



        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    int statusCode = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().value()
                            : 0;

                    log.info("Timestamp: {}", LocalDateTime.now().format(formatter));
                    log.info("Status Code: {}", statusCode);
                    log.info("Path: {}", path);

                }));
    }

    @Override
    public int getOrder() {

        return Ordered.HIGHEST_PRECEDENCE; // -2147483648 chạy đầu tiên
    }


}
