package gateway.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;

@Slf4j
@Component
public class AddResponseHeaderFilter  implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    exchange.getResponse().getHeaders().add(HEADER_NAME, HEADER_VALUE);
                    log.info("header: {} = {}", HEADER_NAME, HEADER_VALUE);
                }));
    }

    @Override
    public int getOrder() {

        return Ordered.HIGHEST_PRECEDENCE + 1;
    }



}
