package com.example.tracingdemo.services;


import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    private final LogisticsService logisticsService;
    private final Tracer tracer;

    @Autowired
    public DeliveryService(LogisticsService logisticsService, Tracer tracer) {
        this.logisticsService = logisticsService;
        this.tracer = tracer;
    }

    public void arrangeDelivery(Span parentSpan) {
        Span span = tracer.buildSpan("arrangeDelivery").asChildOf(parentSpan).start();
        logisticsService.transport(span);
        span.finish();
    }
}
