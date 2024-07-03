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
    public Player getScorer() {
        return scorer;
    }
    @Override
    public String toString() {
        return  "Minute: " + minute + ":00" + ", Scored the goal: " + scorer;
    }
}
