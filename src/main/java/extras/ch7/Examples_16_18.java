package extras.ch7;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_16_18 {
    public static void main(String[] args) {
        System.out.println(106);
        // Example 7-16. Logging intermediate values in order to debug a for loop
        System.out.println(getNationalities(SampleData.manyTrackAlbum));

        // Example 7-17. Using a naive forEach to log intermediate values
        SampleData.manyTrackAlbum.getMusicians() //
                .map(artist -> artist.getNationality()) //
                .forEach(nationality -> System.out.println("Found: " + nationality));
        // to fetch the intermediate value, we need to reset the musicians stream!
        Set<String> nationalities = SampleData.manyTrackAlbum.getMusicians() //
                .map(artist -> artist.getNationality()) //
                .collect(Collectors.<String>toSet());
        System.out.println(nationalities);

        // Example 7-18. Using peek to log intermediate values
        nationalities = SampleData.manyTrackAlbum.getMusicians() //
                .map(artist -> artist.getNationality())
                .peek(nation -> System.out.println("Found nationality: " + nation))
                .collect(Collectors.<String>toSet());
        System.out.println(nationalities);
    }

    // Example 7-16. Logging intermediate values in order to debug a for loop
    private static Set<String> getNationalities(Album album) {
        Set<String> result = new HashSet<>();
        for (Artist artist : album.getMusicianList()) {
            String nationality = artist.getNationality();
            System.out.println("Found nationality: " + nationality);
            result.add(nationality);
        }
        return result;
    }
}
