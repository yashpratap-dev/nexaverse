package com.nexaverse.nexaverse.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> windowStart = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 100;
    private static final long WINDOW_MS = 60_000;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        windowStart.computeIfAbsent(ip, k -> new AtomicLong(now));
        requestCounts.computeIfAbsent(ip, k -> new AtomicInteger(0));

        long windowTime = windowStart.get(ip).get();

        // Reset window if expired
        if (now - windowTime > WINDOW_MS) {
            windowStart.get(ip).set(now);
            requestCounts.get(ip).set(0);
        }

        int count = requestCounts.get(ip).incrementAndGet();

        if (count > MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Too many requests! Wait 1 minute.\",\"data\":null}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}