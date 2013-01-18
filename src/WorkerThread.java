
public class WorkerThread extends Thread {
    private boolean done = false;
    public NArmedBandit bandit;
    public Player player;
    private int arms;
    private int rounds;
    private MachineLearning mgr;

    public WorkerThread(MachineLearning mgr, int arms, int rounds) {
        super();
        this.mgr = mgr;
        this.arms = arms;
        this.rounds = rounds;
    }

    @Override
    public void run() {
        if (!done) {
            while (mgr.getRunsDone() < mgr.getTotalRuns()) {
                mgr.incrRunsDone();
                bandit = new NArmedBandit(arms);
                player = new SimplePlayer(bandit);
                player.play(rounds);
                if (player.getBestArmIndex() == bandit.getBestArmIndex())
                    mgr.incrGoodRuns();
            }
            done = true;
        }
    }
    
    public boolean isDone(){return done;}
}
