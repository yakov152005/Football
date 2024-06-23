import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    private final static String[] FIRST_NAMES = {"Yakov", "Daniel", "Ido", "Niv", "Yadin", "Shai", "Gal", "Or", "Odel", "Amit", "Roni", "Sivan","Toni","Nick","Ron","Adi","Eli"};
    private final static String[] LAST_NAMES = {"BenHemo", "Gino", "Holi", "Muli", "Lolo", "Givati", "BenHaim", "Shimono", "Nono", "Aton", "Gon", "Mizrahi"};

    public Main() {
        List<Team> teamList = loadTeamsFromCSV("teams.csv");
        System.out.println("Teams and Players:");
        for (Team team : teamList) {
            System.out.println("Team ID: " + team.getId() + ", Name: " + team.getTeamName());
            for (Player player : team.getPlayers()) {
                System.out.println("    " + player);
            }
        }
        Goal[] goalsArr = {
                new Goal(5, teamList.get(1).getPlayers().get(2)),
                new Goal(20, teamList.get(1).getPlayers().get(3)),
                new Goal(45, teamList.get(2).getPlayers().get(6)),
                new Goal(60, teamList.get(2).getPlayers().get(4)),
        };

        Goal[] goalsArr2 = {
                new Goal(15, teamList.get(1).getPlayers().get(2)),
                new Goal(34, teamList.get(1).getPlayers().get(3)),
                new Goal(44, teamList.get(3).getPlayers().get(6)),
                new Goal(88, teamList.get(3).getPlayers().get(4)),
        };

        List<Goal> goals = new LinkedList<>();
        for (int i = 0; i < goalsArr.length; i++) {
            goals.add(goalsArr[i]);
        }

        List<Goal> goals2 = new LinkedList<>();
        for (int i = 0; i < goalsArr2.length; i++) {
            goals2.add(goalsArr2[i]);
        }


        Match match = new Match(teamList.get(1), teamList.get(2), goals);
        Match match2 = new Match(teamList.get(1),teamList.get(3),goals2);

        List<Match> matchList = new ArrayList<>();
        matchList.add(match);
        matchList.add(match2);
        LeagueManager leagueManager = new LeagueManager();
        List<Match> newMatch = leagueManager.findMatchesByTeam(teamList.get(1).getId(), matchList);
        System.out.println(newMatch);
    }

    public static List<Team> loadTeamsFromCSV(String fileName) {
        List<Team> teamsList = new ArrayList<>();
        Random random = new Random();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" +fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Player> players = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                    String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                    players.add(new Player(firstName, lastName));
                }
                Team team = new Team(line, players);
                teamsList.add(team);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamsList;
    }

    public static void main(String[] args) {
        new Main();
    }
}
