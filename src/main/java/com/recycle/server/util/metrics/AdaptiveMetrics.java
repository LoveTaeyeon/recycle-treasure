package com.recycle.server.util.metrics;

import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AdaptiveMetrics {

    private static ConcurrentHashMap<String, Counter> TAG_COUNTER_MAP = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, AtomicDouble> TAG_GAUGE_MAP = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, AtomicLong> GAUGE_MAP = new ConcurrentHashMap<>();

    public static void increase(String key, String tagName, String tag) {
        String mapKey = key + ":" + tag;
        Counter newCounter = Metrics.counter(key, tagName, tag);
        TAG_COUNTER_MAP.putIfAbsent(mapKey, newCounter);
        TAG_COUNTER_MAP.get(mapKey).increment();
    }

    private static ConcurrentHashMap<String, Pair<AtomicInteger, AtomicDouble>> AVG_LATENCY = new ConcurrentHashMap<>();

    public static void addLatency(String key, String tag, String tagName, long latency) {
        // calculate avg latency
        Pair result = AVG_LATENCY.computeIfPresent(tagName, (k, v) -> {
            int count = v.getLeft().get() > 0 ? v.getLeft().get() : 1;
            double avgLatency = v.getRight().get();
            return Pair.of(new AtomicInteger(count + 1), new AtomicDouble((count * avgLatency + latency) / (count + 1)));
        });
        if (result == null) {
            /*
             * There may be some data missing due to concurrency issues,
             * but I don’t think there is much impact, no need to lock
             */
            AVG_LATENCY.putIfAbsent(tagName, Pair.of(new AtomicInteger(1), new AtomicDouble(latency)));
        }
        // set value to Gauge
        AtomicDouble atomicDouble = Metrics.gauge(key, Arrays.asList(Tag.of(tag, tagName)), AVG_LATENCY.get(tagName).getRight());
        String avgKey = key + ":" + tagName;
        TAG_GAUGE_MAP.putIfAbsent(avgKey, atomicDouble);
        TAG_GAUGE_MAP.get(avgKey).set(AVG_LATENCY.get(tagName).getRight().doubleValue());

        // calculate timeout rate
        String timeKey = key + ".p";
        String level = getLatencyLevel(latency);
        Counter counter = Metrics.counter(timeKey, Arrays.asList(
                Tag.of(tag, tagName),
                Tag.of("time", level)
        ));
        String mapKey = timeKey + ":" + tagName + ":" + level;
        TAG_COUNTER_MAP.putIfAbsent(mapKey, counter);
        TAG_COUNTER_MAP.get(mapKey).increment();
    }

    private static String getLatencyLevel(long latency) {
        if (latency <= 10) {
            return "10";
        } else if (latency <= 20) {
            return "(10,20]";
        } else if (latency <= 50) {
            return "(20,50]";
        } else if (latency <= 100) {
            return "(50,100]";
        } else if (latency <= 200) {
            return "(100,200]";
        } else if (latency <= 1000) {
            return "(200,1000]";
        } else if (latency <= 5000) {
            return "(1000,5000]";
        } else if (latency <= 10000) {
            return "(5000,10000]";
        } else {
            return "10000";
        }
    }

    public static void monitorTimestamp(String key, String tagName, String tag, Long timestamp) {
        String mapKey = key + ":" + tag;
        AtomicLong maxTimestamp = Metrics.gauge(key, Arrays.asList(Tag.of(tagName, tag)), new AtomicLong(timestamp));
        GAUGE_MAP.putIfAbsent(mapKey, maxTimestamp);
        GAUGE_MAP.get(mapKey).set(timestamp);
    }

}
