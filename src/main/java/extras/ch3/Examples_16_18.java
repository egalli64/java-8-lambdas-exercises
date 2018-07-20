/**
 * Java 8 Lambdas, Chapter 3, Common Stream Operations
 * 
 * Examples rewritten for my better comprehension
 * @author manny egalli64@gmail.com
 * 
 * From the cited ones in the book 
 * @author Richard Warburton
 */
package extras.ch3;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Examples_16_18 {
    public static void main(String[] args) {
        // Paragraph: reduce
        // Example 3-16
        int count = Stream.of(1, 2, 3).reduce(0, (lhs, rhs) -> lhs + rhs);
        System.out.println("Sum by reduce: " + count);
        
        // 3-16a: feature preview
        count = Stream.of(1, 2, 3).reduce(0, Integer::sum);
        System.out.println("Ditto - Integer::sum: " + count);
        
        // 3-16b: another feature preview - get rid of boxing/unboxing
        count = IntStream.of(1, 2, 3).sum();
        System.out.println("Ditto - IntStream.sum(): " + count);
        
        // Example 3-18
        count = 0;
        for (int value : new int[]{1, 2, 3}) {
            count += value;
        }
        System.out.println("Ditto - classic: " + count);
        
    }
}
