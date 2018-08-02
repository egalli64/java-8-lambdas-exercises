package extras.ch4;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Exercises {
    public static void main(String[] args) {
        // ex1 - see ExercisesTest for getAllMusician() test
        Performance pfm = new BeatlesPerformance();
        System.out.println("Musicians in performance:");
        pfm.getMusicians().forEach(System.out::println);

        System.out.println("Mapped musicians in performance:");
        pfm.getMusicians() //
                .flatMap(a -> a.getMembers()) //
                .forEach(System.out::println);

        System.out.println("Gropus _and_ mapped musicians in performance:");
        pfm.getMusicians() //
                .flatMap( //
                        a -> Stream.concat(Stream.of(a), a.getMembers())) //
                .forEach(System.out::println);
    }
}

// Example 4-25. An interface denoting the concept of a musical performance
/** A Performance by some musicians - e.g., an Album or Gig. */
interface Performance {
    public String getName();

    public Stream<Artist> getMusicians();

    // ex 1
    /**
     * 
     * @return a Stream of the artists performing and, in the case of groups, any
     *         musicians who are members of those groups
     */
    default public Stream<Artist> getAllMusician() {
        return getMusicians() //
                .flatMap(a -> Stream.concat( //
                        Stream.of(a), a.getMembers()));
    }
}

// a concrete class implementing the above interface
class BeatlesPerformance implements Performance {

    @Override
    public String getName() {
        return "whatever";
    }

    @Override
    public Stream<Artist> getMusicians() {
        return Stream.of(SampleData.theBeatles);
    }
}

// Example 4-26
class Artists {
    private List<Artist> artists;

    public Artists(List<Artist> artists) {
        this.artists = artists;
    }

    /*
     * Robustness: Has to be refactored to return an Optional<Artist>
     * implies refactoring of getArtistName()
     */
    public Artist getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            indexException(index);
        }
        return artists.get(index);
    }

    private void indexException(int index) {
        throw new IllegalArgumentException(index + " doesn't correspond to an Artist");
    }

    /*
     * To be refactored as getArtist() changes
     */
    public String getArtistName(int index) {
        try {
            Artist artist = getArtist(index);
            return artist.getName();
        } catch (IllegalArgumentException e) {
            return "unknown";
        }
    }
}

// ex3
class ArtistsOptional {
    private List<Artist> artists;

    public ArtistsOptional(List<Artist> artists) {
        this.artists = artists;
    }

    public Optional<Artist> getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            return Optional.empty();
        }
        return Optional.of(artists.get(index));
    }

    public Optional<String> getArtistName(int index) {
        return getArtist(index).map(Artist::getName);
    }
}

