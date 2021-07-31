package com.example.tracingdemo.logistics;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.tracingdemo.Helper.getScopeFromHeaders;

@RestController
public class LogisticsController {
    private final Tracer tracer;

    @Autowired
    public LogisticsController(Tracer tracer) {
        this.tracer = tracer;
    }

    @RequestMapping("/transport")
    public void transport(@RequestHeader Map<String, String> headers) {
        try (Scope scope = getScopeFromHeaders(tracer, headers, "transport")) {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
