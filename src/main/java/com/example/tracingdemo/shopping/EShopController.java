package com.example.tracingdemo.shopping;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
            restTemplate.getForEntity("http://inventory:8080/createOrder", Void.class);
            restTemplate.getForEntity("http://billing:8080/payment", Void.class);
            restTemplate.getForEntity("http://delivery:8080/arrangeDelivery", Void.class);
            return "You have successfully checked out your shopping cart";
        }
    }
}
