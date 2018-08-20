package extras.ch6;

import java.util.Arrays;
import java.util.Random;

public class Examples_07_08 {
    private static final int SIZE = 1_000_000;
    private static final int TEST_SZ = 10;
    private static Random random = new Random();

    private static void arrayInitialize() {
        // Example 6-7. Initializing an array using a for loop
        int[] da = new int[TEST_SZ];
        int[] va = new int[SIZE];
        for (int i = 0; i < TEST_SZ; i++) {
            long start = System.nanoTime();
            for (int j = 0; j < va.length; j++) {
                va[j] = j;
            }
            da[i] = (int) (System.nanoTime() - start) / 100_000;
        }

        // Example 6-8. Initializing an array using a parallel array operation
        int[] db = new int[TEST_SZ];
        int[] vb = new int[SIZE];
        for (int i = 0; i < TEST_SZ; i++) {
            long start = System.nanoTime();
            Arrays.parallelSetAll(vb, j -> j);
            db[i] = (int) (System.nanoTime() - start) / 100_000;
        }

        for (int i = 0; i < SIZE; i++) {
            if (va[i] != vb[i]) {
                System.out.println("Unexpected!");
                return;
            }
        }

        System.out.println("On the long term, same performance expected");
        System.out.println("Serial: " + Arrays.toString(da));
        System.out.println("Parallel: " + Arrays.toString(db));
    }

    private static void arraySorting() {
        int[] base = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            base[i] = random.nextInt();
        }

        int[] a = null;
        int[] b = null;

        int[] da = new int[TEST_SZ];
        for (int i = 0; i < TEST_SZ; i++) {
            a = Arrays.copyOf(base, SIZE);
            long start = System.nanoTime();
            Arrays.sort(a);
            da[i] = (int) (System.nanoTime() - start) / 1_000_000;
        }

        int[] db = new int[TEST_SZ];
        for (int i = 0; i < TEST_SZ; i++) {
            b = Arrays.copyOf(base, SIZE);
            long start = System.nanoTime();
            Arrays.parallelSort(b);
            db[i] = (int) (System.nanoTime() - start) / 1_000_000;
        }

        for (int i = 0; i < SIZE; i++) {
            if (a[i] != b[i]) {
                System.out.println("Unexpected!");
                return;
            }
        }

        System.out.println("Here parallelizing should pay off");
        System.out.println("Serial: " + Arrays.toString(da));
        System.out.println("Parallel: " + Arrays.toString(db));
    }

    public static void main(String[] args) {
        // examples 6-7/8
        arrayInitialize();
        
        // extra
        arraySorting();
    }
}
