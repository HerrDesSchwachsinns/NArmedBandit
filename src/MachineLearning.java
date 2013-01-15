public class MachineLearning {

    private static int TOTAL_RUNS = 3000;
    private static int ARMS = 10;

    public static void main(String[] args) {
        int i, goodRuns = 0;

        for (i = 1; i <= TOTAL_RUNS; ++i) {
            NArmedBandit bandit = new NArmedBandit(ARMS);
            Player player = new SimplePlayer(bandit);
            int best = player.play(1000000);
            if (best == bandit.getBestArmIndex())
                goodRuns++;
            if (i % 10 == 0) {
                printStats(i, goodRuns);
                printProgress(i);
            }
        }
        printStats(i, goodRuns);
    }

    public static void printStats(int runs, int goodRuns) {
        double good_runs = goodRuns / (double) runs * 100;

        System.out.print("good/runs: " + goodRuns + "/" + runs + "=");
        System.out.print(String.format("%.1f", good_runs));
        System.out.println("%");

    }

    public static void printProgress(int runs) {
        double progress = runs / (double) TOTAL_RUNS * 100;
        System.out
                .print("\tprogress: " + String.format("%.1f", progress) + "%");
        System.out.println();
    }
}
