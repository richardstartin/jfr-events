package io.github.richardstartin.jfrevents;

import jdk.jfr.Recording;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 5, time = 1)
@Warmup(iterations = 5, time = 1)
@Fork(value = 3, jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+AlwaysPreTouch", "-XX:+UseSerialGC"})
@State(Scope.Benchmark)
public class CommitEventBenchmark {

  public enum IdGenerator {
    UNIFORM_RANDOM {
      @Override
      void generate(long seed, long[] ids) {
        SplittableRandom random = new SplittableRandom(seed);
        Arrays.setAll(ids, i -> random.nextLong());
      }
    },
    SEQUENTIAL {
      @Override
      void generate(long seed, long[] ids) {
        for (int i = 0; i < ids.length; ++i) {
          ids[i] = seed + i;
        }
      }
    };
    abstract void generate(long seed, long[] ids);
  }

  @Param
  IdGenerator idGenerator;

  @Param("5000")
  int n;

  @Param("0")
  long seed;

  private long[] ids;

  Recording recording;

  @Setup(Level.Trial)
  public void createData() {
    ids = new long[n];
    idGenerator.generate(seed, ids);
  }

  @Setup(Level.Iteration)
  public void startRecording() {
    recording = new Recording();
    recording.start();
    recording.setMaxSize(Integer.MAX_VALUE);
  }

  @TearDown(Level.Iteration)
  public void stopRecording() {
    recording.stop();
    recording.close();
  }

  @Benchmark
  public void emptyEvent() {
    for (int i = 0; i < n; i++) {
      new EmptyEvent().commit();
    }
  }

  @Benchmark
  public void oneCorrelationIdEvent() {
    for (int i = 0; i < n; i++) {
      new OneCorrelationIdEvent(ids[i]).commit();
    }
  }

  @Benchmark
  public void twoCorrelationIdsEvent() {
    for (int i = 0; i < n; i++) {
      new TwoCorrelationIdsEvent(ids[i], ids[i]).commit();
    }
  }

  @Benchmark
  public void twoDoubleCorrelationIdsEvent() {
    for (int i = 0; i < n; i++) {
      new TwoDoubleCorrelationIdsEvent(ids[i], ids[i]).commit();
    }
  }

  @Benchmark
  public void uuidCorrelationIdEvent(Blackhole bh) {
    if (idGenerator == IdGenerator.SEQUENTIAL) {
      for (int i = 0; i < n; i++) {
        bh.consume(ThreadLocalRandom.current().nextLong());
        new UUIDCorrelationIdEvent(i).commit();
      }
    } else {
      for (int i = 0; i < n; i++) {
        new UUIDCorrelationIdEvent(ThreadLocalRandom.current().nextLong()).commit();
      }
    }
  }
}
