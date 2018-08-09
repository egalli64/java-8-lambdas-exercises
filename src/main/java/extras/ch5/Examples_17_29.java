package extras.ch5;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

public class Examples_17_29 {
    // Example 5-17 classic pretty print of a collection
    private static void example517(List<Artist> artists) {
        StringBuilder builder = new StringBuilder("[");
        for (Artist artist : artists) {
            if (builder.length() > 1)
                builder.append(", ");
            builder.append(artist.getName());
        }
        builder.append("]");
        System.out.println("For loop: " + builder.toString());
    }

    // Example 5-18 Light refactoring
    private static void example518(List<Artist> artists) {
        StringBuilder builder = new StringBuilder("[");
        artists.stream().map(Artist::getName).forEach(name -> {
            if (builder.length() > 1)
                builder.append(", ");
            builder.append(name);
        });
        builder.append("]");
        System.out.println("Refactored w/ stream, map, forEach: " + builder.toString());
    }

    // Example 5-19 Map reduce, first tentative
    private static void example519(List<Artist> artists) {
        StringBuilder reduced = artists.stream().map(Artist::getName) //
                .reduce(new StringBuilder(), (builder, name) -> {
                    if (builder.length() > 0)
                        builder.append(", ");
                    builder.append(name);
                    return builder;
                }, (lhs, rhs) -> lhs.append(rhs));
        reduced.insert(0, "[");
        reduced.append("]");
        System.out.println("Refactored w/ map-reduce (1): " + reduced.toString());
    }

    // Example 5-20 map reduce, with a support class
    private static void example520(List<Artist> artists) {
        StringCombiner combined = artists.stream().map(Artist::getName) //
                .reduce(new StringCombiner(", ", "[", "]"), //
                        StringCombiner::add, StringCombiner::merge);
        System.out.println("Refactored w/ map-reduce (2): " + combined.toString());
    }

    // Example 5-24 refactoring the reduce to collector
    private static void example524(List<Artist> artists) {
        String result = artists.stream().map(Artist::getName)
                .collect(new StringCollector(", ", "[", "]"));
        System.out.println("Refactored to collector: " + result);
    }

    public static void main(String[] args) {
        example517(SampleData.getThreeArtists());
        example518(SampleData.getThreeArtists());
        example519(SampleData.getThreeArtists());
        example520(SampleData.getThreeArtists());
        example524(SampleData.getThreeArtists());
    }
}

// used by Example 5-20 see java.util.StringJoiner
class StringCombiner {
    private final String prefix;
    private final String suffix;
    private final String delim;
    private final StringBuilder builder = new StringBuilder();

    public StringCombiner(String delim, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.delim = delim;
    }

    // Example 5-21 reduce accumulator
    public StringCombiner add(String word) {
        if (!areAtStart()) {
            builder.append(delim);
        }
        builder.append(word);

        return this;
    }

    // Example 5-22 reduce combiner
    public StringCombiner merge(StringCombiner other) {
        if (!other.equals(this)) {
            if (!other.areAtStart() && !this.areAtStart()) {
                this.builder.insert(0, this.delim);
            }
            this.builder.append(other.builder);
        }
        return this;
    }

    @Override
    public String toString() {
        return prefix + builder.toString() + suffix;
    }

    private boolean areAtStart() {
        return builder.length() == 0;
    }
}

// Example 5-25 collector used in 5-24
class StringCollector implements Collector<String, StringCombiner, String> {
    private static final Set<Characteristics> characteristics = Collections.emptySet();

    private final String delim;
    private final String prefix;
    private final String suffix;

    public StringCollector(String delim, String prefix, String suffix) {
        this.delim = delim;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    // Example 5-26 supplier: factory of combiner
    @Override
    public Supplier<StringCombiner> supplier() {
        return () -> new StringCombiner(delim, prefix, suffix);
    }

    // Example 5-27 accumulator to fold
    @Override
    public BiConsumer<StringCombiner, String> accumulator() {
        return StringCombiner::add;
    }

    // Example 5-28 combiner to merge
    @Override
    public BinaryOperator<StringCombiner> combiner() {
        return StringCombiner::merge;
    }

    // Example 5-29 finisher to produce the result
    @Override
    public Function<StringCombiner, String> finisher() {
        return StringCombiner::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}