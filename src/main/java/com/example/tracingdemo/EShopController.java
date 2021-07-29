package com.example.tracingdemo;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EShopController {
    private final Tracer tracer;

    @Autowired
    public EShopController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/checkout")
    public String checkout() {
        Span span = tracer.buildSpan("checkout").start();

        String msg = "You have successfully checked out your shopping cart";

        span.finish();

        return msg;
    }
}
