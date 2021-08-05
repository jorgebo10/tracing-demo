package com.example.tracingdemo;

import io.opentracing.Scope;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import io.opentracing.propagation.TextMapInjectAdapter;

import java.util.Map;

public final class Helper {
    public static Scope getScopeFromHeaders(Tracer tracer, Map<String, String> headers, String spanName) {
        SpanContext spanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers));
        Tracer.SpanBuilder spanBuilder = tracer.buildSpan(spanName);
        return spanCtx != null ? spanBuilder.asChildOf(spanCtx).startActive(true) :
                spanBuilder.startActive(true);
    }

    public static void injectHeaders(Tracer tracer, SpanContext context, Map<String, String> headers) {
        tracer.inject(context, Format.Builtin.HTTP_HEADERS, new TextMapInjectAdapter(headers));
    }
}
