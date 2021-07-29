package com.example.tracingdemo;

import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaegerConfig {

    @Bean
    public Tracer tracer() {
        SamplerConfiguration samplerConfig = SamplerConfiguration
                .fromEnv()
                .withType("const")
                .withParam(1);
        ReporterConfiguration reporterConfig = ReporterConfiguration
                .fromEnv()
                .withLogSpans(true);
        return new io.jaegertracing.Configuration("EShop")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig)
                .getTracer();
    }
}
