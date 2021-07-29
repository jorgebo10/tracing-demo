package com.example.tracingdemo.services;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final Tracer tracer;

    @Autowired
    public InventoryService(Tracer tracer) {
        this.tracer = tracer;
    }

    public void createOrder() {
        try (Scope ignored = tracer.buildSpan("createOrder").startActive(true)) {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
