package features;

public class CartState {

    private static int expectedCount = 0;

    public static void reset() {
        expectedCount = 0;
    }

    public static void add() {
        expectedCount++;
    }

    public static void remove() {
        if (expectedCount > 0) {
            expectedCount--;
        }
    }

    public static int get() {
        return expectedCount;
    }
}
