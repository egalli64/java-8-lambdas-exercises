package extras.ch3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;

public class Exercises {
    // 1. Common Stream operations
    // 1a reducing a stream to its sum
    public static int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (lhs, rhs) -> lhs + rhs);
    }

    // 1a2 raw variation
    public static int addUp(IntStream numbers) {
        return numbers.sum();
    }

    // 1.b extract name and nationality for each artist
    public static List<String> namesAndOrigins(List<Artist> artists) {
        return artists.stream().flatMap(a -> Stream.of(a.getName(), a.getNationality()))
                .collect(Collectors.toList());
    }

    // 1.c filter out albums with less than four tracks
    public static List<Album> threeTracksOrLess(List<Album> box) {
        return box.stream() //
                .filter(a -> a.getTrackList().size() < 4) //
                .collect(Collectors.toList());
    }

    // 1c2 simplified filtering adding method in Album
    public static List<Album> fewTracks(List<Album> box) {
        return box.stream() //
                .filter(Album::hasFewTracks) //
                .collect(Collectors.toList());
    }

    // 2 code provided to be refactored
    public static int countArtistsOriginal(List<Artist> artists) {
        int totalMembers = 0;
        for (Artist artist : artists) {
            Stream<Artist> members = artist.getMembers();
            totalMembers += members.count();
        }

        return totalMembers;
    }

    // 2 refactored code
    public static int countArtists(List<Artist> artists) {
        return (int) artists.stream() //
                .flatMap(Artist::getMembers) //
                .count();
    }

    // 6 count lowercase letters in a string
    public static long lowercaseLetters(String s) {
        return s.chars() //
                .filter(Character::isLowerCase) //
                .count();
    }

    // 7 get the longest string, considering only lowercase characters for each one
    public static Optional<String> mostLowercase(List<String> data) {
        return data.stream() //
                .max(Comparator.comparing(Exercises::lowercaseLetters));
    }
}
