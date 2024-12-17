package com.chamcongtinhluong.account_service.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/performance")
    public Map<String, Object> getPerformanceMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        TimeUnit timeUnit = meterRegistry.get("http.server.requests").timer().baseTimeUnit();
        // Số lượng yêu cầu thành công (200 OK)
        double successCount = meterRegistry
                .get("http.server.requests")
                .tags("availableTags")
                .tags("status", "200")
                .counter()
                .count();

        // Số lượng yêu cầu lỗi phía client (4xx)
        double clientErrorCount = meterRegistry
                .get("http.server.requests")
                .tags("availableTags")
                .tags("status", "4xx")
                .counter()
                .count();

        // Số lượng yêu cầu lỗi phía server (5xx)
        double serverErrorCount = meterRegistry
                .get("http.server.requests")
                .tags("availableTags")
                .tags("status", "5xx")
                .counter()
                .count();

        // Thời gian xử lý yêu cầu (tính tổng thời gian)
        double totalTime = meterRegistry
                .get("http.server.requests")
                .timer().totalTime(timeUnit);


        // Số lượng yêu cầu (tổng số lượng)
        double totalRequests = successCount + clientErrorCount + serverErrorCount;

        // Thời gian xử lý lâu nhất
        double maxTime = meterRegistry
                .get("http.server.requests")
                .timer()
                .max(timeUnit);

        // Tính tỷ lệ thành công và thất bại
        double successRate = (successCount / totalRequests) * 100;
        double failureRate = ((clientErrorCount + serverErrorCount) / totalRequests) * 100;

        // Tính thời gian trung bình
        double avgTime = totalTime / totalRequests;

        // Tạo Map trả về
        metrics.put("successRate", successRate);
        metrics.put("failureRate", failureRate);
        metrics.put("averageResponseTime", avgTime);
        metrics.put("maxResponseTime", maxTime);
        metrics.put("totalRequests", totalRequests);

        return metrics;
    }


}
