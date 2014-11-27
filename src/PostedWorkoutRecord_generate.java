// Generate dummy data for GymBuddy database
// For table PostedWorkoutRecord
// @author Pengyi Pan
//
//


import java.util.Random;

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
	
	private static int numToGenerate = 50;
	
	public static void main(String[] args){
		generate();
	}
	
	private static void generate(){
		String pre = "INSERT INTO `PostedWorkoutRecord2`(`time_start`, `time_end`, `location`, `sport_type`, `sport_sub_type`) VALUES";
		System.out.println(pre);
		generateValue();
	}
	
	private static void generateValue(){
		
		for(int i=0;i<numToGenerate;i++){
			String[] dates = randomDateTime();
			String startTime = "'"+dates[0]+"'";
			String endTime = "'"+dates[1]+"'";
			String loc = "'"+locations[randIntIncludeMax(locations.length)]+"'";
			String sport = "'"+sportTypes[randIntIncludeMax(sportTypes.length)]+"'";
			String sportSub = "'"+sportSubTypes[randIntIncludeMax(sportSubTypes.length)]+"'";
			String out = "(" + startTime +","+ endTime +","+ loc +","+ sport +","+ sportSub + ")";
			if(i==numToGenerate-1){
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
		int maxPeriodInHours = 5;

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
