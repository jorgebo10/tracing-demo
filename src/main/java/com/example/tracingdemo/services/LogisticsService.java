package com.example.tracingdemo.services;

import io.opentracing.Span;
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

    public void transport(Span parentSpan) {
        Span span = tracer.buildSpan("transport").asChildOf(parentSpan).start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }
}
