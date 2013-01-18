
public class WorkerThread extends Thread {
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
