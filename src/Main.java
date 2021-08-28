import java.util.*;

public class Main {
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        HashMap<String,Integer> data;
       Answers answers=new Answers();
       answers.matchesWonOfAllTeams();

       System.out.println("Press 1: Number of matches played per year of all the years in IPL.");
       System.out.println("Press 2: Number of matches won of all teams over all the years of IPL.") ;
       System.out.println("Press 3: For the year 2016 get the extra runs conceded per team.");
       System.out.println("Press 4:For the year 2015 get the top economical bowlers");
       System.out.println("Press 5:Total No. of Matches Played in Every City");
       int input=sc.nextInt();
        switch (input)
       {
           case 1: {
               data = answers.NumberOfMatchesPlayedPerYearOfAllYears();
               System.out.println("Year" + "                 " + "No.of Matches");
               for (Map.Entry<String, Integer> entry : data.entrySet()) {

                   System.out.println(entry.getKey() + "------->" + entry.getValue().toString());
               }
               break;
           }
           case 2:
           {
               data = answers.matchesWonOfAllTeams();
               System.out.println("Teams"+"                       "+"Match Won");
               for (Map.Entry<String, Integer> entry : data.entrySet()) {
                   System.out.println("| "+entry.getKey() + " |        | " + entry.getValue().toString()+" |");
               }
               break;
           }

        case 3: {
            data = answers.ExtraRunsConcededPerTeamOf2016();
            System.out.println("Team"+"                       "+"Extra Run");
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                System.out.println("| "+entry.getKey() + " |        | " + entry.getValue().toString()+" |");
            }
            break;
        }

           case 4:{
               String arr[]=new String[1000];
               Arrays.fill(arr,"");
               data = answers.topEconomicalBowler2015();
               System.out.println("Players(Bowlers)"+"            "+"Runs Given");
               for (Map.Entry<String, Integer> entry : data.entrySet()) {
                   arr[entry.getValue()]=entry.getKey();
                   //System.out.println("| "+entry.getKey() + " |        | " + entry.getValue().toString()+" |");
               }
               for(int i=0;i<arr.length;i++)
               {
                  if(arr[i]!="")
                  {
                      System.out.println("| "+arr[i] + " |        | " +i+" |");
                  }
               }
               break;
           }

           case 5:
           {
               data=answers.TotalMatchesPlayedInEveryCity();
               System.out.println("City"+"                   "+" No of Matches");
               for (Map.Entry<String, Integer> entry : data.entrySet()) {
                   System.out.println("| "+entry.getKey() + " |        | " + entry.getValue().toString()+" |");
               }
           }
        }
    }
}
