import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        List<Match> matchesData = getMatchesData();
        List<Delivery> deliveryData = getDeliveryData();

        HashMap<String, Integer> matchesPlayedPerYear = getMatchesPlayedPerYear(matchesData);
        HashMap<String, Integer> teamsPerMatchesWon = getMatchesWonPerTeam(matchesData);
        HashMap<String, Integer> extraRunsPerTeamIn2016 = getExtraRunsPerTeamsIn2016(matchesData, deliveryData);
        HashMap<String, Integer> bowlerPerRunIn2015 = getEconomicalBowlersPerRunGivenIn2015(matchesData, deliveryData);
        HashMap<String, Integer> totalMatchesPerCity = getTotalMatchesPlayedPerCity(matchesData);

        System.out.println("Press 1: Number of matches played per year of all the years in IPL.");
        System.out.println("Press 2: Number of matches won of all teams over all the years of IPL.");
        System.out.println("Press 3: For the year 2016 get the extra runs conceded per team.");
        System.out.println("Press 4:For the year 2015 get the top economical bowlers");
        System.out.println("Press 5:Total No. of Matches Played in Every City");

        Scanner scanner = new Scanner(System.in); // take input to print result
        int input = scanner.nextInt();
        switch (input) {
            case 1: {
                System.out.println("Season => Total Number of Matches");
                printResultsInKeyValueFormat(matchesPlayedPerYear);
                break;
            }
            case 2: {
                System.out.println("Team => Total Matches played");
                printResultsInKeyValueFormat(teamsPerMatchesWon);
                break;
            }
            case 3: {
                System.out.println("Team => Extra Runs");
                printResultsInKeyValueFormat(extraRunsPerTeamIn2016);
                break;
            }
            case 4: {
                System.out.println("Bowler => Runs Given");
                //comparing Runs to sort the map
                List runsGivenByEachBowler = new LinkedList(bowlerPerRunIn2015.entrySet());
                Collections.sort(runsGivenByEachBowler, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
                    }
                });
                HashMap bowlerPerRunGiven = new LinkedHashMap();
                for (Iterator it = runsGivenByEachBowler.iterator(); it.hasNext(); ) {
                    Map.Entry entry = (Map.Entry) it.next();
                    bowlerPerRunGiven.put(entry.getKey(), entry.getValue());
                }
                printResultsInKeyValueFormat(bowlerPerRunGiven);
                break;
            }
            case 5: {
                System.out.println("City => Number Of Matches");
                printResultsInKeyValueFormat(totalMatchesPerCity);
                break;
            }
        }
    }

    private static void printResultsInKeyValueFormat(HashMap<String, Integer> results) {
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
        System.out.println("\n");
    }

    private static HashMap<String, Integer> getTotalMatchesPlayedPerCity(List<Match> matchesData) {
        HashMap<String, Integer> totalMatchesPlayedPerCity = new HashMap<>();
        Iterator<Match> matchData = matchesData.iterator();
        while (matchData.hasNext()) {
            Match match = matchData.next();
            if (totalMatchesPlayedPerCity.containsKey(match.getCity())) {
                totalMatchesPlayedPerCity.put(match.getCity(), totalMatchesPlayedPerCity.get(match.getCity()) + 1);
            } else {
                totalMatchesPlayedPerCity.put(match.getCity(), 1);
            }
        }
        if (totalMatchesPlayedPerCity.containsKey(""))   // if totalMatchesPlayedPerCity contains empty key
            totalMatchesPlayedPerCity.remove("");

        return totalMatchesPlayedPerCity;
    }

    private static HashMap<String, Integer> getEconomicalBowlersPerRunGivenIn2015(List<Match> matchesData, List<Delivery> deliveriesData) {
        HashMap<String, Integer> bowlerPerRunGiven = new HashMap<>();
        LinkedHashSet<String> matchIds = new LinkedHashSet<>();
        Iterator<Match> matchData = matchesData.iterator();
        while (matchData.hasNext()) {
            Match match = matchData.next();
            if (match.getSeason().equals("2015")) {
                matchIds.add(match.getId());
            }
        }

        Iterator<Delivery> deliveryData = deliveriesData.iterator();
        while (deliveryData.hasNext()) {
            Delivery delivery = deliveryData.next();
            if (matchIds.contains(delivery.getMatch_id())) {
                if (!bowlerPerRunGiven.containsKey(delivery.getBowler())) {
                    bowlerPerRunGiven.put(delivery.getBowler(), Integer.parseInt(delivery.getTotal_runs()));
                } else {
                    bowlerPerRunGiven.put(delivery.getBowler(), bowlerPerRunGiven.get(delivery.getBowler())
                            + Integer.parseInt(delivery.getTotal_runs()));
                }
            }
        }
        return bowlerPerRunGiven;
    }

    private static HashMap<String, Integer> getExtraRunsPerTeamsIn2016(List<Match> matchesData, List<Delivery> deliveriesData) {
        HashMap<String, Integer> teamPerExtraRuns = new HashMap<>();
        LinkedHashSet<String> matchIds = new LinkedHashSet<>();
        Iterator<Match> matchData = matchesData.iterator();
        while (matchData.hasNext()) {
            Match match = matchData.next();
            if (match.getSeason().equals("2016")) {
                matchIds.add(match.getId());
            }
        }

        Iterator<Delivery> deliveryData = deliveriesData.iterator();
        while (deliveryData.hasNext()) {
            Delivery delivery = deliveryData.next();
            if (matchIds.contains(delivery.getMatch_id())) {
                if (!teamPerExtraRuns.containsKey(delivery.getBatting_team())) {
                    teamPerExtraRuns.put(delivery.getBatting_team(), Integer.parseInt(delivery.getExtra_runs()));
                } else {
                    teamPerExtraRuns.put(delivery.getBatting_team(), teamPerExtraRuns.get(delivery.getBatting_team())
                            + Integer.parseInt(delivery.getExtra_runs()));
                }
            }
        }
        return teamPerExtraRuns;
    }


    private static List<Delivery> getDeliveryData() {
        final int MATCH_ID = 0, INNING = 1, BATTING_TEAM = 2, BOWLING_TEAM = 3, OVER = 4, BAlL = 5, BATSMAN = 6,
                NON_STRIKER = 7, BOWLER = 8, IS_SUPER_OVER = 9, WIDE_RUNS = 10, BYE_RUNS = 11, LEGBYE_RUNS = 12,
                NOBALL_RUNS = 13, PENALTY_RUNS = 14, BATSMAN_RUN = 15, EXTRA_RUNS = 16, TOTAL_RUNS = 17,
                PLAYER_DISMISSED = 18, DISMISSAL_KIND = 19, FIELDER = 20;
        String deliveryData;
        List<Delivery> deliveryDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/deliveries.csv"))) {
            while ((deliveryData = br.readLine()) != null) {
                String deliveryDataColumn[] = deliveryData.split(","); //split delivery data by ","(comma)
                Delivery delivery = new Delivery();
                delivery.setMatch_id(deliveryDataColumn[MATCH_ID]);
                delivery.setInning(deliveryDataColumn[INNING]);
                delivery.setBatting_team(deliveryDataColumn[BATTING_TEAM]);
                delivery.setBowling_team(deliveryDataColumn[BOWLING_TEAM]);
                delivery.setOver(deliveryDataColumn[OVER]);
                delivery.setBall(deliveryDataColumn[BAlL]);
                delivery.setBatsman(deliveryDataColumn[BATSMAN]);
                delivery.setNon_striker(deliveryDataColumn[NON_STRIKER]);
                delivery.setBowler(deliveryDataColumn[BOWLER]);
                delivery.setIs_super_over(deliveryDataColumn[IS_SUPER_OVER]);
                delivery.setWide_runs(deliveryDataColumn[WIDE_RUNS]);
                delivery.setBye_runs(deliveryDataColumn[BYE_RUNS]);
                delivery.setNoball_runs(deliveryDataColumn[NOBALL_RUNS]);
                delivery.setLegbye_runs(deliveryDataColumn[LEGBYE_RUNS]);
                delivery.setPenalty_runs(deliveryDataColumn[PENALTY_RUNS]);
                delivery.setBatsman_runs(deliveryDataColumn[BATSMAN_RUN]);
                delivery.setExtra_runs(deliveryDataColumn[EXTRA_RUNS]);
                delivery.setTotal_runs(deliveryDataColumn[TOTAL_RUNS]);
//                delivery.setPlayer_dismissed(deliveryDataColumn[PLAYER_DISMISSED]);
//                delivery.setDismissal_kind(deliveryDataColumn[DISMISSAL_KIND]);
//                delivery.setFielder(deliveryDataColumn[FIELDER]);
                deliveryDataList.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveryDataList;
    }


    private static List<Match> getMatchesData() {
        final int ID = 0, SEASON = 1, CITY = 2, DATE = 3, TEAM1 = 4, TEAM2 = 5, TOSS_WINNER = 6, TOSS_DECISION = 7,
                RESULT = 8, DL_APPLIED = 9, WINNER = 10, WIN_BY_RUNS = 11, WIN_BY_WICKETS = 12, PLAYER_OF_MATCH = 13,
                VENUE = 14, UMPIRE1 = 15, UMPIRE2 = 16, UMPIRE3 = 17;
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
                String matchDataColumn[] = matchData.split(","); // splitting matches.csv data by ","(comma)
                match.setId(matchDataColumn[ID]);
                match.setSeason(matchDataColumn[SEASON]);
                match.setCity(matchDataColumn[CITY]);
                match.setDate(matchDataColumn[TEAM1]);
                match.setTeam1(matchDataColumn[TEAM2]);
                match.setTeam2(matchDataColumn[TOSS_WINNER]);
                match.setToss_decision(matchDataColumn[TOSS_DECISION]);
                match.setWinner(matchDataColumn[WINNER]);
                matchDataList.add(match);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return matchDataList;
    }


    private static HashMap<String, Integer> getMatchesWonPerTeam(List<Match> matchesData) {
        HashMap<String, Integer> matchesWonPerTeam = new HashMap<>();
        Iterator<Match> iterator = matchesData.iterator();
        while (iterator.hasNext()) {
            Match match = iterator.next();
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
}
