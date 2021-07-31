package com.example.tracingdemo.billing;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.tracingdemo.Helper.getScopeFromHeaders;

@RestController
public class BillingController {
    private final Tracer tracer;

    @Autowired
    public BillingController(Tracer tracer) {
        this.tracer = tracer;
    }

    @RequestMapping("/payment")
    public void payment(@RequestHeader Map<String, String> headers) {
        try (Scope scope = getScopeFromHeaders(tracer, headers, "payment")) {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
