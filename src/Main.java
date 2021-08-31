import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        List<Match> matchesData = getMatchesData();
        System.out.println(matchesData.get(2).getId());
        HashMap<String, Integer> matchesPlayedPerYear = getMatchesPlayedPerYear(matchesData);
        System.out.println(matchesPlayedPerYear);
        HashMap<String, Integer> noOfMatchesWonByAllTeams = getTheNoOfMatchesWOnByAllTeams(matchesData);
        System.out.println(noOfMatchesWonByAllTeams);
        HashMap<String,Integer> extraRunsPerTeamIn2016=getExtraRunsPerTeams();


    }

    private static HashMap<String, Integer> getTheNoOfMatchesWOnByAllTeams(List<Match> matchesData) {
        int counter = 0; //to skip first row.
        HashMap<String, Integer> matchesWonPerTeam = new HashMap<>();
        Iterator<Match> iterator = matchesData.iterator();
        while (iterator.hasNext()) {
            Match match = iterator.next();
            if (counter == 0) {
                counter++;
                continue;
            }

            if (!matchesWonPerTeam.containsKey(match.getTeam1())) {
                matchesWonPerTeam.put(match.getTeam1(), 0);
            }

            if (!matchesWonPerTeam.containsKey(match.getTeam2())) {
                matchesWonPerTeam.put(match.getTeam2(), 0);
            }

            if (!matchesWonPerTeam.containsKey(match.getWinner())) {
                matchesWonPerTeam.put(match.getWinner(), 0);
            } else {
                matchesWonPerTeam.put(match.getWinner(), 1 + matchesWonPerTeam.get(match.getWinner()));
            }
        }
        if (matchesWonPerTeam.containsKey("")) {  //to remove blank key if present
            matchesWonPerTeam.remove("");
        }

        return matchesWonPerTeam;
    }

    private static HashMap<String, Integer> getMatchesPlayedPerYear(List<Match> matchesData) {
        HashMap<String, Integer> matchesPlayedPerYear = new HashMap<>();
        Iterator<Match> iterator = matchesData.iterator();
        while (iterator.hasNext()) {
            Match match = iterator.next();
            if (matchesPlayedPerYear.containsKey(match.getSeason())) {
                matchesPlayedPerYear.put(match.getSeason(), 1 + matchesPlayedPerYear.get(match.getSeason()));
            } else {
                matchesPlayedPerYear.put(match.getSeason(), 1);
            }
        }
        return matchesPlayedPerYear;
    }


    private static List<Match> getMatchesData() {
        final int ID = 0, SEASON = 1, CITY = 2, DATE = 3, TEAM1 = 4, TEAM2 = 5, TOSS_WINNER = 6, TOSS_DECISION = 7, RESULT = 8, DL_APPLIE = 9, WINNER = 10, WIN_BY_RUNS = 11, WIN_BY_WICKETS = 12, PLAYER_OF_MATCH = 13, VENUE = 14, UMPIRE1 = 15, UMPIRE2 = 16, UMPIRE3 = 17;

        int counter = 0;
        String matchData;
        List<Match> matchDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((matchData = br.readLine()) != null) {
                Match match = new Match();
                if (counter == 0) {
                    counter++;
                    continue;
                }
                String matchDataSplitted[] = matchData.split(",");
                match.setId(matchDataSplitted[0]);
                match.setSeason(matchDataSplitted[1]);
                match.setCity(matchDataSplitted[2]);
                match.setDate(matchDataSplitted[3]);
                match.setTeam1(matchDataSplitted[4]);
                match.setTeam2(matchDataSplitted[5]);
                match.setToss_decision(matchDataSplitted[6]);
                match.setWinner(matchDataSplitted[10]);
                matchDataList.add(match);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
        return matchDataList;
    }
}