package org.roaringbitmap.aggregation.andnot.worstcase;

import org.openjdk.jmh.annotations.*;
import org.roaringbitmap.RoaringBitmap;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class RoaringBitmapBenchmark {

    private RoaringBitmap bitmap1;
    private RoaringBitmap bitmap2;

    @Setup
    public void setup() {
        bitmap1 = new RoaringBitmap();
        bitmap2 = new RoaringBitmap();
        int k = 1 << 16;
        for(int i = 0; i < 10000; ++i) {
            bitmap1.add(2 * i * k);
            bitmap2.add(2 * i * k + 1);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public RoaringBitmap andNot() {
        return RoaringBitmap.andNot(bitmap1, bitmap2);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public RoaringBitmap inplace_andNot() {
        RoaringBitmap b1 = bitmap1.clone();
        b1.andNot(bitmap2);
        return b1;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public RoaringBitmap justclone() {
      return bitmap1.clone();
    }

}
