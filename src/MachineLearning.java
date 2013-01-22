public class MachineLearning {

    private static int TOTAL_RUNS = 3000;
    private static int ARMS = 10;
    private static int ROUNDS = 5000;
    private static int THREADS = 2;
    
    private int i = 0;
    private int goodRuns = 0;
    private boolean done = false;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        new MachineLearning().run();
        System.out.printf("Took %.6f seconds",(System.nanoTime() - startTime)*1e-9);
    }

    public void printStats(int runs, int goodRuns) {
        double good_runs = goodRuns / (double) runs * 100;

        System.out.print("good/runs: " + goodRuns + "/" + runs + "=");
        System.out.print(String.format("%.1f", good_runs));
        System.out.println("%");

    }

    public void printProgress() {
        double progress = getRunsDone() / (double) TOTAL_RUNS * 100;
        System.out
                .print("\tprogress: " + String.format("%.1f", progress) + "%");
        System.out.println();
    }
    
    public MachineLearning() {
        
    }
    
    public void run() {
        if (!done) {
            int k = 0;
            WorkerThread threads[] = new WorkerThread[THREADS];
            
            boolean done = false;
            for (int j = 0; j < THREADS; j++){
                threads[j] = new WorkerThread(this, ARMS, ROUNDS);
                threads[j].start();
            }
            while (!done) {
                try {
                    k++;
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                if (k % 5 == 0) {
                    //printStats(i, goodRuns);
                    printProgress();
                    if (getRunsDone() >= TOTAL_RUNS)
                        done = true;
                }
            }
            
            for (int j = 0; j < THREADS; j++){
                try {
                    threads[j].join();
                } catch (InterruptedException e) {}
            }
            
            printStats(getRunsDone(), getGoodRuns());
        }
    }
    
    public synchronized int getRunsDone() { return i; }
    public synchronized void incrRunsDone() { i++; }
    public synchronized int getGoodRuns() { return goodRuns; }
    public synchronized void incrGoodRuns() { goodRuns++; }
    
    public int getTotalRuns() { return TOTAL_RUNS; }
}
