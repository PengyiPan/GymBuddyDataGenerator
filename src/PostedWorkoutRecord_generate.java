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
    private static String[] fmprofilePictures = {"default picture", "doge", "female1","female2","female3","female4","a1","a2","a3","a4","a5"};
    private static String[] mprofilePictures = {"default picture","doge","male1","male2","male3","male4","male5","male6","a1","a2","a3","a4","a5"};

    private static String[] Last_Names = {"Lee","Luu","James","Cox","Stewart","Smith","John","Williams"};
    private static String[] First_Names = {"Bob","Jill","Tom","Brandon","Roy","Peter","Jenny","Sarah","Vivian","Sandy","Irene"};


    public static void main(String[] args){

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the num of users needed: ");
//        int numUserToGenerate = scanner.nextInt();
//
//        System.out.println("Enter the num of records needed: ");
//        int numRecordToGenerate = scanner.nextInt();


        int numUserToGenerate = 80;
        int numRecordToGenerate = 600;


		generate(numUserToGenerate,numRecordToGenerate);
	}
	
	private static void generate(int numUserToGenerate, int numRecordToGenerate){



        ArrayList<String> user_id = generateUser(numUserToGenerate);


        String pre = "INSERT INTO `PostedWorkoutRecord2`(`time_start`, `time_end`, `location`, `sport_type`, `sport_sub_type`) VALUES";
        System.out.println(pre);
        generateRecord(numRecordToGenerate,user_id);




	}


	private static ArrayList<String> generateUser(int numUserToGenerate){
        ArrayList<String> user_id = new ArrayList<String>();
        String query_adduser = "INSERT INTO `User`(`net_id`, `password`, `last_name`, `first_name`, `gender`,`picture_url`,`num_thumbs`,`signature`) VALUES";
        System.out.println(query_adduser);

        //for(int i=0;i<numUserToGenerate;i++)
        int i = 0;
        while(i<numUserToGenerate){
            Random r = new Random();
            int i1 = r.nextInt(123 - 97) + 97;
            int i2 = r.nextInt(123 - 97) + 97;
            int i3 = r.nextInt(600);
            char ch1 = (char) i1;
            char ch2 = (char) i2;

            String net_id = "'"+ ch1+""+ch2+ Integer.toString(i3)+ "'";
            //System.out.println(net_id);

            if (!user_id.contains(net_id)){
                user_id.add(net_id);

                i++;

                int index_f = new Random().nextInt(First_Names.length);
                int index_l = new Random().nextInt(Last_Names.length);


                String password = "'hjknv45321'";
                String last_name = "'"+Last_Names[index_l]+ "'";
                String first_name = "'"+First_Names[index_f]+ "'";
                String gender;
                String picture_url;
                if (index_f < 1/2*First_Names.length){
                    gender = "'male'";
                    picture_url = "'"+mprofilePictures[new Random().nextInt(mprofilePictures.length)]+"'";
                } else {
                    gender = "'female'";
                    picture_url = "'"+fmprofilePictures[new Random().nextInt(fmprofilePictures.length)]+"'";
                }
                String num_thumbs = "'"+new Random().nextInt(99)+"'";
                String signature = "'"+"My name is "+First_Names[index_f] +". Contact me at 919-647-xxxx.'";



                String out = "(" + net_id + "," + password + "," + last_name + "," + first_name + "," + gender + "," + picture_url + "," + num_thumbs + "," + signature + ")";

                if(i==numUserToGenerate){
                    System.out.print(out);
                    System.out.println("\n\n");
                }else{
                    System.out.print(out);
                    System.out.print(",");
                    System.out.println();
                }
            }

        }
        return user_id;
    }

	private static void generateRecord(int numRecordToGenerate,ArrayList<String> user_id){

        ArrayList<String> postedBy = new ArrayList<String>();

		for(int i=0;i<numRecordToGenerate;i++){
			String[] dates = randomDateTime();
			String startTime = "'"+dates[0]+"'";
			String endTime = "'"+dates[1]+"'";
			String loc = "'"+locations[randIntIncludeMax(locations.length)]+"'";
			String sport = "'"+sportTypes[randIntIncludeMax(sportTypes.length)]+"'";
			String sportSub = "'"+sportSubTypes[randIntIncludeMax(sportSubTypes.length)]+"'";
			String out = "(" + startTime +","+ endTime +","+ loc +","+ sport +","+ sportSub + ")";

            //generate corresponding postedby entry for this record
            String by_id =  user_id.get(new Random().nextInt(user_id.size()));
            String postTime = dates[2];
            String pb = "("+ by_id+ ",'"+ (i+1) + "','"+ postTime + "')";
            postedBy.add(pb);

            if(i==numRecordToGenerate-1){
				System.out.print(out);
				System.out.println("\n\n");
			}else{
				System.out.print(out);
				System.out.print(",");
				System.out.println();
			}					
			
		}

        String pre = "INSERT INTO `PostedBy`(`net_id`, `record_id`, `time_posted`) VALUES";
        System.out.println(pre);
        for (int i = 0; i< postedBy.size();i++){
            System.out.print(postedBy.get(i));
            if (i == postedBy.size()-1){
                System.out.println();
            } else {
                System.out.println(",");
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

        Seconds randomPeriod2 = Seconds.seconds(random.nextInt(3*maximumPeriodInSeconds));
        DateTime postTime = startTime.minus(randomPeriod2);

		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		return new String[]{dateTimeFormatter.print(startTime),dateTimeFormatter.print(endTime),dateTimeFormatter.print(postTime)};

	}
}
