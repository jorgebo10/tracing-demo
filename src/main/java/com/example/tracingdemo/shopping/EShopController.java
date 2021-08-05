package com.example.tracingdemo.shopping;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.tracingdemo.Helper.injectHeaders;

@RestController
@RequiredArgsConstructor
public class EShopController {
    private final Tracer tracer;
    private final RestTemplate restTemplate;

    @GetMapping("/checkout")
    public String checkout(@RequestHeader("user") String user) {
        try (Scope ignored = tracer.buildSpan("checkout").startActive(true)) {
            tracer.activeSpan().setBaggageItem("user", user);
            Map<String, String> headersMap = new HashMap<>();
            injectHeaders(tracer, tracer.activeSpan().context(), headersMap);
            final HttpHeaders httpHeaders = new HttpHeaders();
            headersMap.forEach(httpHeaders::set);
            HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);
            restTemplate.exchange("http://inventory:8080/createOrder", HttpMethod.POST, entity, Void.class);
            restTemplate.exchange("http://billing:8080/payment", HttpMethod.POST, entity, Void.class);
            restTemplate.exchange("http://delivery:8080/arrangeDelivery", HttpMethod.POST, entity, Void.class);
            return "You have successfully checked out your shopping cart";
        }
    }
}
