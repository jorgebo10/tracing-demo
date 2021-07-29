package com.example.tracingdemo.services;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingService {
    private final Tracer tracer;

    @Autowired
    public BillingService(Tracer tracer) {
        this.tracer = tracer;
    }

    public void payment() {
        try (Scope scope = tracer.buildSpan("payment").startActive(true)) {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
