package extras.ch5;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Examples_01_04 {
    // Example 5-1 encounter order preserved
    private static void example51() {
        List<Integer> aList = Arrays.asList(1, 2, 3, 4);
        List<Integer> anotherOne = aList.stream().collect(Collectors.toList());
        System.out.println(aList + " same order as " + anotherOne);
    }

    // Example 5-2 hashes do not keep encounter order
    private static void example52() {
        Integer[] base = { 10, 20, 30, 40 };
        Set<Integer> aSet = new HashSet<>(Arrays.asList(base));
        List<Integer> aList = aSet.stream().collect(Collectors.toList());

        System.out.println("Set doesn't keep the encounter order:");
        System.out.printf("%s -> %s -> %s%n", Arrays.toString(base), aSet, aList);
    }

    // Example 5-3 creating an encounter order
    private static void example53() {
        Integer[] base = { 30, 20, 10, 40 };
        Set<Integer> aSet = new HashSet<>(Arrays.asList(base));
        List<Integer> aList = aSet.stream().sorted().collect(Collectors.toList());
        System.out.println("Sort to impose an encounter order");

        Arrays.sort(base);
        System.out.println(Arrays.toString(base) + " == " + aList);
    }

    // Example 5-4 no encounter order leads to weaker assumptions
    private static void example54() {
        List<Integer> aList = Arrays.asList(10, 20, 30, 40);
        List<Integer> anotherList = aList.stream() //
                .map(x -> x + 1) //
                .collect(Collectors.toList());

        System.out.println("Ordered to ordered:");
        System.out.println(aList + " -> " + anotherList);

        Set<Integer> aSet = new HashSet<>(aList);
        List<Integer> aListFromSet = aSet.stream() //
                .map(x -> x + 1) //
                .collect(Collectors.toList());

        System.out.println("Hash from list to list - the original order is lost");
        System.out.println(aList + " -> " + aSet + " -> " + aListFromSet);
    }

    public static void main(String[] args) {
        example51();
        example52();
        example53();
        example54();
    }
}
