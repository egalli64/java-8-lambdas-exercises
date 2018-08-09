package extras.ch5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_31_34 {
    // Example 5-31/2 cache
    static private Map<String, Artist> artistCache = new HashMap<>();

    // Example 5-31/2 fake access to db
    private static Artist readArtistFromDB(String name) {
        System.out.println("Faking a DB access for " + name);
        return new Artist(name, "DK");
    }

    // Example 5-31 classic caching
    private static Artist getArtistClassic(String name) {
        Artist artist = artistCache.get(name);
        if (artist == null) {
            artist = readArtistFromDB(name);
            artistCache.put(name, artist);
        }
        return artist;
    }

    // Example 5-32 Map.computeIfAbsent() for modern caching
    public static Artist getArtistModern(String name) {
        return artistCache.computeIfAbsent(name, Examples_31_34::readArtistFromDB);
    }

    // Example 5-33 classic iterating on map
    private static void ex533(Map<Artist, List<Album>> albumsByArtist) {
        Map<Artist, Integer> countOfAlbums = new HashMap<>();
        for (Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
            Artist artist = entry.getKey();
            List<Album> albums = entry.getValue();
            countOfAlbums.put(artist, albums.size());
        }
        System.out.println("Classic count of albums per artist: " + countOfAlbums);
    }

    private static void ex534(Map<Artist, List<Album>> albumsByArtist) {
        Map<Artist, Integer> countOfAlbums = new HashMap<>();
        albumsByArtist.forEach((artist, albums) -> {
            countOfAlbums.put(artist, albums.size());
        });

        System.out.println("Modern count of albums per artist: " + countOfAlbums);
    }

    
    public static void main(String[] args) {
        // Example 5-31 classic caching
        System.out.println(getArtistClassic("Tom"));
        System.out.println(getArtistClassic("Tom"));
        // Example 5-32 computeIfAbsent for modern caching
        System.out.println(getArtistModern("Bill"));
        System.out.println(getArtistModern("Bill"));

        // generate a map of albums grouped by artist for examples 5-33/4
        Stream<Album> albums = Arrays.asList( //
                SampleData.aLoveSupreme, SampleData.manyTrackAlbum) //
                .stream();
        Map<Artist, List<Album>> albumsByArtist = albums //
                .collect(Collectors.groupingBy(Album::getMainMusician));

        ex533(albumsByArtist);
        ex534(albumsByArtist);
    }
}
