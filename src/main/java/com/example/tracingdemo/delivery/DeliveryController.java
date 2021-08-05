package com.example.tracingdemo.delivery;


import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.tracingdemo.Helper.getScopeFromHeaders;
import static com.example.tracingdemo.Helper.injectHeaders;

@RestController
public class DeliveryController {
    private final RestTemplate restTemplate;
    private final Tracer tracer;
    private final Log log = LogFactory.getLog(DeliveryController.class);

    @Autowired
    public DeliveryController(Tracer tracer, RestTemplateBuilder restTemplateBuilder) {
        this.tracer = tracer;
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("/arrangeDelivery")
    public void arrangeDelivery(@RequestHeader Map<String, String> headers) {
        try (Scope scope = getScopeFromHeaders(tracer, headers, "arrangeDelivery")) {
            log.info(String.format("User: %s", tracer.activeSpan().getBaggageItem("user")));
            Map<String, String> headers2 = new HashMap<>();
            injectHeaders(tracer, tracer.activeSpan().context(), headers2);
            final HttpHeaders httpHeaders = new HttpHeaders();
            headers2.forEach(httpHeaders::set);
            HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);
            restTemplate.exchange("http://logistics:8080/transport", HttpMethod.POST, entity, Void.class);
        }
    }

}
