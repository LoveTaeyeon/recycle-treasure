package com.recycle.server.util.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

public interface MainMetrics {

    String QPS_METRICS_KEY = "api.server.qps";
    String LATENCY_METRICS_KEY = "api.server.latency";

    Counter INVALID_SESSION = Metrics.counter("invalid.session");

}
