public class MachineLearning {

    private static int TOTAL_RUNS = 3000;
    private static int ARMS = 10;
    private static int ROUNDS = 10000;
    private static int THREADS = 2;

    public static void main(String[] args) {
        int i, goodRuns = 0;
        WorkerThread threads[] = new WorkerThread[THREADS];
        //for (int j = 0; j < THREADS; j++)

        /*for (i = 1; i <= TOTAL_RUNS; ++i) {
            NArmedBandit bandit = new NArmedBandit(ARMS);
            Player player = new SimplePlayer(bandit);
            int best = player.play(1000000);
            if (best == bandit.getBestArmIndex())
                goodRuns++;
            if (i % 10 == 0) {
                printStats(i, goodRuns);
                printProgress(i);
            }
        }*/
        
        i = 1;
        while (i <= TOTAL_RUNS) {
            for (int j = 0; j < THREADS; j++)
                if (threads[j] == null) {
                    threads[j] = new WorkerThread(ARMS, ROUNDS);
                    threads[j].start();
                    i++;
                }
                else if (threads[j].isDone()) {
                    if (threads[j].player.getBestArmIndex() == threads[j].bandit.getBestArmIndex())
                        goodRuns++;
                    threads[j] = new WorkerThread(ARMS, ROUNDS);
                    threads[j].start();
                    i++;
                }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
            if (i % 10 == 0) {
                //printStats(i, goodRuns);
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
    
    private static class WorkerThread extends Thread {
        private boolean done = false;
        public NArmedBandit bandit;
        public Player player;
        private int rounds;

        public WorkerThread(int arms, int rounds) {
            super();
            this.bandit = new NArmedBandit(arms);
            this.player = new SimplePlayer(bandit);
            this.rounds = rounds;
        }

        @Override
        public void run() {
            if (!done) {
                player.play(rounds);
                done = true;
            }
        }
        
        public boolean isDone(){return done;}
    
    }
}
