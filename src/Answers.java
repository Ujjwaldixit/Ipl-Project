import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Answers {
    //Answer1
    public HashMap<String,Integer> NumberOfMatchesPlayedPerYearOfAllYears()
    {
        String row;
        HashMap<String,Integer> AllYearsMatches=new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv")))
        {
            while ((row = br.readLine()) != null)
            {
                String dataSet[]= row.split(",");
                if(AllYearsMatches.containsKey(dataSet[1]))
                {
                    AllYearsMatches.put(dataSet[1], AllYearsMatches.get(dataSet[1]) + 1);
                }
                else{
                    AllYearsMatches.put(dataSet[1],1);
                }
                
            }
            System.out.println(AllYearsMatches);
        }

        catch(Exception e)
        {
            e.getMessage();
        }
        return AllYearsMatches;
    }




    //Answer2
    public void matchesWonOfAllTeams() {
        String row;
        HashMap<String, Integer> AllYearsMatches = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv"))) {
            while ((row = br.readLine()) != null) {
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
            System.out.println(AllYearsMatches);
        } catch (Exception e) {
            e.getMessage();
        }
    }




    //Answer3
    public void ExtraRunsConcededPerTeamOf2016() {
        String row;
        HashMap<String, Integer> extraRuns2016 = new HashMap<>();
        LinkedHashSet<String> MatchId = new LinkedHashSet<>();
        int counter = 0;   // to skip first row
        try (BufferedReader br = new BufferedReader(new FileReader("/home/kali/IdeaProjects/IPL_Project/matches.csv")))
        {
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
                String dataSet[]=row.split(",");
                if(MatchId.contains(dataSet[0]))
                {
                    System.out.println("da"+dataSet[0]);
                    if(!extraRuns2016.containsKey(dataSet[2]))
                    {
                        extraRuns2016.put(dataSet[2],0);
                    }
                    else{
                        extraRuns2016.put(dataSet[2],extraRuns2016.get(dataSet[2])+Integer.parseInt(dataSet[16]));
                    }
                }
            }
            System.out.println(extraRuns2016);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
