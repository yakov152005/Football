import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {
    private static AtomicInteger idCounter = new AtomicInteger();

    private final int id;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    public Match(Team homeTeam, Team awayTeam,List<Goal> goals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.id = idCounter.incrementAndGet();
        this.goals = goals;
    }

    public int getId() {
        return id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Match ID: ").append(id).append("\n");
        sb.append("Home Team: ").append(homeTeam.getTeamName()).append(" (ID: ").append(homeTeam.getId()).append(")\n");
        sb.append("Away Team: ").append(awayTeam.getTeamName()).append(" (ID: ").append(awayTeam.getId()).append(")\n");
        sb.append("Goals:\n");
        for (Goal goal : goals) {
            sb.append(goal).append("\n");
        }
        return sb.toString();
    }
}
