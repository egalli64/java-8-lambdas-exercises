package extras.ch7;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToLongFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.examples.chapter1.Track;

public class Examples_01_07 {
    private static Logger logger = Logger.getLogger("ExamplesLogger");

    public static void main(String[] args) {
        // Example 7-1. Logger classic check to avoid performance overhead
        System.err.println("Explicit check before logging a severe message");
        if (logger.isLoggable(Level.SEVERE)) {
            logger.severe("Look at this -> " + expensiveOperation());
        }

        // Example 7-2. Using lambda expressions to simplify logging code
        System.err.println("Implicit check when logging a severe message");
        logger.severe(() -> "Look at this -> " + expensiveOperation());

        System.err.println("Implicit check when logging a finest message");
        logger.finest(() -> "Don't look at this -> " + expensiveOperation());

        // Example 7-5/6/7 Tracks-Album checks
        List<Album> albums = Arrays.asList(SampleData.manyTrackAlbum, SampleData.aLoveSupreme);

        // Example 7-5. imperative implementation
        System.out.println(countRunningTimeClassic(albums));
        System.out.println(countMusiciansClassic(albums));
        System.out.println(countTracksClassic(albums));

        // Example 7-6. use streams
        System.out.println(countRunningTimeModern(albums));
        System.out.println(countMusiciansModern(albums));
        System.out.println(countTracksModern(albums));

        // Example 7-7. use domain-level methods
        System.out.println(countRunningTime(albums));
        System.out.println(countMusicians(albums));
        System.out.println(countTracks(albums));

    }

    // used by example 7-1/2
    private static String expensiveOperation() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Something";
    }

    // Example 7-5. imperative implementation
    public static long countRunningTimeClassic(List<Album> albums) {
        long count = 0;
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                count += track.getLength();
            }
        }
        return count;
    }

    // Example 7-5. imperative implementation
    public static long countMusiciansClassic(List<Album> albums) {
        long count = 0;
        for (Album album : albums) {
            count += album.getMusicianList().size();
        }
        return count;
    }

    // Example 7-5. imperative implementation
    public static long countTracksClassic(List<Album> albums) {
        long count = 0;
        for (Album album : albums) {
            count += album.getTrackList().size();
        }
        return count;
    }

    // Example 7-6. use streams
    public static long countRunningTimeModern(List<Album> albums) {
        return albums.stream() //
                .flatMap(Album::getTracks) //
                .mapToLong(Track::getLength) //
                .sum();
    }

    // Example 7-6. use streams
    public static long countMusiciansModern(List<Album> albums) {
        return albums.stream() //
                .flatMap(Album::getAllMusicians) //
                .count();
    }

    // Example 7-6. use streams
    public static long countTracksModern(List<Album> albums) {
        return albums.stream() //
                .flatMap(Album::getTracks) //
                .count();
    }

    // Example 7-7. use domain-level methods
    private static long countFeature(List<Album> albums, ToLongFunction<Album> function) {
        return albums.stream() //
                .mapToLong(function) //
                .sum();
    }

    // Example 7-7. use domain-level methods
    public static long countRunningTime(List<Album> albums) {
        return countFeature(albums, //
                a -> a.getTracks().mapToLong(Track::getLength).sum());
    }

    // Example 7-7. use domain-level methods
    public static long countMusicians(List<Album> albums) {
        return countFeature(albums, //
                a -> a.getAllMusicians().count());
    }

    // Example 7-7. use domain-level methods
    public static long countTracks(List<Album> albums) {
        return countFeature(albums, //
                a -> a.getTrackList().size());
    }
}
