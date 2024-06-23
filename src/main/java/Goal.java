import java.util.concurrent.atomic.AtomicInteger;
public class Goal {
    private static AtomicInteger idCounter = new AtomicInteger();

    private final int id;
    private int minute;
    private Player scorer;

    public Goal(int minute, Player scorer) {
        this.minute = minute;
        this.scorer = scorer;
        this.id = idCounter.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public int getMinute() {
        return minute;
    }

    public Player getScorer() {
        return scorer;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setScorer(Player scorer) {
        this.scorer = scorer;
    }

    @Override
    public String toString() {
        return  "Minute: " + minute + ":00" + ", Scored the goal: " + scorer;
    }
}
