package com.example.tracingdemo.services;

import io.opentracing.Span;
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

    public void payment(Span parentSpan) {
        Span span = tracer.buildSpan("payment").asChildOf(parentSpan).start();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }
}
