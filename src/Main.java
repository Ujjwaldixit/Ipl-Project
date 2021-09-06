import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    static final int ID = 0, SEASON = 1, CITY = 2, DATE = 3, TEAM1 = 4, TEAM2 = 5, TOSS_WINNER = 6, TOSS_DECISION = 7,
            RESULT = 8, DL_APPLIED = 9, WINNER = 10, WIN_BY_RUNS = 11, WIN_BY_WICKETS = 12, PLAYER_OF_MATCH = 13,
            VENUE = 14, UMPIRE1 = 15, UMPIRE2 = 16, UMPIRE3 = 17;

    static final int MATCH_ID = 0, INNING = 1, BATTING_TEAM = 2, BOWLING_TEAM = 3, OVER = 4, BAlL = 5, BATSMAN = 6,
            NON_STRIKER = 7, BOWLER = 8, IS_SUPER_OVER = 9, WIDE_RUNS = 10, BYE_RUNS = 11, LEGBYE_RUNS = 12,
            NOBALL_RUNS = 13, PENALTY_RUNS = 14, BATSMAN_RUN = 15, EXTRA_RUNS = 16, TOTAL_RUNS = 17,
            PLAYER_DISMISSED = 18, DISMISSAL_KIND = 19, FIELDER = 20;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveryData();

        findMatchesPlayedPerYear(matches);
        findMatchesWonPerTeam(matches);
        findExtraRunsPerTeamsIn2016(matches, deliveries);
        findEconomicalBowlersPerRunGivenIn2015(matches, deliveries);
        findTotalMatchesPlayedPerCity(matches);
    }

    private static void findTotalMatchesPlayedPerCity(List<Match> matches) {
        HashMap<String, Integer> totalMatchesPlayedPerCity = new HashMap<>();
        for (Match match : matches) {
            if (totalMatchesPlayedPerCity.containsKey(match.getCity())) {
                totalMatchesPlayedPerCity.put(match.getCity(), totalMatchesPlayedPerCity.get(match.getCity()) + 1);
            } else {
                totalMatchesPlayedPerCity.put(match.getCity(), 1);
            }
        }
        // if totalMatchesPlayedPerCity contains empty key
        totalMatchesPlayedPerCity.remove("");

        System.out.println("\n\nCITY => MATCHES");
        for (Map.Entry<String, Integer> entry : totalMatchesPlayedPerCity.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());
    }


    private static void findEconomicalBowlersPerRunGivenIn2015(List<Match> matches, List<Delivery> deliveries) {
        HashMap<String, Integer> bowlerPerRunGiven = new HashMap<>();
        LinkedHashSet<String> matchIds = new LinkedHashSet<>();
        for (Match match : matches) {
            if (match.getSeason().equals("2015")) {
                matchIds.add(match.getId());
            }
        }

        for (Delivery delivery : deliveries) {
            if (matchIds.contains(delivery.getMatch_id())) {
                if (!bowlerPerRunGiven.containsKey(delivery.getBowler())) {
                    bowlerPerRunGiven.put(delivery.getBowler(), Integer.parseInt(delivery.getTotalRuns()));
                } else {
                    bowlerPerRunGiven.put(delivery.getBowler(), bowlerPerRunGiven.get(delivery.getBowler())
                            + Integer.parseInt(delivery.getTotalRuns()));
                }
            }
        }

        System.out.println("\n\nBOWLERS => RUN GIVEN");
        for (Map.Entry<String, Integer> entry : bowlerPerRunGiven.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());

    }

    private static void findExtraRunsPerTeamsIn2016(List<Match> matches, List<Delivery> deliveries) {
        HashMap<String, Integer> teamPerExtraRuns = new HashMap<>();
        LinkedHashSet<String> matchIds = new LinkedHashSet<>();
        for (Match match : matches) {
            if (match.getSeason().equals("2016")) {
                matchIds.add(match.getId());
            }
        }

        for (Delivery delivery : deliveries) {
            if (matchIds.contains(delivery.getMatch_id())) {
                if (!teamPerExtraRuns.containsKey(delivery.getBattingTeam())) {
                    teamPerExtraRuns.put(delivery.getBattingTeam(), Integer.parseInt(delivery.getExtraRuns()));
                } else {
                    teamPerExtraRuns.put(delivery.getBattingTeam(), teamPerExtraRuns.get(delivery.getBattingTeam())
                            + Integer.parseInt(delivery.getExtraRuns()));
                }
            }
        }
        System.out.println("\n\nTEAMS =>  EXTRA RUNS");
        for (Map.Entry<String, Integer> entry : teamPerExtraRuns.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());


    }


    private static List<Delivery> getDeliveryData() {
        String deliveryData;
        List<Delivery> deliveryDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("deliveries.csv"))) {
            while ((deliveryData = br.readLine()) != null) {
                String[] deliveryDataColumn = deliveryData.split(","); //split delivery data by ","(comma)
                Delivery delivery = new Delivery();
                delivery.setMatch_id(deliveryDataColumn[MATCH_ID]);
                delivery.setInning(deliveryDataColumn[INNING]);
                delivery.setBattingTeam(deliveryDataColumn[BATTING_TEAM]);
                delivery.setBowlingTeam(deliveryDataColumn[BOWLING_TEAM]);
                delivery.setOver(deliveryDataColumn[OVER]);
                delivery.setBall(deliveryDataColumn[BAlL]);
                delivery.setBatsman(deliveryDataColumn[BATSMAN]);
                delivery.setNonStriker(deliveryDataColumn[NON_STRIKER]);
                delivery.setBowler(deliveryDataColumn[BOWLER]);
                delivery.setIsSuperOver(deliveryDataColumn[IS_SUPER_OVER]);
                delivery.setWideRuns(deliveryDataColumn[WIDE_RUNS]);
                delivery.setByeRuns(deliveryDataColumn[BYE_RUNS]);
                delivery.setNoBallRuns(deliveryDataColumn[NOBALL_RUNS]);
                delivery.setLegByeRuns(deliveryDataColumn[LEGBYE_RUNS]);
                delivery.setPenaltyRuns(deliveryDataColumn[PENALTY_RUNS]);
                delivery.setBatsmanRuns(deliveryDataColumn[BATSMAN_RUN]);
                delivery.setExtraRuns(deliveryDataColumn[EXTRA_RUNS]);
                delivery.setTotalRuns(deliveryDataColumn[TOTAL_RUNS]);
                deliveryDataList.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveryDataList;
    }


    private static List<Match> getMatchesData() {

        int counter = 0;
        String matchData;
        List<Match> matchDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("matches.csv"))) {
            while ((matchData = br.readLine()) != null) {
                Match match = new Match();
                if (counter == 0) {
                    counter++;
                    continue;
                }
                String[] matchDataColumn = matchData.split(","); // splitting matches.csv data by ","(comma)
                match.setId(matchDataColumn[ID]);
                match.setSeason(matchDataColumn[SEASON]);
                match.setCity(matchDataColumn[CITY]);
                match.setDate(matchDataColumn[TEAM1]);
                match.setTeam1(matchDataColumn[TEAM2]);
                match.setTeam2(matchDataColumn[TOSS_WINNER]);
                match.setTossDecision(matchDataColumn[TOSS_DECISION]);
                match.setWinner(matchDataColumn[WINNER]);
                matchDataList.add(match);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return matchDataList;
    }


    private static void findMatchesWonPerTeam(List<Match> matches) {
        HashMap<String, Integer> matchesWonPerTeam = new HashMap<>();
        for (Match match : matches) {
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
        //to remove blank key if present
        matchesWonPerTeam.remove("");

        System.out.println("\n\nTEAM => NO OF MATCHES WON");
        for (Map.Entry<String, Integer> entry : matchesWonPerTeam.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());
    }

    private static void findMatchesPlayedPerYear(List<Match> matches) {
        HashMap<String, Integer> matchesPlayedPerYear = new HashMap<>();
        for (Match match : matches) {
            if (matchesPlayedPerYear.containsKey(match.getSeason())) {
                matchesPlayedPerYear.put(match.getSeason(), 1 + matchesPlayedPerYear.get(match.getSeason()));
            } else {
                matchesPlayedPerYear.put(match.getSeason(), 1);
            }
        }

        System.out.println("\n\nSEASON => MATCHES PLAYED");
        for (Map.Entry<String, Integer> entry : matchesPlayedPerYear.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());
    }
}
