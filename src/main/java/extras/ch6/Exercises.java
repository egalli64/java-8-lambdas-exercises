package extras.ch6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Exercises {

    public static void main(String[] args) {
        System.out.println("Example 6-10: " + sequentialSumOfSquares(IntStream.range(1, 5 + 1)));
        System.out.println("Parallelize it: " + ex1(IntStream.range(1, 5 + 1)));

        List<Integer> data = List.of(1, 2, 3, 4, 5);
        System.out.printf("Example 6-11: %s -> %d%n", data, multiplyThrough(data));
        System.out.printf("Parallel bug: %s -> %d%n", data, mtp(data));
        System.out.printf("Ex 2 serial: %s -> %d%n", data, ex2(data));
        System.out.printf("Ex 2 parallel: %s -> %d%n", data, ex2p(data));

        System.out.println("Ex 3: Improve performance of this algo");
        int size = 1_000_000;
        LinkedList<Integer> li = new LinkedList<>();
        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            li.add(2);
            al.add(2);
        }

        int testSz = 10;
        long[] times = new long[testSz];
        for (int i = 0; i < testSz; i++) {
            long start = System.nanoTime();
            slowSumOfSquares(li);
            times[i] = (System.nanoTime() - start) / 100_000;
        }
        System.out.println(Arrays.toString(times));

        System.out.println("Ex 3: Using primitive types is faster");
        for (int i = 0; i < testSz; i++) {
            long start = System.nanoTime();
            ex3s(al);
            times[i] = (System.nanoTime() - start) / 100_000;
        }
        System.out.println(Arrays.toString(times));

        System.out.println("Ex 3: Parallelizing could help");
        for (int i = 0; i < testSz; i++) {
            long start = System.nanoTime();
            ex3(al);
            times[i] = (System.nanoTime() - start) / 100_000;
        }
        System.out.println(Arrays.toString(times));
    }

    // Example 6-10. Sequentially summing the squares of numbers in a list
    public static int sequentialSumOfSquares(IntStream range) {
        return range.map(x -> x * x).sum();
    }

    private static int ex1(IntStream range) {
        return range.parallel().map(x -> x * x).sum();
    }

    // Example 6-11: buggy multiplying together every number and then by 5
    // works fine sequentially
    public static int multiplyThrough(List<Integer> data) {
        return data.stream().reduce(5, (acc, x) -> x * acc);
    }

    // Example 6-11: buggy multiplying together every number and then by 5
    // don't do that
    public static int mtp(List<Integer> data) {
        // BUGGY!
        return data.stream().parallel().reduce(5, (acc, x) -> x * acc);
    }

    public static int ex2(List<Integer> data) {
        return 5 * data.stream().reduce(1, (acc, x) -> x * acc);
    }

    public static int ex2p(List<Integer> data) {
        return 5 * data.stream().parallel().reduce(1, (acc, x) -> x * acc);
    }

    // Example 6-12. Slow implementation of summing the squares of numbers in a list
    public static int slowSumOfSquares(LinkedList<Integer> li) {
        return li.parallelStream() //
                .map(x -> x * x) //
                .reduce(0, (acc, x) -> acc + x);
    }

    public static int ex3(ArrayList<Integer> al) {
        return al.parallelStream().mapToInt(x -> x * x).sum();
    }

    public static int ex3s(ArrayList<Integer> al) {
        return al.stream().mapToInt(x -> x * x).sum();
    }
}
