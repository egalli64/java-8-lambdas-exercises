/**
 * Java 8 Lambdas, Chapter 3, examples from 1 to 7
 * 
 * @author manny egalli64@gmail.com
 * 
 * Heavily based on com.insightfullogic.java8.examples.chapter3.Iteration
 * @author Richard Warburton
 */
package extras.ch3;

import java.util.Iterator;
import java.util.List;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_01_07 {
    private static List<Artist> artists = SampleData.membersOfTheBeatles;

    public static void main(String[] args) {
        System.out.println("Counting items in a collection with a given feature");
        System.out.println("For-each loop: " + example31());
        System.out.println("Iterator loop: " + example32());

        // Example 3-3. Counting UK artists using internal iteration
        System.out.println("Internal iteration: " + //
                artists.stream() //
                        .filter(a -> a.isFrom("UK")) //
                        .count());

        // Example 3-6. Print intermediate steps in filtering
        System.out.println("Debug filter: " + //
                artists.stream() //
                        .filter(artist -> {
                            System.out.println(artist.getName());
                            return artist.isFrom("UK");
                        }) //
                        .count());
    }

    // Example 3-1. Counting UK artists using a for loop
    static int example31() {
        int result = 0;
        for (Artist artist : artists) {
            if (artist.isFrom("UK")) {
                result += 1;
            }
        }
        return result;
    }

    // Example 3-2. Counting UK artists using an iterator
    static int example32() {
        int result = 0;
        Iterator<Artist> iterator = artists.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isFrom("UK")) {
                result += 1;
            }
        }
        return result;
    }

}
