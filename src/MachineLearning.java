public class MachineLearning {

    private static int TOTAL_RUNS = 3000;
    private static int ARMS = 10;
    private static int ROUNDS = 10000;
    private static int THREADS = 2;

    public static void main(String[] args) {
        new MachineLearning();
    }

    public void printStats(int runs, int goodRuns) {
        double good_runs = goodRuns / (double) runs * 100;

        System.out.print("good/runs: " + goodRuns + "/" + runs + "=");
        System.out.print(String.format("%.1f", good_runs));
        System.out.println("%");

    }

    public void printProgress(int runs) {
        double progress = runs / (double) TOTAL_RUNS * 100;
        System.out
                .print("\tprogress: " + String.format("%.1f", progress) + "%");
        System.out.println();
    }
    
    public MachineLearning() {
        int i, goodRuns = 0;
        WorkerThread threads[] = new WorkerThread[THREADS];
        
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
}
