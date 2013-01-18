public class MachineLearning {

    private static int TOTAL_RUNS = 3000;
    private static int ARMS = 10;
    private static int ROUNDS = 10000;
    private static int THREADS = 2;
    
    private int i = 0;
    private boolean done = false;

    public static void main(String[] args) {
        new MachineLearning();
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
            int k = 0, goodRuns = 0;
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
                if (k % 10 == 0) {
                    //printStats(i, goodRuns);
                    printProgress();
                    if (getRunsDone() >= TOTAL_RUNS)
                        done = true;
                }
            }
            
            printStats(getRunsDone(), goodRuns);
        }
    }
    
    public synchronized int getRunsDone() {
        return i;
    }
    
    public synchronized void incrRunsDone() { i++; }
}
