package extras.ch6;

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class Examples_09 {    // 
    /**
     * Example 6-9. Calculating a simple moving average
     * 
     * @param values the input data
     * @param size number of elements in the window
     * @return the moving average for each window in input
     */
    public static double[] simpleMovingAverage(double[] values, int size) {
        double[] partialSum = Arrays.copyOf(values, values.length);
        Arrays.parallelPrefix(partialSum, Double::sum);
        System.out.println("Partial sum: " + Arrays.toString(partialSum));

        final int begin = size - 1;
        IntToDoubleFunction winAvg = i -> {
            // leftSum is the partial sum for the left of the window
            double leftSum = (i == begin) ? 0 : partialSum[i - size];
            // get the partial sum for the window only, than divide for its size
            return (partialSum[i] - leftSum) / size;
        };

        // get the window average up to the 'begin' element on
        return IntStream.range(begin, partialSum.length).mapToDouble(winAvg).toArray();
    }

    public static void main(String[] args) {
        double[] values = { 0, 1, 2, 3, 4, 3.5 };
        System.out.println("Input: " + Arrays.toString(values));

        double[] avg = simpleMovingAverage(values, 3);
        System.out.println("Output: " + Arrays.toString(avg));
    }
}
