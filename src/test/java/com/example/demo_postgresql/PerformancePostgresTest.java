package com.example.demo_postgresql;

import com.example.demo_postgresql.model.Config;
import com.example.demo_postgresql.model.DocumentEntity;
import com.example.demo_postgresql.model.NestedArray;
import com.example.demo_postgresql.repository.DocumentEntityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestcontainersConfiguration.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PerformancePostgresTest {

    private final static Integer MEASUREMENT_ITERATIONS = 5;
    private final static Integer WARMUP_ITERATIONS = 3;
    public static DocumentEntityRepository repository;
    @Param({"10", "100", "1000", "10000", "29000"})
    int y;

    @Setup(Level.Trial)
    public void setup() {
        repository.deleteAll();
        for(int i=0; i < y; i++) {
            var entity = new DocumentEntity();
            var config = new Config();
            config.setSize(RandomUtils.nextDouble());
            config.setSet(RandomStringUtils.randomAlphabetic(10));
            config.setConfigId(RandomStringUtils.randomAlphabetic(10));
            config.setMultiply(RandomUtils.nextDouble());
            config.setDocumentEntity(entity);

            NestedArray nestedArray = new NestedArray();
            nestedArray.setValue(RandomStringUtils.randomAlphabetic(10));
            nestedArray.setDocumentEntity(entity);

            entity.setName(RandomStringUtils.randomAlphabetic(10));
            entity.setConfig(config);

            var arr = new ArrayList<NestedArray>(){{
                add(nestedArray);
            }};

            entity.setNestedArr(arr);
            repository.save(entity);
        }
    }

    @BeforeAll
    static void beforeAll(@Autowired DocumentEntityRepository repository) {
        PerformancePostgresTest.repository = repository;
    }
    @Benchmark
    public void performTest(Blackhole blackhole) {
        var all = PerformancePostgresTest.repository.findAll();
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
        blackhole.consume(all);
    }

    @Test
    public void nanosPark() {
        System.out.println(TimeUnit.SECONDS.toNanos(2));
    }

    @Test
    public void executeJmhRunner() throws RunnerException {
        Options opt = new OptionsBuilder()
                // set the class name regex for benchmarks to search for to the current class
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(WARMUP_ITERATIONS)
                .measurementIterations(MEASUREMENT_ITERATIONS)
                .timeUnit(TimeUnit.MILLISECONDS)
                // do not use forking or the benchmark methods will not see references stored within its class
                .forks(0)
                // do not use multiple threads
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result("src/main/resources/result.json") // set this to a valid filename if you want reports
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
    }

}
