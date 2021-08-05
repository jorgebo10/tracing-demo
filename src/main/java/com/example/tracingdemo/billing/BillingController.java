package com.example.tracingdemo.billing;

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
public class BillingController {
    private final Tracer tracer;
    private final Log log = LogFactory.getLog(BillingController.class);

    @RequestMapping("/payment")
    public void payment(@RequestHeader Map<String, String> headers) {
        try (Scope scope = getScopeFromHeaders(tracer, headers, "payment")) {
            log.info(String.format("User: %s", tracer.activeSpan().getBaggageItem("user")));
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
