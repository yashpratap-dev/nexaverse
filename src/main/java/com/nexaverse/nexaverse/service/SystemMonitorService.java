package com.nexaverse.nexaverse.service;

import org.springframework.stereotype.Service;
import java.lang.management.*;
import java.util.*;

@Service
public class SystemMonitorService {

    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    private final OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    // JVM uptime
    public long getUptimeSeconds() {
        return runtimeMXBean.getUptime() / 1000;
    }

    // CPU load
    public double getCpuLoad() {
        return Math.round(osMXBean.getSystemLoadAverage() * 100.0) / 100.0;
    }

    // Memory info
    public Map<String, Object> getMemoryInfo() {
        MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
        Map<String, Object> memory = new HashMap<>();
        memory.put("heap_used_mb",  heap.getUsed() / 1024 / 1024);
        memory.put("heap_max_mb",   heap.getMax() / 1024 / 1024);
        memory.put("heap_free_mb",  (heap.getMax() - heap.getUsed()) / 1024 / 1024);
        return memory;
    }

    // Thread info
    public Map<String, Object> getThreadInfo() {
        Map<String, Object> threads = new HashMap<>();
        threads.put("active_threads",  threadMXBean.getThreadCount());
        threads.put("peak_threads",    threadMXBean.getPeakThreadCount());
        threads.put("daemon_threads",  threadMXBean.getDaemonThreadCount());
        return threads;
    }

    // JVM info
    public Map<String, Object> getJvmInfo() {
        Map<String, Object> jvm = new HashMap<>();
        jvm.put("java_version",  System.getProperty("java.version"));
        jvm.put("jvm_name",      runtimeMXBean.getVmName());
        jvm.put("pid",           ProcessHandle.current().pid());
        jvm.put("uptime_sec",    getUptimeSeconds());
        return jvm;
    }

    // Sab ek saath
    public Map<String, Object> getFullSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("jvm",     getJvmInfo());
        info.put("memory",  getMemoryInfo());
        info.put("threads", getThreadInfo());
        info.put("cpu",     getCpuLoad());
        return info;
    }
}