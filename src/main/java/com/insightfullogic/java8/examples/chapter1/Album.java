/**
 * Java 8 Lambdas
 * @author Richard Warburton
 * 
 * Minor changes by
 * @author manny egalli64@gmail.com
 */
package com.insightfullogic.java8.examples.chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author richard
 */
public final class Album implements Performance {
    
    private String name;
    private List<Track> tracks;
    private List<Artist> musicians;

    public Album(String name, List<Track> tracks, List<Artist> musicians) {
        this.name = Objects.requireNonNull(name);
        this.tracks = new ArrayList<>(Objects.requireNonNull(tracks));
        this.musicians = new ArrayList<>(Objects.requireNonNull(musicians));
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the tracks
     */
    public Stream<Track> getTracks() {
        return tracks.stream();
    }

    /**
     * Used in imperative code examples that need to iterate over a list
     */
    public List<Track> getTrackList() {
        return unmodifiableList(tracks);
    }

    /**
     * Simplify filtering of short/long albums
     * 
     * @return are there less than four tracks?
     */
    public boolean hasFewTracks() {
        return getTrackList().size() < 4;
    }
    
    /**
     * @return the musicians
     */
    public Stream<Artist> getMusicians() {
        return musicians.stream();
    }

    /**
     * Used in imperative code examples that need to iterate over a list
     */
    public List<Artist> getMusicianList() {
        return unmodifiableList(musicians);
    }

    public Artist getMainMusician() {
        return musicians.get(0);
    }

    public Album copy() {
        List<Track> tracks = getTracks().map(Track::copy).collect(toList());
        List<Artist> musicians = getMusicians().map(Artist::copy).collect(toList());
        return new Album(name, tracks, musicians);
    }

    /**
     * Always useful
     * 
     * @author manny
     */
    @Override
    public String toString() {
        return name + " by " + musicians;
    }
}
