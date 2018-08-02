package extras.ch4;

import java.util.function.Consumer;

public class Examples_10_18 {
    public static void main(String[] args) {
        // Example 4-12: ParentImpl does not define welcome()
        // its default implementation from Parent interface is used!
        Parent parent = new ParentImpl();
        parent.welcome();
        System.out.println(parent.getLastMessage());

        // Example 4-14: ChildImpl does not define welcome()
        // its default implementation from Child interface is used!
        // notice the default in Parent is override by Child
        Parent child = new ChildImpl();
        child.welcome();
        System.out.println(child.getLastMessage());

        // Example 4-16: as expected, the OverridingParent implementation is used
        Parent op = new OverridingParent();
        op.welcome();
        System.out.println(op.getLastMessage());
        
        // Example 4-18. concrete wins against interface
        Parent oc = new OverridingChild();
        oc.welcome();
        System.out.println(oc.getLastMessage());
        
    }
}

interface MyIterable<T> extends Iterable<T> {
    @Override
    // Example 4-10: An example default method
    default void forEach(Consumer<? super T> action) {
        for (T t : this) {
            action.accept(t);
        }
    }
}

// Example 4-11
interface Parent {
    public void message(String body);

    public default void welcome() {
        message("Parent: Hi!");
    }

    public String getLastMessage();
}

class ParentImpl implements Parent {

    private String body;

    @Override
    public void message(String body) {
        this.body = body;
    }

    @Override
    public String getLastMessage() {
        return body;
    }
}

// Example 4-13
interface Child extends Parent {
    @Override
    public default void welcome() {
        message("Child: Hi!");
    }
}

class ChildImpl extends ParentImpl implements Child {
}

// Example 4-15: class overriding of interface default
class OverridingParent extends ParentImpl {
    @Override
    public void welcome() {
        message("OverridingParent Class: Hi!");
    }
}

// Example 4-17. OverridingParent (concrete) wins against Child (interface)
class OverridingChild extends OverridingParent implements Child {
}
