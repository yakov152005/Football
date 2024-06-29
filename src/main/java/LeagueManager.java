import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LeagueManager {
    private final static String[] FIRST_NAMES = {"Yakov", "Daniel", "Ido", "Niv", "Yadin", "Shai", "Gal", "Or", "Odel", "Amit", "Roni", "Sivan", "Toni", "Nick", "Ron", "Adi", "Eli", "Roni", "Ben", "Maor", "Tomer", "David", "Naor", "Yakir", "Itamer", "Nisim", "Nir", "Lowi", "Cristiano", "Lyonal", "Embpa", "Eto", "Sergio", "Alex", "Roy", "Din", "Dan", "Baruch","Tamir","Tal","Div","Eiv","Saar","Lior","Lian","Dolev","Leon","Liam"};
    private final static String[] LAST_NAMES = {"BenHemo", "Gino", "Korn", "Muli", "Amitay", "Givati", "BenHaim", "Shimono", "Balgres", "Aton", "Gon", "Mizrahi", "Messi", "Ronaldo", "David", "Torgman", "Davidovich", "Levi", "Neshar", "Hadida", "Zomer", "Shamir","Shokron","Nahum","Revivo","Zevi","Atias","Robinson","Ben-Dahan","Perez","Tamoz","Bokobza","Malachi"};
    public static Random r = new Random();
    public static Scanner s = new Scanner(System.in);
    public static final int GAMES = 5;
    private List<Team> teamList;
    private List<Match> matches;
    private Map<Team, Integer> scoreTable;

    public LeagueManager() {
        this.teamList = loadTeamsFromCSV("teams.csv");
        this.matches = new LinkedList<>();
        this.scoreTable = new HashMap<>();


        generateMatchSchedule();
        System.out.println("|Welcome to the Champions League|");

        int cycles = 9;
        int numberOfCycles = 1;
        while (cycles > 0) {
            System.out.println("The " +  numberOfCycles + "st Cycle begins");
            System.out.println("---------------------");
            for (int i = 0; i < GAMES; i++) {
                Match match = matches.get(r.nextInt(matches.size()));
                System.out.print("Game number: " + (i + 1)  +" --> ");
                System.out.println(match.getHomeTeam().getTeamName() + " |VS| " + match.getAwayTeam().getTeamName());
                countdown(10);
                runMatch(match);
            }

            printLeagueTable();
            printMenu();
            cycles--;
            numberOfCycles++;
        }

        System.out.println("--------------------");
        System.out.println("Finish Game cycles.");
        System.out.println("|BYE|");
    }

    private void generateMatchSchedule() {
        Set<String> scheduledMatches = new HashSet<>();
        for (int i = 0; i < GAMES; i++) {
            for (Team homeTeam : teamList) {
                for (Team awayTeam : teamList) {
                    if (!homeTeam.equals(awayTeam) && !scheduledMatches.contains(homeTeam.getTeamName() + "-" + awayTeam.getTeamName())) {
                        matches.add(new Match(homeTeam, awayTeam, new LinkedList<>()));
                        scheduledMatches.add(homeTeam.getTeamName() + "-" + awayTeam.getTeamName());
                    }
                }
            }
        }
        Collections.shuffle(matches);
    }

    private void countdown(int seconds) {
        try {
            for (int i = seconds; i > 0; i--) {
                System.out.println(i + "...");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void printTeam(List<Team> teamList){
        System.out.println("Teams and Players:");
        for (Team team : teamList) {
            System.out.println("Team ID: " + team.getId() + ", Name: " + team.getTeamName());
            for (Player player : team.getPlayers()) {
                System.out.println("    " + player);
            }
        }
    }

    private void runMatch(Match match) {
        int goalsTeam1 = r.nextInt(5);
        int goalsTeam2 = r.nextInt(5);

        updateScores(match.getHomeTeam(), match.getAwayTeam(), goalsTeam1, goalsTeam2);

        List<Goal> goals = generateGoals(match.getHomeTeam(), match.getAwayTeam(), goalsTeam1, goalsTeam2);
        match.setGoals(goals);

        System.out.println("THE RESULT OF THE GAME: " + match.getHomeTeam().getTeamName() + ":" + goalsTeam1 + " || " + match.getAwayTeam().getTeamName() + ":" + goalsTeam2);
        System.out.println("Goals:");
        for (Goal goal : goals) {
            System.out.println(goal + ", Team: " + goal.getScorer().getTeam().getTeamName() +", ID: " +goal.getScorer().getId() );
        }
    }

    private void updateScores(Team homeTeam, Team awayTeam, int goalsTeam1, int goalsTeam2) {
        if (goalsTeam1 > goalsTeam2) {
            scoreTable.put(homeTeam, scoreTable.getOrDefault(homeTeam, 0) + 3);
        } else if (goalsTeam1 < goalsTeam2) {
            scoreTable.put(awayTeam, scoreTable.getOrDefault(awayTeam, 0) + 3);
        } else {
            scoreTable.put(homeTeam, scoreTable.getOrDefault(homeTeam, 0) + 1);
            scoreTable.put(awayTeam, scoreTable.getOrDefault(awayTeam, 0) + 1);
        }
    }

    private List<Goal> generateGoals(Team homeTeam, Team awayTeam, int goalsTeam1, int goalsTeam2) {
        List<Goal> goals = new LinkedList<>();
        for (int j = 0; j < goalsTeam1; j++) {
            Goal goal = new Goal(r.nextInt(90) + 1, homeTeam.getPlayers().get(r.nextInt(15)));
            goals.add(goal);
        }
        for (int j = 0; j < goalsTeam2; j++) {
            Goal goal = new Goal(r.nextInt(90) + 1, awayTeam.getPlayers().get(r.nextInt(15)));
            goals.add(goal);
        }
        return goals;
    }

    private void printLeagueTable() {
        System.out.println("|The League table|");
        System.out.println("------------------");
        AtomicInteger index = new AtomicInteger(1);
        scoreTable.entrySet().stream()
                .sorted((e1, e2) -> {
                    int comparison = e2.getValue().compareTo(e1.getValue());
                    if (comparison != 0) return comparison;
                    return e1.getKey().getTeamName().compareTo(e2.getKey().getTeamName());
                })
                .forEach(entry -> System.out.println((index.getAndIncrement()) + ": " +entry.getKey().getTeamName() + ": " + entry.getValue()));
        System.out.println("------------------");
    }

    private void printMenu() {
        boolean temp = true;
        printMenuForSwitch();
        while (temp) {
            String choiceStr;
            choiceStr = s.next();
            int choice = stringToInt(choiceStr);
            if (choice == 0) {
                temp = false;
            } else {
                handleMenuChoice(choice);
            }
        }
    }
    public void printMenuForSwitch(){
        System.out.println();
        System.out.println("""
        1 - Find matches by team.
        2 - Find top scoring teams.
        3 - Find players with at least n goals.
        4 - Get team by position.
        5 - Get top scorers.
        0 - To Exit.
        """);

    }

    public int stringToInt(String input){
        int output = 0;
        try {
            output = Integer.parseInt(input);
        }catch (NumberFormatException e){
            System.out.println("Invalid input.");
        }
        return output;
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> {
                System.out.print("Enter team ID: ");
                String teamIdStr = s.next();
                int teamID = stringToInt(teamIdStr);
//                List<Match> matchesByTeam = findMatchesByTeam(teamID);
//                matchesByTeam.forEach(System.out::println);
                List<Match> playedMatchesByTeam = findMatchesByTeam(teamID);
                if (!playedMatchesByTeam.isEmpty()) {
                    playedMatchesByTeam.forEach(match -> {
                        int goalsTeam1 = 0;
                        int goalsTeam2 = 0;
                        System.out.println("Match ID: " + match.getId());
                        System.out.println("Home Team: " + match.getHomeTeam().getTeamName() + " (ID: " + match.getHomeTeam().getId() + ")");
                        System.out.println("Away Team: " + match.getAwayTeam().getTeamName() + " (ID: " + match.getAwayTeam().getId() + ")");
                        System.out.println("Goals: ");
                        for (Goal goal : match.getGoals()) {
                            System.out.println(goal + ", Team: " + " " + goal.getScorer().getTeam().getTeamName());
                            if (goal.getScorer().getTeam().getTeamName().equals(match.getHomeTeam().getTeamName())) {
                                goalsTeam1++;
                            } else if (goal.getScorer().getTeam().getTeamName().equals(match.getAwayTeam().getTeamName())) {
                                goalsTeam2++;
                            }
                        }
                        System.out.println("Result: " + goalsTeam1 + " - " + goalsTeam2);
                        String winner = goalsTeam1 > goalsTeam2 ? match.getHomeTeam().getTeamName() :
                                goalsTeam1 < goalsTeam2 ? match.getAwayTeam().getTeamName() : "Draw";
                        System.out.println("Winner: " + winner);
                        System.out.println();
                    });
                }else {
                    System.out.println("This team has not played any games yet.");
                }
            }
            case 2 -> {
                System.out.print("Enter number of teams: ");
                String nStr = s.next();
                int n = stringToInt(nStr);
                List<Pair<Team, Integer>> topScoringTeams= findTopScoringTeams(n);
                topScoringTeams.forEach(pair ->
                        System.out.println(pair.getKey().getTeamName()));
            }
            case 3 -> {
                System.out.print("Enter a number of goals: ");
                String nStr = s.next();
                int n = stringToInt(nStr);
                List<Player> playerHigherThenN = findPlayersWithAtLeastNGoals(n);
                if (playerHigherThenN.isEmpty()){
                    System.out.println("There is no player who has made such a number of goals");
                }else {
                    if (n <= 0){
                        System.out.println("Default position is 1.");
                    }
                    playerHigherThenN.forEach(player ->
                            System.out.println(player.getId() + ", " + player.getFirstName() + " " + player.getLastName() +
                            ", " + player.getTeam().getTeamName()));
                }
            }
            case 4 ->{
                System.out.print("Enter the position: ");
                String positionStr = s.next();
                int position = stringToInt(positionStr);
                Team team = getTeamByPosition(position);
                System.out.println(team);
            }
            case 5 ->{
                System.out.print("Enter number of players do u want to see in a top: ");
                String nStr = s.next();
                int n = stringToInt(nStr);
                Map<Integer,Integer> getTopScorers = getTopScorers(n);
                for (Map.Entry<Integer,Integer> m : getTopScorers.entrySet()){
                    System.out.println(m);
                }
            }
            default -> System.out.println("Invalid option.");
        }
        printMenuForSwitch();
    }

    public static List<Team> loadTeamsFromCSV(String fileName) {
        List<Team> teamsList = new ArrayList<>();
        Random random = new Random();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Player> players = new ArrayList<>();
                Team team = new Team(line, players);
                for (int i = 0; i < 15; i++) {
                    String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                    String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                    players.add(new Player(firstName, lastName, team));
                }
                teamsList.add(team);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamsList;
    }

    public List<Match> findMatchesByTeam(int teamId) {
        return matches.stream()
                .filter(match -> (match.getHomeTeam().getId() == teamId || match.getAwayTeam().getId() == teamId) && !match.getGoals().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Pair<Team, Integer>> findTopScoringTeams(int n) {
        Map<Team, Integer> teamGoals = new HashMap<>();

        for (Match match : matches) {
            for (Goal goal : match.getGoals()) {
                Team team = goal.getScorer().getTeam();
                teamGoals.put(team, teamGoals.getOrDefault(team, 0) + 1);
            }
        }

        return teamGoals.entrySet().stream()
                .sorted(Map.Entry.<Team, Integer>comparingByValue().reversed())
                .limit(n)
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }



    public List<Player> findPlayersWithAtLeastNGoals(int n){
//        Map<Player,Integer> playersGoal = new HashMap<>();
//
//        for (Match match : matches) {
//            for (Goal goal : match.getGoals()) {
//               playersGoal.put(goal.getScorer(),playersGoal.getOrDefault(goal.getScorer(),0)+1);
//            }
//        }
//        List<Player> playersHighThenN = new LinkedList<>();
//        for (Map.Entry<Player,Integer> m : playersGoal.entrySet()){
//            if (m.getValue() >= n){
//                playersHighThenN.add(m.getKey());
//            }
//        }
        Map<Player,Long> playersGoal = matches.stream()
                .flatMap(match -> match.getGoals().stream())
                .collect(Collectors.groupingBy(
                        Goal::getScorer,
                        Collectors.counting()
                ));

        List<Player> playersHighThenN;
        playersHighThenN = playersGoal.entrySet().stream()
                .filter(entry -> entry.getValue() >= n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return playersHighThenN;
    }

    public Team getTeamByPosition(int position) {
        List<Map.Entry<Team, Integer>> sortedTeams = scoreTable.entrySet().stream()
                .sorted((e1, e2) -> {
                    int comparison = e2.getValue().compareTo(e1.getValue());
                    if (comparison != 0) return comparison;
                    return e1.getKey().getTeamName().compareTo(e2.getKey().getTeamName());
                })
                .toList();

        if (position < 1 || position > sortedTeams.size()) {
            position +=1;
            System.out.println("Default position is 1.");
        }

        return sortedTeams.get(position - 1).getKey();
    }


    public Map<Integer, Integer> getTopScorers(int n) {
        Map<Integer, Integer> goalsMap = new HashMap<>();

        for (Match match : matches) {
            for (Goal goal : match.getGoals()) {
                int idPlayer = goal.getScorer().getId();
                goalsMap.put(idPlayer, goalsMap.getOrDefault(idPlayer, 0) + 1);
            }
        }


//        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(goalsMap.entrySet());
//        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
//
//
//        Map<Integer, Integer> topScorers = new LinkedHashMap<>();
//        for (int i = 0; i < n && i < sortedEntries.size(); i++) {
//            Map.Entry<Integer, Integer> entry = sortedEntries.get(i);
//            topScorers.put(entry.getKey(), entry.getValue());
//        }
//
//        return topScorers;

        return goalsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}
