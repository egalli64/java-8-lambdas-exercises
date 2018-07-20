/**
 * Java 8 Lambdas, Chapter 3, Common Stream Operations
 * 
 * @author manny egalli64@gmail.com
 * 
 * Heavily based on com.insightfullogic.java8.examples.chapter3.HigherOrderFunctionExamplesTest
 * @author Richard Warburton
 */
package extras.ch3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Examples_08_12 {
    public static void main(String[] args) {
        // Paragraph: collect(toList())
        List<String> abcd_ = Arrays.asList("a", "b", "c", "d_");
        List<String> collected = abcd_.stream().collect(Collectors.toList());
        System.out.println("Collect to list: " + collected);

        // Paragraph: map
        // Example 3-8
        collected.clear();
        for (String s : Arrays.asList("a", "b", "hello")) {
            collected.add(s.toUpperCase());
        }
        System.out.println("Convert strings to uppercase - classic: " + collected);

        // Example 3-9
        collected = Stream.of("a", "b", "hello") //
                .map(s -> s.toUpperCase()) //
                .collect(Collectors.toList()); //
        System.out.println("Ditto, modern: " + collected);

        // Paragraph: filter
        // Example 3-10
        collected.clear();
        for (String s : abcd_) {
            if (s.length() == 1) {
                collected.add(s);
            }
        }
        System.out.println("Filtered by size - classic: " + collected);

        collected = abcd_.stream() //
                .filter(s -> s.length() == 1) //
                .collect(Collectors.toList());
        System.out.println("Ditto - modern: " + collected);

        // Paragraph: flatMap
        // Example 3-12
        collected = Stream.of(Arrays.asList("a", "b"), Arrays.asList("c", "d")) // stream of lists
                .flatMap(li -> li.stream()) // lists flattened
                .collect(Collectors.toList());
        System.out.println("Flattened list: " + collected);
    }

}
