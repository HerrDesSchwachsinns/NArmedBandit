import java.util.Random;

public class NArmedBandit {
    private int n;
    private double[] array;
    private Random rand;

    public NArmedBandit(int n) {
        this.n = n;
        array = new double[n];

        rand = new Random();

        for (int i = 0; i < n; ++i)
            array[i] = rand.nextDouble();
        // System.out.println(this);
    }

    public int play(int n) {
        if (array[n] > rand.nextDouble())
            return 1;
        else
            return 0;
    }

    public String toString() {
        String str = "[ ";
        for (int i = 0; i < n; ++i)
            str += String.format("%.1f", array[i] * 100) + "% ";
        str += "]";

        return str;
    }

    public int n() {
        return n;
    }

    public int getBestArmIndex() {
        int maxIndex = 0;
        for (int i = 0; i < n; ++i) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
