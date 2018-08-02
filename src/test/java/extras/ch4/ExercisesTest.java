package extras.ch4;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.Is.*;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

class ExercisesTest {

    @Test // ex1
    void testPerformaceGetAllMusician() {
        Performance pfm = new BeatlesPerformance();
        List<Artist> la = pfm.getAllMusician().collect(Collectors.toList());
        assertThat(la.size(), is(5));
        assertThat(la.get(0).getName(), is("The Beatles"));
        assertThat(la.get(1).getName(), is("John Lennon"));
        assertThat(la.get(2).getName(), is("Paul McCartney"));
        assertThat(la.get(3).getName(), is("George Harrison"));
        assertThat(la.get(4).getName(), is("Ringo Starr"));
    }

    // ex3 - as provided
    private static Artists artists = new Artists(SampleData.threeArtists().collect(Collectors.toList()));

    @Test
    void testGetArtistOriginalSuccess() {
        assertThat(artists.getArtist(0).getName(), is("John Coltrane"));
    }

    @Test
    void testGetArtistOriginalFailure() {
        assertThrows(IllegalArgumentException.class, () -> artists.getArtist(42));
    }

    @Test
    void testGetArtistNameOriginalSuccess() {
        assertThat(artists.getArtistName(0), is("John Coltrane"));
    }

    @Test
    void testGetArtistNameOriginalFailure() {
        assertThat(artists.getArtistName(42), is("unknown"));
    }

    // ex3 - refactored
    private static ArtistsOptional aos = new ArtistsOptional(
            SampleData.threeArtists().collect(Collectors.toList()));


    @Test
    void testNewGetArtistSuccess() {
        Optional<Artist> result = aos.getArtist(0);
        assertTrue(result.isPresent());
        assertThat(result.get().getName(), is("John Coltrane"));
    }

    @Test
    void testNewGetArtistFailure() {
        assertFalse(aos.getArtist(42).isPresent());
    }

    @Test
    void testNewGetArtistNameSuccess() {
        Optional<String> result = aos.getArtistName(0);
        assertTrue(result.isPresent());
        assertThat(result.get(), is("John Coltrane"));
    }

    @Test
    void testNewGetArtistNameFailure() {
        assertFalse(aos.getArtistName(42).isPresent());
    }
}
