package extras.ch5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_05_10 {
    // Example 5-5 Collecting to custom collection
    private static void example55() {
        List<Integer> aList = Arrays.asList(10, 40, 30, 20);
        TreeSet<Integer> tse = aList.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println("The set ranges from " + tse.first() + " to " + tse.last());
    }

    // Example 5-6 maxBy collector
    private static void example56() {
        Stream<Artist> sa = SampleData.threeArtists();

        Optional<Artist> biggestGroup = sa.collect( //
                Collectors.maxBy( //
                        Comparator.comparing( //
                                a -> a.getMembers().count())));
        System.out.println("Biggest group is: " + biggestGroup.orElse(Artist.EMPTY));
    }

    // Example 5-7 averagingInt collector
    private static void example57() {
        double avg = SampleData.getStreamAlbums().collect( //
                Collectors.averagingInt(a -> a.getTrackList().size()));

        System.out.println("Average number of tracks in albums: " + avg);
    }

    // Example 5-8/9 partitioningBy collector
    private static void example58() {
        Map<Boolean, List<Artist>> ma = SampleData.threeArtists() //
                .collect(Collectors.partitioningBy(Artist::isSolo));
        System.out.println("Solo: " + ma.get(true));
        System.out.println("Band: " + ma.get(false));
    }

    // Example 5-10 groupingBy collector
    private static void example510() {
        Map<Artist, List<Album>> maa = SampleData.getStreamAlbums() //
                .collect(Collectors.groupingBy(Album::getMainMusician));
        for (Map.Entry<Artist, List<Album>> entry : maa.entrySet()) {
            System.out.println("Artist " + entry.getKey() + ": ");
            System.out.println(entry.getValue());
        }
    }

    public static void main(String[] args) {
        example55();
        example56();
        example57();
        example58();
        example510();
    }
}
