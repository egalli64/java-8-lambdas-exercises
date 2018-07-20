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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.insightfullogic.java8.examples.chapter1.Track;

public class Examples_13_14 {
    public static void main(String[] args) {
        // Paragraph: max and min
        // Example 3-13
        List<Track> tracks = Arrays.asList(new Track("Bakai", 524), //
                new Track("Violets for Your Furs", 378), //
                new Track("Time Was", 451));

        Track track = tracks.stream() //
                .min(Comparator.comparing(t -> t.getLength())) //
                .get(); // ?! what if tracks is empty?
        System.out.println("Shortest track: " + track);

        // Counterexample 3-13a
        try {
            track = Collections.<Track>emptyList().stream() //
                    .min(Comparator.comparing(t -> t.getLength())) //
                    .get();
            System.out.println(":" + track);
        } catch (NoSuchElementException nse) {
            System.out.println("As expected, an exception. " + nse);
        }

        // Patched 3-13b
        // Lot of stuff in it not already explained in the book ...
        Optional<Track> ot = tracks.stream() //
                .min(Comparator.comparing(Track::getLength));
        System.out.println("Shortest track: " + ot.orElse(Track.EMPTY));

        ot = Collections.<Track>emptyList().stream() //
                .min(Comparator.comparing(Track::getLength));
        System.out.println("Shortest track: " + ot.orElse(Track.EMPTY));

        // Paragraph: A Common Pattern Appears
        // Example 3-14
        Track result = tracks.get(0); // ?! are we sure tracks is not empty?
        for (Track t : tracks) {
            if (t.getLength() < result.getLength()) {
                result = t;
            }
        }
        System.out.println("Ditto - classic: " + track);
    }
}
