package com.example.tracingdemo.delivery;


import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeliveryController {
    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @Autowired
    public DeliveryController(Tracer tracer, RestTemplateBuilder restTemplateBuilder) {
        this.tracer = tracer;
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("/arrangeDelivery")
    public void arrangeDelivery() {
        try (Scope ignored = tracer.buildSpan("arrangeDelivery").startActive(true)) {
            restTemplate.getForEntity("http://logistics:8080/transport", Void.class);
        }
    }
}
