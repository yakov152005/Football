import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeagueManager {

    public List<Match> findMatchesByTeam(int teamId,List<Match> matches){
        //רשימה של משחקים שקבוצה מסוימת שיחקה
        return matches.stream()
                .filter(match -> match.getHomeTeam().getId() == teamId || match.getAwayTeam().getId() == teamId)
                .collect(Collectors.toList());
    }

    public List<Team> findTopScoringTeams(int n){
        //להחזיר את הקבוצות ששהבקיעו את המספר הרבה ביותר של השערים
        return null;
    }

    public List<Player> findPlayersWithAtLeastNGoals(int n){
        //מתודה זו צריכה להחזיר רשימה של שחקנים שכבשו לפחות n  שערים.
        return null;
    }

    public Team getTeamByPosition(int position){
    //מתודה זו אמורה להחזיר את הקבוצה שנמצאת במיקום הנתון בטבלת הליגה.
        return null;
    }

    public Map<Integer, Integer> getTopScorers(int n){
        //מתודה זו מקבלת פרמטר n ואמורה להחזיר
        // את n השחקנים שכבשו את המספר הרב ביותר של שערים,
        // כשהם מאוחסנים במפה: מפתח – מזהה ייחודי של שחקן, ערך – כמות השערים שהבקיע.
        return null;
    }

}
