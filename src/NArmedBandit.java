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
            array[i] = rand.nextGaussian();
    }

    public double play(int n) {
            return rand.nextGaussian()+array[n];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < numArms; ++i)
            sb.append(String.format("%5.2f%% ", array[i] * 100));
        sb.append(']');

        return sb.toString();
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
