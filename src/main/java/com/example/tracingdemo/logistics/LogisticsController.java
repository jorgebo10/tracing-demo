package com.example.tracingdemo.logistics;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsController {
    private final Tracer tracer;

    @Autowired
    public LogisticsController(Tracer tracer) {
        this.tracer = tracer;
    }

    @RequestMapping("/transport")
    public void transport() {
        try (Scope ignored = tracer.buildSpan("transport").startActive(true)) {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
