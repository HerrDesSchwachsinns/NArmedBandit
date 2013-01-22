import java.util.Random;

public class GreedyPlayer implements Player {
	private double[] rewards;
	private Random rand;
	private NArmedBandit bandit;

	private double epsilon;

	public GreedyPlayer(NArmedBandit bandit, double epsilon) {
		rewards = new double[bandit.getNumArms()];
		rand = new Random();
		this.bandit = bandit;
		this.epsilon = epsilon;
	}

	public int play(int games) {
		for (int i = 0; i < games; ++i) {
			if (rand.nextDouble() < epsilon)
				explore();
			else
				exploit();
		}
		return getBestArmIndex();
	}

	private void explore() {
		int n = rand.nextInt(bandit.getNumArms());
		rewards[n] += bandit.play(n);
	}

	private void exploit() {
		double[] probSum = new double[bandit.getNumArms()];
		double sum = sumRewards();
		if (sum <= 0) { // prevent division by zero
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

	public int getBestArmIndex() {
		int maxIndex = 0;
		for (int i = 0; i < bandit.getNumArms(); ++i) {
			if (rewards[i] > rewards[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	public double sumRewards() {
		double sum = 0;
		for (Double i : rewards) {
			sum += i;
		}
		return sum;
	}
}
