package extras.ch4;

import java.util.Optional;

public class Examples_19_24 {
    public static void main(String[] args) {
        // Example 4-22. Creating an Optional from a value
        Optional<String> a = Optional.of("a");
        System.out.println("Getting value from an optional: " + a.get());

        // Example 4-23. an empty Optional
        Optional<Object> empty = Optional.empty();
        Optional<Object> empty2 = Optional.ofNullable(null);
        System.out.println("Is a value present? " + empty.isPresent());
        System.out.println("\t" + empty2.isPresent());
        System.out.println("\t" + a.isPresent());

        // Example 4-24. orElse and orElseGet
        System.out.println("Get the optional or 'b' if not present: " + empty.orElse("b"));
        System.out.println("Ditto, through supplier: " + empty.orElseGet(() -> "b"));
    }
}

// Multiple Inheritance
// Example 4-19
interface Jukebox {
    public default String rock() {
        return "... all over the world!";
    }
}

// Example 4-20
interface Carriage {
    public default String rock() {
        return "... from side to side";
    }
}

// Multiple Inheritance 4-19/20
// Duplicate default methods inherited!
class MusicalCarriage implements Carriage, Jukebox {

    // we need to take a choice
    @Override
    public String rock() {
        return Carriage.super.rock();
    }
}
