import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JFrame;

public class MachineLearning {

    private static int TOTAL_RUNS = 2000;
    private static int ARMS = 10;
    private static int ROUNDS = 1000;
    private static int THREADS = 2;
    
    private int i = 0;
    private int goodRuns = 0;
    private boolean done = false;
    private double rewards[];

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
        rewards = new double[ROUNDS];
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
            System.out.println(Arrays.toString(rewards));
            System.out.println(new NArmedBandit(ARMS).toString());
            printGraph();
        }
    }
    
    private void printGraph() {
        JFrame f = new MyFrame("Result");
    }
    
    public synchronized int getRunsDone() { return i; }
    public synchronized void incrRunsDone() { i++; }
    public synchronized int getGoodRuns() { return goodRuns; }
    public synchronized void incrGoodRuns() { goodRuns++; }
    public synchronized void incrReward(int index, double reward) { rewards[index] += reward; }
    
    public int getTotalRuns() { return TOTAL_RUNS; }
    
    private class MyFrame extends JFrame {
        
        public MyFrame(String title) {
            super(title);
            setVisible(true);
            setLocation(100, 100);
            setSize(800, 600);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        
        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
            g.drawLine(0, getHeight()/2-100, getWidth(), getHeight()/2-100);
            int xArr[] = new int[rewards.length];
            int yArr[] = new int[rewards.length];
            for (int i = 0; i < rewards.length; i++) {
                xArr[i] = 10 + (int)(((double)i/ rewards.length) * (getWidth()-10));
                yArr[i] = getHeight()/2 - (int)(100*(rewards[i]/(double)ROUNDS));
                
            }
            g.setColor(Color.red);
            g.drawPolyline(xArr, yArr, xArr.length);
        }
    }
}
