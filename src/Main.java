import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        List<Match> matchesData = getMatchesData();
        List<Delivery> deliveryData = getDeliveryData();

        HashMap<String, Integer> matchesPlayedPerYear = getMatchesPlayedPerYear(matchesData);
        HashMap<String, Integer> noOfMatchesWonByAllTeams = getTheNoOfMatchesWOnByAllTeams(matchesData);
        HashMap<String, Integer> extraRunsPerTeamIn2016 = getExtraRunsPerTeamsIn2016(matchesData, deliveryData);
        HashMap<String, Integer> topEconomicalBowlers2015 = getTopEconomicalBowler(matchesData, deliveryData);
        HashMap<String, Integer> totalMatchesPlayedPlayedInEachCity = getTotalMatchesPlayedInEachCity(matchesData);

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
                printResultsInKeyValueFormat(noOfMatchesWonByAllTeams);
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
                List list = new LinkedList(topEconomicalBowlers2015.entrySet());
                Collections.sort(list, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
                    }
                });
                HashMap sortedHashMap = new LinkedHashMap();
                for (Iterator it = list.iterator(); it.hasNext(); ) {
                    Map.Entry entry = (Map.Entry) it.next();
                    sortedHashMap.put(entry.getKey(), entry.getValue());
                }
                printResultsInKeyValueFormat(sortedHashMap);
                break;
            }
            case 5: {
                System.out.println("City => Number Of Matches");
                printResultsInKeyValueFormat(totalMatchesPlayedPlayedInEachCity);
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

    private static HashMap<String, Integer> getTotalMatchesPlayedInEachCity(List<Match> matchesData) {
        HashMap<String, Integer> matchesPlayedPerCity = new HashMap<>();
        Iterator<Match> iterator = matchesData.iterator();
        while (iterator.hasNext()) {
            Match match = iterator.next();
            if (matchesPlayedPerCity.containsKey(match.getCity())) {
                matchesPlayedPerCity.put(match.getCity(), matchesPlayedPerCity.get(match.getCity()) + 1);
            } else {
                matchesPlayedPerCity.put(match.getCity(), 1);
            }
        }
        if (matchesPlayedPerCity.containsKey(""))   // if matchesPlayedPerCity contains empty key
            matchesPlayedPerCity.remove("");

        return matchesPlayedPerCity;
    }

    private static HashMap<String, Integer> getTopEconomicalBowler(List<Match> matchesData, List<Delivery> deliveryData) {
        HashMap<String, Integer> topEconomicalBowler = new HashMap<>();
        LinkedHashSet<String> MatchId = new LinkedHashSet<>();
        Iterator<Match> Matchiterator = matchesData.iterator();
        while (Matchiterator.hasNext()) {
            Match match = Matchiterator.next();
            if (match.getSeason().equals("2015")) {
                MatchId.add(match.getId());
            }
        }

        Iterator<Delivery> deliveryIterator = deliveryData.iterator();
        while (deliveryIterator.hasNext()) {
            Delivery delivery = deliveryIterator.next();
            if (MatchId.contains(delivery.getMatch_id())) {
                if (!topEconomicalBowler.containsKey(delivery.getBowler())) {
                    topEconomicalBowler.put(delivery.getBowler(), Integer.parseInt(delivery.getTotal_runs()));
                } else {
                    topEconomicalBowler.put(delivery.getBowler(), topEconomicalBowler.get(delivery.getBowler()) + Integer.parseInt(delivery.getTotal_runs()));
                }
            }
        }
        return topEconomicalBowler;
    }

    private static HashMap<String, Integer> getExtraRunsPerTeamsIn2016(List<Match> matchesData, List<Delivery> deliveryData) {
        HashMap<String, Integer> extraRunsPerTeamIn2016 = new HashMap<>();
        LinkedHashSet<String> MatchId = new LinkedHashSet<>();
        Iterator<Match> Matchiterator = matchesData.iterator();
        while (Matchiterator.hasNext()) {
            Match match = Matchiterator.next();
            if (match.getSeason().equals("2016")) {
                MatchId.add(match.getId());
            }
        }

        Iterator<Delivery> deliveryIterator = deliveryData.iterator();
        while (deliveryIterator.hasNext()) {
            Delivery delivery = deliveryIterator.next();
            if (MatchId.contains(delivery.getMatch_id())) {
                if (!extraRunsPerTeamIn2016.containsKey(delivery.getBatting_team())) {
                    extraRunsPerTeamIn2016.put(delivery.getBatting_team(), Integer.parseInt(delivery.getExtra_runs()));
                } else {
                    extraRunsPerTeamIn2016.put(delivery.getBatting_team(), extraRunsPerTeamIn2016.get(delivery.getBatting_team()) + Integer.parseInt(delivery.getExtra_runs()));
                }
            }
        }
        return extraRunsPerTeamIn2016;
    }


    private static List<Delivery> getDeliveryData() {
        final int MATCH_ID = 0, INNING = 1, BATTING_TEAM = 2, BOWLING_TEAM = 3, OVER = 4, BAlL = 5, BATSMAN = 6, NON_STRIKER = 7, BOWLER = 8, IS_SUPER_OVER = 9, WIDE_RUNS = 10, BYE_RUNS = 11, LEGBYE_RUNS = 12, NOBALL_RUNS = 13, PENALTY_RUNS = 14, BATSMAN_RUN = 15, EXTRA_RUNS = 16, TOTAL_RUNS = 17, PLAYER_DISMISSED = 18, DISSMISSAL_KIND = 19, FIELDER = 20;
        int counter = 0;
        String deliveryData;
        List<Delivery> deliveryList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/deliveries.csv"))) {
            while ((deliveryData = br.readLine()) != null) {
                String deliveryDataSplitted[] = deliveryData.split(",");
                Delivery delivery = new Delivery();
                delivery.setMatch_id(deliveryDataSplitted[MATCH_ID]);
                delivery.setInning(deliveryDataSplitted[INNING]);
                delivery.setBatting_team(deliveryDataSplitted[BATTING_TEAM]);
                delivery.setBowling_team(deliveryDataSplitted[BOWLING_TEAM]);
                delivery.setOver(deliveryDataSplitted[OVER]);
                delivery.setBall(deliveryDataSplitted[BAlL]);
                delivery.setBatsman(deliveryDataSplitted[BATSMAN]);
                delivery.setNon_striker(deliveryDataSplitted[NON_STRIKER]);
                delivery.setBowler(deliveryDataSplitted[BOWLER]);
                delivery.setIs_super_over(deliveryDataSplitted[IS_SUPER_OVER]);
                delivery.setWide_runs(deliveryDataSplitted[WIDE_RUNS]);
                delivery.setBye_runs(deliveryDataSplitted[BYE_RUNS]);
                delivery.setNoball_runs(deliveryDataSplitted[NOBALL_RUNS]);
                delivery.setLegbye_runs(deliveryDataSplitted[LEGBYE_RUNS]);
                delivery.setPenalty_runs(deliveryDataSplitted[PENALTY_RUNS]);
                delivery.setBatsman_runs(deliveryDataSplitted[BATSMAN_RUN]);
                delivery.setExtra_runs(deliveryDataSplitted[EXTRA_RUNS]);
                delivery.setTotal_runs(deliveryDataSplitted[TOTAL_RUNS]);
//                delivery.setPlayer_dismissed(deliveryDataSplitted[PLAYER_DISMISSED]);
//                delivery.setDismissal_kind(deliveryDataSplitted[DISSMISSAL_KIND]);
//                delivery.setFielder(deliveryDataSplitted[FIELDER]);
                deliveryList.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveryList;
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

    private static HashMap<String, Integer> getExtraRunsPerTeams(List<Match> matchesData) {

        return null;

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

}