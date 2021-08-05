package com.example.tracingdemo.logistics;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.tracingdemo.Helper.getScopeFromHeaders;

@RestController
@RequiredArgsConstructor
public class LogisticsController {
    private final Tracer tracer;
    private final Log log = LogFactory.getLog(LogisticsController.class);

    @RequestMapping("/transport")
    public void transport(@RequestHeader Map<String, String> headers) {
        try (Scope scope = getScopeFromHeaders(tracer, headers, "transport")) {
            log.info(String.format("User: %s", tracer.activeSpan().getBaggageItem("user")));
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
