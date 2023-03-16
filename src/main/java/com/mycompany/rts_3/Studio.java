package com.mycompany.rts_3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

// Studio class containing the benchmarking instructions
public class Studio {
 
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(iterations = 1, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @Warmup(iterations = 1)

    public void open_studio() {
        benchmark_var b = new benchmark_var();
        b.bench_time();
        }
        
    }

