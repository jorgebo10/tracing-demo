package com.example.tracingdemo.inventory;

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
public class InventoryController {
    private final Tracer tracer;
    private final Log log = LogFactory.getLog(InventoryController.class);

    @RequestMapping("/createOrder")
    public void createOrder(@RequestHeader Map<String, String> headers) {
        try (Scope scope = getScopeFromHeaders(tracer, headers, "createOrder")) {
            log.info(String.format("User: %s", tracer.activeSpan().getBaggageItem("user")));
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
