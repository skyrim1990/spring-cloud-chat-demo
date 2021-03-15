package com.tao.gatewaypoint.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.OrderComparator.OrderSourceProvider;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustGlobalFilter implements GlobalFilter, Ordered {

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    log.info("[pre]-Enter Filter");

    ServerHttpRequest req = exchange.getRequest();
    req.getQueryParams().forEach((k, v)->{
      log.info("Param: " + k + " : " + v);
    });

    log.info("URI: " + req.getURI());
    log.info("Path: " + req.getURI().getPath());

    return chain.filter(exchange)
                .then(Mono.fromRunnable(()->{
                  log.info("[post]-Return Result");
                }));
  }
  
}
