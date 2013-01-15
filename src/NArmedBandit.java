import java.util.Random;

public class NArmedBandit {
    private int numArms;
    private double[] array;
    private Random rand;

    public NArmedBandit(int numArms) {
        this.numArms = numArms;
        array = new double[numArms];

        rand = new Random();

        for (int i = 0; i < numArms; ++i)
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
        for (int i = 0; i < numArms; ++i)
            str += String.format("%.1f", array[i] * 100) + "% ";
        str += "]";

        return str;
    }

    public int getNumArms() {
        return numArms;
    }

    public int getBestArmIndex() {
        int maxIndex = 0;
        for (int i = 0; i < numArms; ++i) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
