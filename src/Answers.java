import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Answers {
    //Answer1
    public HashMap<String, Integer> NumberOfMatchesPlayedPerYearOfAllYears() {
        String row;
        HashMap<String, Integer> AllYearsMatches = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((row = br.readLine()) != null) {
                String dataSet[] = row.split(",");
                if (AllYearsMatches.containsKey(dataSet[1])) {
                    AllYearsMatches.put(dataSet[1], AllYearsMatches.get(dataSet[1]) + 1);
                } else {
                    AllYearsMatches.put(dataSet[1], 1);
                }

            }
            System.out.println(AllYearsMatches);
        } catch (Exception e) {
            e.getMessage();
        }
        return AllYearsMatches;
    }


    //Answer2
    public HashMap matchesWonOfAllTeams() {
        String row;
        int counter = 0;
        HashMap<String, Integer> AllYearsMatches = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((row = br.readLine()) != null) {
                if (counter == 0) {
                    counter++;
                    continue;
                }
                String dataSet[] = row.split(",");
                if (!AllYearsMatches.containsKey(dataSet[4])) {
                    AllYearsMatches.put(dataSet[4], 0);
                }
                if (!AllYearsMatches.containsKey(dataSet[5])) {
                    AllYearsMatches.put(dataSet[5], 0);
                }
                if (!AllYearsMatches.containsKey(dataSet[10])) {
                    AllYearsMatches.put(dataSet[10], 0);
                } else {
                    AllYearsMatches.put(dataSet[10], AllYearsMatches.get(dataSet[10]) + 1);
                }
            }
            if (AllYearsMatches.containsKey("")) {
                AllYearsMatches.remove(""); //some blank key was there
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return AllYearsMatches;
    }


    //Answer3
    public HashMap<String, Integer> ExtraRunsConcededPerTeamOf2016() {
        String row;
        HashMap<String, Integer> extraRuns2016 = new HashMap<>();
        LinkedHashSet<String> MatchId = new LinkedHashSet<>();
        int counter = 0;   // to skip first row
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((row = br.readLine()) != null) {
                String dataSet[] = row.split(",");
                //storing ID to set so that we can retrieve the data of corresponding from deliveries.csv
                if (dataSet[1].equals("2016")) {
                    MatchId.add(dataSet[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // retrieving data of corresponding matchId
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/deliveries.csv"))) {
            while ((row = br.readLine()) != null) {
                String dataSet[] = row.split(",");
                if (MatchId.contains(dataSet[0])) {
                    //System.out.println("da"+dataSet[0]);
                    if (!extraRuns2016.containsKey(dataSet[2])) {
                        extraRuns2016.put(dataSet[2], Integer.parseInt(dataSet[16]));
                    } else {
                        extraRuns2016.put(dataSet[2], extraRuns2016.get(dataSet[2]) + Integer.parseInt(dataSet[16]));
                    }
                }
            }
            //System.out.println(extraRuns2016);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extraRuns2016;
    }


    //Answer4
    public HashMap<String, Integer> topEconomicalBowler2015() {
        String row;
        LinkedHashSet<String> MatchId = new LinkedHashSet<>();
        int counter = 0;   // to skip first row
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((row = br.readLine()) != null) {
                String dataSet[] = row.split(",");

                //storing ID to set so that we can retrieve the data of corresponding from deliveries.csv
                if (dataSet[1].equals("2015")) {
                    MatchId.add(dataSet[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, Integer> economicalBowler = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/deliveries.csv"))) {
            while ((row = br.readLine()) != null) {
                String dataSet[] = row.split(",");
                if (MatchId.contains(dataSet[0])) {
                    //System.out.println("da"+dataSet[0]);
                    if (!economicalBowler.containsKey(dataSet[8])) {
                        economicalBowler.put(dataSet[8], Integer.parseInt(dataSet[17]));
                    } else {
                        economicalBowler.put(dataSet[8], economicalBowler.get(dataSet[8]) + Integer.parseInt(dataSet[17]));
                    }
                }
            }
            //  System.out.println(economicalBowler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return economicalBowler;
    }


    //Answer 5
    public HashMap<String, Integer> TotalMatchesPlayedInEveryCity() {
        String row;
        HashMap<String, Integer> Cities = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((row = br.readLine()) != null) {
                String dataSet[] = row.split(",");
                if (Cities.containsKey(dataSet[2])) {
                    Cities.put(dataSet[2], Cities.get(dataSet[2]) + 1);
                } else {
                    Cities.put(dataSet[2], 1);
                }

            }
            if (Cities.containsKey(""))
                Cities.remove("");
            //System.out.println(Cities);
        } catch (Exception e) {
            e.getMessage();
        }
        return Cities;
    }
}
