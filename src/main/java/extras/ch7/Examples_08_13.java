package extras.ch7;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Examples_08_13 {
    // Example 7-8. Converting strings into their uppercase equivalents
    public static List<String> allToUpperCase(List<String> words) {
        return words.stream() //
                .map(s -> s.toUpperCase()) //
                .collect(Collectors.toList());
    }

    // Example 7-12. Upping first character only from a string
    // extracted from example 7-10 to make code more readable and testable
    public static final Function<String, String> firstUp = //
            s -> Character.toUpperCase(s.charAt(0)) + s.substring(1);

    // Example 7-10. Convert first character of all list elements to uppercase
    public static List<String> elementFirstToUpperCaseLambdas(List<String> words) {
        return words.stream() //
                .map(firstUp) //
                .collect(Collectors.<String>toList());
    }

    public static void main(String[] args) {
        // Example 7-9. Testing conversion of words to uppercase equivalents
        List<String> words = List.of("a", "few", "words", "seen", "as", "a", "list");
        System.out.println(words);
        System.out.println(allToUpperCase(words));
        // Example 7-11. Testing that only the first characters are upped
        System.out.println(elementFirstToUpperCaseLambdas(words));
        // Example 7-13. Testing the upping function
        System.out.println(firstUp.apply("hello"));
    }
}
