// Generate dummy data for GymBuddy database
// For table PostedWorkoutRecord
// @author Pengyi Pan
//
//


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class PostedWorkoutRecord_generate {
	
	private static String[] sportTypes = {"Swimming", "Basketball", "Tennis","Workout","Running","Soccer","Other" };
	private static String[] sportSubTypes = {"Pro","Advanced","Intermediate","Beginner","Other"};
	private static String[] locations = {"East","Central","West","Other"};
    private static String[] profilePictures = {"default picture", "doge", "female1","female2","female3","female4","male1","male2","male3","male4","male5","male6"};


    public static void main(String[] args){

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the num of users needed: ");
//        int numUserToGenerate = scanner.nextInt();
//
//        System.out.println("Enter the num of records needed: ");
//        int numRecordToGenerate = scanner.nextInt();


        int numUserToGenerate = 100;
        int numRecordToGenerate = 10;


		generate(numUserToGenerate,numRecordToGenerate);
	}
	
	private static void generate(int numUserToGenerate, int numRecordToGenerate){



        ArrayList<String> user_id = generateUser(numUserToGenerate);


        String pre = "INSERT INTO `PostedWorkoutRecord2`(`time_start`, `time_end`, `location`, `sport_type`, `sport_sub_type`) VALUES";
        System.out.println(pre);
        generateRecord(numRecordToGenerate);




	}


	private static ArrayList<String> generateUser(int numUserToGenerate){
        ArrayList<String> user_id = new ArrayList<String>();
        String query_adduser = "INSERT INTO `User`(`net_id`, `password`, `last_name`, `first_name`, `gender`,`picture_url`,`num_thumbs`,`signature`) VALUES";
        System.out.println(query_adduser);

        for(int i=0;i<numUserToGenerate;i++){
            Random r = new Random();
            int i1 = r.nextInt(123 - 97) + 97;
            int i2 = r.nextInt(123 - 97) + 97;
            int i3 = r.nextInt(600);
            char ch1 = (char) i1;
            char ch2 = (char) i2;


            String net_id = ch1+""+ch2+ "";
            System.out.println(net_id);
      


        }
        

        return user_id;

    }

	private static void generateRecord(int numRecordToGenerate){
		
		for(int i=0;i<numRecordToGenerate;i++){
			String[] dates = randomDateTime();
			String startTime = "'"+dates[0]+"'";
			String endTime = "'"+dates[1]+"'";
			String loc = "'"+locations[randIntIncludeMax(locations.length)]+"'";
			String sport = "'"+sportTypes[randIntIncludeMax(sportTypes.length)]+"'";
			String sportSub = "'"+sportSubTypes[randIntIncludeMax(sportSubTypes.length)]+"'";
			String out = "(" + startTime +","+ endTime +","+ loc +","+ sport +","+ sportSub + ")";
			if(i==numRecordToGenerate-1){
				System.out.print(out);
				System.out.println();
			}else{
				System.out.print(out);
				System.out.print(",");
				System.out.println();
			}					
			
		}		

	}
	
	public static int randIntIncludeMax(int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt(max);

	    return randomNum;
	}




	//using Joda.org
	public static String[] randomDateTime() {
		Random random = new Random();
		int minPeriodInMins = 30;
		int maxPeriodInHours = 3;

		DateTime startTime = new DateTime(System.currentTimeMillis() + random.nextInt()).withMillisOfSecond(0);
		startTime.withYear(2014);

		Minutes minimumPeriod = Minutes.minutes(minPeriodInMins);
		int minimumPeriodInSeconds = minimumPeriod.toStandardSeconds().getSeconds();
		int maximumPeriodInSeconds = Hours.hours(maxPeriodInHours).toStandardSeconds().getSeconds();

		Seconds randomPeriod = Seconds.seconds(random.nextInt(maximumPeriodInSeconds - minimumPeriodInSeconds));
		DateTime endTime = startTime.plus(minimumPeriod).plus(randomPeriod);
		endTime.withYear(2014);

		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		return new String[]{dateTimeFormatter.print(startTime),dateTimeFormatter.print(endTime)};

	}
}
