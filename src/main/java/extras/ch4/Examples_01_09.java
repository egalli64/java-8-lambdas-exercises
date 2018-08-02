package extras.ch4;

import java.util.IntSummaryStatistics;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_01_09 {
    private static Logger logger = Logger.getLogger("ExamplesLogger");

    public static void main(String[] args) {
        // Example 4-1 and 4-2
        classicOpWithSevereLogging();
        modernOpWithSevereLogging();

        System.err.println("Disabling logging");
        logger.setLevel(Level.OFF);

        classicOpWithSevereLogging();
        modernOpWithSevereLogging();

        // Example 4-4
        printTrackLengthStatistics(SampleData.aLoveSupreme);

        // Example 4-5 using an overload method
        overloadedMethod("abc");
        overloadedMethod(42);

        // Example 4-7 4-9 (un)ambiguous overload for functional
        overloadedMethod((Integer x, Integer y) -> x * y);
        overloadedMethod((int x, int y) -> x + y);
        overloadedMethod((x) -> true);
    }

    // Example 4-1: explicit log status check
    private static void classicOpWithSevereLogging() {
        System.err.println("Explicit check before logging a severe message");
        if (logger.isLoggable(Level.SEVERE)) {
            logger.severe("Look at this -> " + expensiveOperation());
        }
    }

    // Example 4-2. lambda enabled logging
    private static void modernOpWithSevereLogging() {
        System.err.println("Implicit check when logging a severe message");
        logger.severe(() -> "Look at this -> " + expensiveOperation());
    }

    private static String expensiveOperation() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Something";
    }

    // Example 4-4 mapping to primitive
    public static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackStats = album.getTracks() //
                .mapToInt(track -> track.getLength()) // map to an IntStream
                .summaryStatistics(); //
        System.out.printf("Album stats -> Max: %d, min: %d, mean: %f, sum: %d%n", //
                trackStats.getMax(), //
                trackStats.getMin(), //
                trackStats.getAverage(), //
                trackStats.getSum());
    }

    // Example 4-6 defining overloaded methods
    private static void overloadedMethod(Object o) {
        System.out.println("Overload for Object " + o.getClass().getSimpleName() + " " + o);
    }

    private static void overloadedMethod(String s) {
        System.out.println("Overload for String " + s);
    }

    // Example 4-8 4-9
    private static void overloadedMethod(BinaryOperator<Integer> lambda) {
        System.out.println("BinaryOperator: " + lambda.apply(42, 12));
    }

    private static void overloadedMethod(IntBinaryOperator lambda) {
        System.out.println("IntBinaryOperator: " + lambda.applyAsInt(42, 12));
    }

    private static void overloadedMethod(IntPredicate lambda) {
        System.out.println("IntPredicate: " + lambda.test(42));
    }
}
