package extras.ch5;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Exercises {
    // using collect()
    private static Optional<String> ex2a1(List<String> names) {
        Comparator<String> comparer = Comparator.comparing(String::length);

        return names.stream() //
                .collect(Collectors.maxBy(comparer));
    }

    // using reduce()
    private static Optional<String> ex2a2(List<String> names) {
        Comparator<String> comparer = Comparator.comparing(String::length);
        return names.stream() //
                .reduce((lhs, rhs) -> {
                    return (comparer.compare(lhs, rhs) >= 0) ? lhs : rhs;
                });
    }

    // forEach
    private static Map<String, Long> ex2b1(List<String> names) {
        Map<String, Long> result = new HashMap<>();
        names.stream().forEach(key -> {
            if (result.containsKey(key))
                result.put(key, result.get(key) + 1);
            else
                result.put(key, 1L);
        });
        return result;
    }

    // grouping and collecting
    private static Map<String, Long> ex2b2(List<String> names) {
        return names.stream().collect( //
                Collectors.groupingBy(name -> name, Collectors.counting()));
    }

    public static void main(String[] args) {
        // Find the artist with the longest name
        List<String> artists = List.of("John Lennon", "Paul McCartney", "George Harrison", //
                "Ringo Starr", "Stuart Sutcliffe", "Pete Best");
        Optional<String> res2a1 = ex2a1(artists);
        System.out.println("Longest name (1): " + res2a1.orElse("N/A"));

        Optional<String> res2a2 = ex2a2(artists);
        System.out.println("Longest name (2): " + res2a2.orElse("N/A"));

        // count the number of times each word appears
        List<String> names = List.of("John", "Paul", "George", "John", "Paul", "John");
        Map<String, Long> res2b1 = ex2b1(names);
        System.out.println("Word count (1): " + res2b1);

        Map<String, Long> res2b2 = ex2b2(names);
        System.out.println("Word count (2): " + res2b2);
        
        // ex3 computeIfAbsent()
        ConcurrentFibonacci confib = new ConcurrentFibonacci();
        System.out.println(confib.calculate(10));
        
        Fibonacci fib = new Fibonacci();
        System.out.println(fib.calculate(10));
    }
}

class ConcurrentFibonacci {
    private static final Map<Integer, Long> cache = new ConcurrentHashMap<>();

    static {
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public long calculate(int x) {
        return cache.computeIfAbsent(x, n -> calculate(n - 1) + calculate(n - 2));
    }
}


class Fibonacci {
    private static final Map<Integer, Long> cache = new HashMap<>();

    static {
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public long calculate(int x) {
        if (cache.containsKey(x)) {
            return cache.get(x);
        }

        long lhs = cache.containsKey(x - 1) ? cache.get(x - 1) : calculate(x - 1);
        long rhs = cache.containsKey(x - 2) ? cache.get(x - 2) : calculate(x - 2);
        long result = lhs + rhs;

        cache.put(x, result);
        return result;
    }
}

