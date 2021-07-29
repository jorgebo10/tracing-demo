package com.example.tracingdemo.services;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsService {
    private final Tracer tracer;

    @Autowired
    public LogisticsService(Tracer tracer) {
        this.tracer = tracer;
    }

    public void transport() {
        try (Scope ignored = tracer.buildSpan("transport").startActive(true)) {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
