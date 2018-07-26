package extras.ch3;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

import static org.hamcrest.core.Is.*;

class ExercisesTest {
    @Test
    void addUpEmpty() {
        assertThat(Exercises.addUp(Collections.<Integer>emptyList().stream()), is(0));
    }

    @Test
    void addUpPlain() {
        assertThat(Exercises.addUp(Stream.of(1, 2, 3, 4, 5, 6)), is(21));
    }

    @Test
    void addUpRawEmpty() {
        assertThat(Exercises.addUp(IntStream.empty()), is(0));
    }

    @Test
    void addUpRawPlain() {
        assertThat(Exercises.addUp(IntStream.of(1, 2, 3, 4, 5, 6)), is(21));
    }

    @Test
    void namesAndOriginsEmpty() {
        List<String> result = Exercises.namesAndOrigins(Collections.<Artist>emptyList());
        assertThat(result.size(), is(0));
    }

    @Test
    void namesAndOriginsPlain() {
        List<String> result = Exercises.namesAndOrigins(SampleData.getThreeArtists());
        assertThat(result.size(), is(6));
        assertThat(result.get(0), is("John Coltrane"));
        assertThat(result.get(1), is("US"));
        assertThat(result.get(2), is("John Lennon"));
        assertThat(result.get(3), is("UK"));
        assertThat(result.get(4), is("The Beatles"));
        assertThat(result.get(5), is("UK"));
    }

    @Test
    void threeTracksOrLess() {
        List<Album> data = Arrays.asList(SampleData.manyTrackAlbum, SampleData.sampleShortAlbum,
                SampleData.aLoveSupreme);
        List<Album> result = Exercises.threeTracksOrLess(data);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(data.get(1)));
        assertThat(result.get(1), is(data.get(2)));
    }

    @Test
    void fewTracks() {
        List<Album> data = Arrays.asList(SampleData.manyTrackAlbum, SampleData.sampleShortAlbum,
                SampleData.aLoveSupreme);
        List<Album> result = Exercises.fewTracks(data);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(data.get(1)));
        assertThat(result.get(1), is(data.get(2)));
    }

    @Test
    void testCountArtistsOriginalEmpty() {
        assertThat(Exercises.countArtistsOriginal(Collections.<Artist>emptyList()), is(0));
    }

    @Test
    void testCountArtistsClassicPlain() {
        assertThat(Exercises.countArtistsOriginal(SampleData.getThreeArtists()), is(4));
    }

    @Test
    void testCountArtistsEmpty() {
        assertThat(Exercises.countArtists(Collections.<Artist>emptyList()), is(0));
    }

    @Test
    void testCountArtistsPlain() {
        assertThat(Exercises.countArtists(SampleData.getThreeArtists()), is(4));
    }

    @Test
    void testLowercaseLettersEmpty() {
        assertThat(Exercises.lowercaseLetters(""), is(0L));
    }

    @Test
    void testLowercaseLettersPlain() {
        assertThat(Exercises.lowercaseLetters("SillyWalk"), is(7L));
    }

    @Test
    void testMostLowercaseEmpty() {
        assertThat(Exercises.mostLowercase(Collections.emptyList()).isPresent(), is(false));
    }

    @Test
    void testMostLowercasePlain() {
        String expected = "win";
        assertThat(Exercises.mostLowercase(Arrays.asList("NopE", expected)).get(), is(expected));
    }

    @Test
    void testMostLowercaseTie() {
        String expected = "Nope";
        assertThat(Exercises.mostLowercase(Arrays.asList(expected, "win")).get(), is(expected));
    }
}
