import java.util.Random;

public class Player {
    private int[] rewards;
    private Random rand;
    private NArmedBandit bandit;

    public Player(NArmedBandit bandit) {
        rewards = new int[bandit.getNumArms()];
        rand = new Random();
        this.bandit = bandit;
    }

    public int play(int games) {
        int i = 0;
        for (; i < games / 2; ++i) {
            explore();
        }
        // System.out.println(java.util.Arrays.toString(rewards));
        for (; i < games; ++i) {
            exploit();
        }
        return getMaxRewardIndex();
    }

    private void explore() {
        int n = rand.nextInt(bandit.getNumArms());
        rewards[n] += bandit.play(n);
    }

    private void exploit() {
        double[] probSum = new double[bandit.getNumArms()];
        int sum = sumRewards();
        if (sum <= 0) {
            explore();
            return;
        }

        probSum[0] = rewards[0] / (double) sum;
        for (int i = 1; i < probSum.length; ++i) {
            probSum[i] = probSum[i - 1] + rewards[i] / (double) sum;
        }
        // System.out.println(java.util.Arrays.toString(probSum));

        double d = rand.nextDouble();
        int n = 0;
        while (n < probSum.length && d > probSum[n]) {
            ++n;
        }
        rewards[n] += bandit.play(n);

    }

    private int getMaxRewardIndex() {
        int maxIndex = 0;
        for (int i = 0; i < bandit.getNumArms(); ++i) {
            if (rewards[i] > rewards[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public int sumRewards() {
        int sum = 0;
        for (Integer i : rewards) {
            sum += i;
        }
        return sum;
    }

    public String toString() {
        String str = "[ ";
        for (int i = 0; i < bandit.getNumArms(); ++i)
            str += rewards[i] + " ";
        str += "]";

        return str;
    }
}
