package com.example.tracingdemo;

import com.example.tracingdemo.services.BillingService;
import com.example.tracingdemo.services.DeliveryService;
import com.example.tracingdemo.services.InventoryService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EShopController {
    private final Tracer tracer;
    private final InventoryService inventoryService;
    private final BillingService billingService;
    private final DeliveryService deliveryService;


    @Autowired
    public EShopController(Tracer tracer, InventoryService inventoryService,
                           BillingService billingService, DeliveryService deliveryService) {
        this.tracer = tracer;
        this.inventoryService = inventoryService;
        this.billingService = billingService;
        this.deliveryService = deliveryService;
    }

    @GetMapping("/checkout")
    public String checkout() {
        Span span = tracer.buildSpan("checkout").start();

        inventoryService.createOrder(span);
        billingService.payment(span);
        deliveryService.arrangeDelivery(span);
        String msg = "You have successfully checked out your shopping cart";

        span.finish();

        return msg;
    }
}
