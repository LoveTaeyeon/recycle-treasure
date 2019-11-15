package com.recycle.server;

import com.recycle.server.util.metrics.AdaptiveMetrics;
import com.recycle.server.util.metrics.MainMetrics;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ApiFilter", urlPatterns = "/*")
@Slf4j
public class ApiFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        long begin = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        String uri = request.getRequestURI();
        long responseTime = System.currentTimeMillis() - begin;
        log.info("[{} {}] duration: {}ms", request.getMethod(), request.getRequestURI(), responseTime);
        // add metrics
        AdaptiveMetrics.increase(MainMetrics.QPS_METRICS_KEY, "url", uri);
        AdaptiveMetrics.addLatency(MainMetrics.LATENCY_METRICS_KEY, "url", uri, responseTime);
    }

}
