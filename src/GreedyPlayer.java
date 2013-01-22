import java.util.Random;

public class GreedyPlayer implements Player {
	private double[] rewards;
	private Random rand;
	private NArmedBandit bandit;

	private double epsilon;
	private MachineLearning mgr;

	public GreedyPlayer(MachineLearning mgr, NArmedBandit bandit, double epsilon) {
		rewards = new double[bandit.getNumArms()];
		rand = new Random();
		this.mgr = mgr;
		this.bandit = bandit;
		this.epsilon = epsilon;
	}

	public int play(int games) {
	    double reward;
		for (int i = 0; i < games; ++i) {
			if (rand.nextDouble() < epsilon)
				reward = explore();
			else
				reward = exploit();
			mgr.incrReward(i, reward);
		}
		return getBestArmIndex();
	}

	private double explore() {
		int n = rand.nextInt(bandit.getNumArms());
		double reward = bandit.play(n);
		rewards[n] += reward;
		return reward;
	}

	private double exploit() {
//		double[] probSum = new double[bandit.getNumArms()];
//		double sum = sumRewards();
//		if (sum <= 0) { // prevent division by zero
//			return explore();
//		}

//		probSum[0] = rewards[0] / (double) sum;
//		for (int i = 1; i < probSum.length; ++i) {
//			probSum[i] = probSum[i - 1] + rewards[i] / (double) sum;
//		}
		// System.out.println(java.util.Arrays.toString(probSum));

//		double d = rand.nextDouble();
//		int n = 0;
//		while (n < probSum.length && d > probSum[n]) {
//			++n;
//		}
	    
	    int n = 0;
	    for (int i = 1; i < rewards.length; i++)
	        if (rewards[i] > rewards[n])
	            n = i;
		double reward = bandit.play(n);
		rewards[n] += reward;
		return reward;
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
