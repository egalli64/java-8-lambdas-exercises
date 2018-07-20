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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.examples.chapter1.Track;

public class Examples_19_23 {
    public static void main(String[] args) {
        // Paragraph: Refactoring Legacy Code
        List<Album> albums = Arrays.asList(SampleData.aLoveSupreme, SampleData.sampleShortAlbum);

        System.out.println("Get tracks longer than 60 secs");
        System.out.println("Legacy code: " + findLongTracks(albums));
        System.out.println("Refactory 1: " + findLongTracks1(albums));
        System.out.println("Refactory 2: " + findLongTracks2(albums));
        System.out.println("Refactory 3: " + findLongTracks3(albums));
        System.out.println("Refactory 4: " + findLongTracks4(albums));
        System.out.println("Refactory X: " + findLongTracksX(albums));
    }

    // Example 3-19
    public static Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    // Example 3-20
    public static Set<String> findLongTracks1(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream().forEach(album -> { // from for-loop to forEach() on stream
            album.getTracks().forEach(track -> { // from for-loop to forEach() on stream
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            });
        });
        return trackNames;
    }

    // Example 3-21
    public static Set<String> findLongTracks2(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream().forEach(album -> {
            album.getTracks() //
                    .filter(track -> track.getLength() > 60) // filter() instead of if
                    .map(track -> track.getName()) // map() instead of explicit extraction
                    .forEach(name -> trackNames.add(name));
        });
        return trackNames;
    }

    // Example 3-22
    public static Set<String> findLongTracks3(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream() //
                .flatMap(album -> album.getTracks()) // flatMap() instead of forEach()
                .filter(track -> track.getLength() > 60) //
                .map(track -> track.getName()) //
                .forEach(name -> trackNames.add(name));
        return trackNames;
    }

    // Example 3-23
    public static Set<String> findLongTracks4(List<Album> albums) {
        return albums.stream() //
                .flatMap(album -> album.getTracks()) //
                .filter(track -> track.getLength() > 60) //
                .map(track -> track.getName()) //
                .collect(Collectors.toSet()); // collect to implicitly fill in the result
    }

    public static Set<String> findLongTracksX(List<Album> albums) {
        return albums.stream() //
                .flatMap(Album::getTracks) // method reference
                .filter(((Predicate<Track>) Track::isShort).negate()) // utility method negated
                .map(Track::getName) //
                .collect(Collectors.toSet());
    }
}
