package com.example.tracingdemo.shopping;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.tracingdemo.Helper.injectHeaders;

@RestController
public class EShopController {
    private final Tracer tracer;
    private final RestTemplate restTemplate;

    @Autowired
    public EShopController(Tracer tracer, RestTemplateBuilder restTemplateBuilder) {
        this.tracer = tracer;
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/checkout")
    public String checkout() {
        try (Scope ignored = tracer.buildSpan("checkout").startActive(true)) {
            Map<String, String> headers = new HashMap<>();
            injectHeaders(tracer, tracer.activeSpan().context(), headers);
            final HttpHeaders httpHeaders = new HttpHeaders();
            headers.forEach(httpHeaders::set);
            HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);
            restTemplate.exchange("http://inventory:8080/createOrder", HttpMethod.POST, entity, Void.class);
            restTemplate.exchange("http://billing:8080/payment", HttpMethod.POST, entity, Void.class);
            restTemplate.exchange("http://delivery:8080/arrangeDelivery", HttpMethod.POST, entity, Void.class);
            return "You have successfully checked out your shopping cart";
        }
    }
}
