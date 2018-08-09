package extras.ch5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_11_16 {
    // Example 5-11 classic formatting from a collection
    private static void example511(List<Artist> artists) {
        StringBuilder builder = new StringBuilder("[");
        for (Artist artist : artists) {
            if (builder.length() > 1)
                builder.append(", ");
            builder.append(artist.getName());
        }
        builder.append("]");
        System.out.println("For loop: " + builder.toString());
    }

    // Example 5-12 modern collection formatting w/ joining collector
    private static void example512(List<Artist> artists) {
        String output = artists.stream().map(Artist::getName) //
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Streamed: " + output);
    }

    // Example 5-13 count artists, naive
    private static void example513(Stream<Album> albums) {
        Map<Artist, List<Album>> albumsByArtist = albums.collect( //
                Collectors.groupingBy(album -> album.getMainMusician()));

        Map<Artist, Integer> result = new HashMap<>();
        albumsByArtist.forEach((key, value) -> {
            result.put(key, value.size());
        });

        System.out.println("Albums for artist, naive:");
        result.forEach((a, count) -> {
            System.out.println(a + ": " + count);
        });
    }

    // Example 5-14 same, composing groupingBy and counting collectors
    private static void example514(Stream<Album> albums) {
        Map<Artist, Long> result = albums.collect( //
                Collectors.groupingBy(Album::getMainMusician, Collectors.counting()));
        System.out.println("Albums for artist, improved:");
        result.forEach((a, count) -> {
            System.out.println(a + ": " + count);
        });
    }

    // Example 5-15 find albums from an author, naive
    private static void example515(Stream<Album> albums) {
        // 1. partition the albums grouping by main artist
        Map<Artist, List<Album>> grouped = albums.collect( //
                Collectors.groupingBy(Album::getMainMusician));

        // 2. extract just the name from each album
        Map<Artist, List<String>> result = new HashMap<>();
        for (Entry<Artist, List<Album>> entry : grouped.entrySet()) {
            result.put(entry.getKey(),
                    entry.getValue().stream().map(Album::getName).collect(Collectors.toList()));
        }

        System.out.println("Artist to album names mapping, naive:");
        result.forEach((a, names) -> {
            System.out.println(a + ": " + names);
        });
    }

    // Example 5-16 same, composing collectors - grouping and mapping
    private static void example516(Stream<Album> albums) {
        Map<Artist, List<String>> result = albums.collect( //
                Collectors.groupingBy(Album::getMainMusician,
                        Collectors.mapping(Album::getName, Collectors.toList())));

        System.out.println("Artist to album names mapping, improved:");
        result.forEach((a, names) -> {
            System.out.println(a + ": " + names);
        });
    }

    public static void main(String[] args) {
        example511(SampleData.membersOfTheBeatles);
        example512(SampleData.membersOfTheBeatles);
        example513(SampleData.getStreamAlbums());
        example514(SampleData.getStreamAlbums());
        example515(SampleData.getStreamAlbums());
        example516(SampleData.getStreamAlbums());
    }
}
