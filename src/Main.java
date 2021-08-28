import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String args[])
    {
        HashMap<String,Integer> data;
       Answers answers=new Answers();
//       data=answers.NumberOfMatchesPlayedPerYearOfAllYears();
//        System.out.println("Year"+"       "+"No.of Matches");
//        for (Map.Entry<String, Integer> entry : data.entrySet()) {
//            System.out.println(entry.getKey()+"------->"+entry.getValue().toString());
//        }

        answers.ExtraRunsConcededPerTeamOf2016();

    }
}
