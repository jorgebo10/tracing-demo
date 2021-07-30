package com.example.tracingdemo.billing;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {
    private final Tracer tracer;

    @Autowired
    public BillingController(Tracer tracer) {
        this.tracer = tracer;
    }

    @RequestMapping("/payment")
    public void payment() {
        try (Scope scope = tracer.buildSpan("payment").startActive(true)) {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
