package com.spring.common.util.logger;

import com.github.kristofa.brave.Brave;
import org.springframework.context.annotation.Bean;
import zipkin.Span;
import zipkin.reporter.Reporter;

/**
 * 初始化Brave
 * @author 00013519
 */
public class ZipKinConfiguration {

    public ZipKinConfiguration(String serviceName) {
        this.serviceName = serviceName;
        this.zipkinHost = null;
    }

    public ZipKinConfiguration(String serviceName, String zipkinHost) {
        this.serviceName = serviceName;
        this.zipkinHost = zipkinHost;
    }

    @Bean
    public Reporter<Span> reporter() {
        return new ReporterWrapper();
    }

    @Bean
    public Brave brave() {
        return new Brave.Builder(serviceName).reporter(reporter()).build();
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getZipkinHost() {
        return zipkinHost;
    }

    private final String serviceName;
    private final String zipkinHost;

}