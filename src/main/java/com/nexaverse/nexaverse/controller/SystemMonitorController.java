package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.service.SystemMonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/monitor")
public class SystemMonitorController {

    private final SystemMonitorService monitorService;

    public SystemMonitorController(SystemMonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status",  "UP",
                "app",     "NexaVerse",
                "version", "1.0.0"
        );
    }

    @GetMapping("/jvm")
    public Map<String, Object> jvmInfo() {
        return monitorService.getJvmInfo();
    }

    @GetMapping("/memory")
    public Map<String, Object> memoryInfo() {
        return monitorService.getMemoryInfo();
    }

    @GetMapping("/threads")
    public Map<String, Object> threadInfo() {
        return monitorService.getThreadInfo();
    }

    @GetMapping("/system")
    public Map<String, Object> fullSystemInfo() {
        return monitorService.getFullSystemInfo();
    }
}