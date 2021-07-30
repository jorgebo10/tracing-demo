package com.example.tracingdemo.inventory;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    private final Tracer tracer;

    @Autowired
    public InventoryController(Tracer tracer) {
        this.tracer = tracer;
    }

    @RequestMapping("/createOrder")
    public void createOrder() {
        try (Scope ignored = tracer.buildSpan("createOrder").startActive(true)) {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
