package extras.ch6;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.examples.chapter1.Track;

public class Examples_01_04 {
    private static void ex61() {
        int ls = SampleData.twoAlbums //
                .stream() //
                .flatMap(Album::getTracks) //
                .mapToInt(Track::getLength) //
                .sum();
        System.out.println("Serial summing of album track lengths: " + ls);
    }

    private static void ex62() {
        int lp = SampleData.twoAlbums //
                .parallelStream() // just change this
                .flatMap(Album::getTracks) //
                .mapToInt(Track::getLength) //
                .sum();
        System.out.println("Parallel summing of album track lengths: " + lp);
    }

    private static int COUNTER = 1_000_000;

    public static Map<Integer, Double> parallelDiceRolls() {
        double fraction = 1.0 / COUNTER;

        IntFunction<Integer> twoDiceThrows = i -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            return random.nextInt(1, 7) + random.nextInt(1, 7);
        };

        return IntStream.range(0, COUNTER) // generate a stream for [0 .. COUNTER)
                .parallel() // parallelize it
                .mapToObj(twoDiceThrows) // map each integer to a lambda on it
                .collect(Collectors.groupingBy( // collect the result of grouping
                        value -> value, // as key the dice throw result
                        Collectors.summingDouble(n -> fraction))); // as value the percentage
    }

    private static void ex63() {
        long start = System.nanoTime();
        System.out.println("Example 6-3. Parallel Monte Carlo simulation of dice rolling");
        for (int i = 0; i < 10; i++) {
            Map<Integer, Double> result = parallelDiceRolls();
            System.out.println(result);
        }
        System.out.printf("Delta: %d%n", (System.nanoTime() - start) / 1_000_000);
    }

    private static ManualDiceRolls rolls = new ManualDiceRolls(COUNTER);

    private static void ex64() {
        long start = System.nanoTime();
        System.out.println("Example 6-4. Simulating dice rolls by manually implementing threading");

        for (int i = 0; i < 10; i++) {
            Map<Integer, Double> result = rolls.go();
            System.out.println(result);
        }
        rolls.terminate();
        System.out.printf("Delta: %d%n", (System.nanoTime() - start) / 1_000_000);
    }

    public static void main(String[] args) {
        ex61();
        ex62();

        ex63();
        ex64();

        System.out.println(85);
    }
}

class ManualDiceRolls {
    private final double fraction;
    private final Map<Integer, Double> results;
    private static final int NR_THREADS = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor;
    private final int workPerThread;

    public ManualDiceRolls(int n) {
        fraction = 1.0 / n;
        results = new ConcurrentHashMap<>();
        workPerThread = n / NR_THREADS;
        executor = Executors.newFixedThreadPool(NR_THREADS);
    }

    public Map<Integer, Double> go() {
        results.clear();
        Future<?>[] futures = new Future[NR_THREADS];
        for (int i = 0; i < NR_THREADS; i++) {
            futures[i] = executor.submit(makeJob());
        }

        for (int i = 0; i < NR_THREADS; i++) {
            try {
                futures[i].get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public void terminate() {
        System.out.println("Shutting down executor");
        executor.shutdown();
    }

    private Runnable makeJob() {
        return () -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < workPerThread; i++) {
                int entry = random.nextInt(1, 7) + random.nextInt(1, 7);
                accumulateResult(entry);
            }
        };
    }

    private void accumulateResult(int entry) {
        results.compute(entry, (key, previous) -> previous == null ? fraction : previous + fraction);
    }
}