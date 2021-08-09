package com.example.tracingdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EShopController {
    private final RestTemplate restTemplate;

    @Autowired
    public EShopController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/checkout")
    public String checkout(@RequestHeader HttpHeaders httpHeaders) {
        HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);
        restTemplate.postForLocation("http://inventory.tracing-demo:8080/createOrder", entity, Void.class);
        restTemplate.postForLocation("http://billing.tracing-demo:8080/payment", entity, Void.class);
        restTemplate.postForLocation("http://delivery.tracing-demo:8080/arrangeDelivery", entity, Void.class);
        return "You have successfully checked out your shopping cart";
    }
}
