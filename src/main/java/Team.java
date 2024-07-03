import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Team {
    private static AtomicInteger idCounter = new AtomicInteger();

    private final int id;
    private String teamName;
    private List<Player> players;

    public Team(String teamName, List<Player> players) {
        this.teamName = teamName;
        this.id = idCounter.incrementAndGet();
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: ").append(this.teamName).append(" ID-").append(id).append("\n");
        sb.append("Players -- > ").append("\n");
        int index = 1;
        for (Player player: players){
            sb.append(index++).append(": ").append(player).append("\n");
        }
        return sb.toString();
    }
}
