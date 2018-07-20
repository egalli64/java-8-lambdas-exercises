/**
 * Java 8 Lambdas
 * @author Richard Warburton
 * 
 * Minor changes by
 * @author manny egalli64@gmail.com
 */
package com.insightfullogic.java8.examples.chapter1;

/**
 * @author richard
 */
public final class Track {
    /**
     * Null Object Pattern
     * 
     * @author manny
     */
    public static final Track EMPTY = new Track("N/A", 0);

    private final String name;
    private final int length;

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the length of the track in milliseconds.
     */
    public int getLength() {
        return length;
    }

    /**
     * Useful to simplify filtering short/long tracks
     * 
     * @author manny
     * @return is the track considered short?
     */
    public boolean isShort() {
        return length <= 60;
    }

    public Track copy() {
        return new Track(name, length);
    }

    @Override
    public String toString() {
        return "[" + name + ", " + length + "]";
    }
}
