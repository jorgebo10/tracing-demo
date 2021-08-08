package com.example.tracingdemo.delivery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class DeliveryController {
    private final RestTemplate restTemplate;

    @Autowired
    public DeliveryController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("/arrangeDelivery")
    public void arrangeDelivery(@RequestHeader Map<String, String> headers) {
        restTemplate.postForLocation("http://logistics.tracing-demo:8080/transport", Void.class);
    }
}
