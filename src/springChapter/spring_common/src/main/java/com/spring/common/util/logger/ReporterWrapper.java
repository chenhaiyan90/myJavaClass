package com.spring.common.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin.reporter.Reporter;

/**
 * 打印日志
 * @param <Span>
 * @author 00013519
 */
public class ReporterWrapper<Span> implements Reporter<Span> {
    private static final Logger zipkinLogger = LoggerFactory.getLogger("zipkin");

    @Override
    public void report(Span span) {
        if (zipkinLogger.isInfoEnabled()) {
            zipkinLogger.info(span.toString());
        }
    }
}