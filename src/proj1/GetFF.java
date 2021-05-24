package proj1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public final class GetFF {
    
	public static void main(String[] args) throws TwitterException, IOException {

		Calendar time = Calendar.getInstance();
        String path = "filepath/log.csv";
		File file = new File(path);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"Shift-JIS")));

	    long myid = user_id;
		
        pw.write(time.getTime() + ",user_name,@screen_name\n");
        pw.write("\n\nFollowers\n");
        pw.write("ID,@Screen Name,Name,Following,Followers,Protected,Tweets,Likes,CreatedAt,location,description\n");
        System.out.println("Listing followers.");
		listFers(myid, pw);
		
		pw.write("\n\n\nFollowings\n");
        pw.write("ID,@Screen Name,Name,Following,Followers,Protected,Tweets,Likes,CreatedAt,location,description\n");
        System.out.println("\nListing followings.");
        listFings(myid, pw);
		
        pw.close();
        System.out.println("\nSuccessfully finished listing. -> log.csv");
        System.exit(0);
	}



    private static void listFers(long myid, PrintWriter fw) throws IOException {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            long cursor = -1;
            IDs ids;

            do {
            	ids = twitter.getFollowersIDs(myid, cursor);
            	for (long id : ids.getIDs()) {
            		String unit = "";
            		unit += Long.toString(id);
            		unit += ",@";
            		twitter4j.User u = twitter.showUser(id);
            		unit += makeUnit(u);
            		unit += "\n";
                    fw.write(unit);
            		System.out.println(unit);
                }
            } while ((cursor = ids.getNextCursor()) != 0);
            fw.write("...total," + twitter.showUser(myid).getFollowersCount());

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            System.exit(-1);
        }
    }


    private static void listFings(long myid, PrintWriter fw) throws IOException {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            long cursor = -1;
            IDs ids;
            do {
            	ids = twitter.getFollowersIDs(myid, cursor);
            	for (long id : ids.getIDs()) {
               		String unit = "";
            		unit += Long.toString(id);
            		unit += ",@";
            		twitter4j.User u = twitter.showUser(id);

            		unit += makeUnit(u);

            		unit += "\n";

                    fw.write(unit);
            		System.out.println(unit);
                }
            } while ((cursor = ids.getNextCursor()) != 0);

            fw.write("...total," + twitter.showUser(myid).getFriendsCount());

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followings' ids: " + te.getMessage());
            System.exit(-1);
        }
    }

    private static String makeUnit(twitter4j.User u) {
    	String ret = "";
		//ID,@Screen Name,Name,Following,Followers,Protected,Tweets,Likes,CreatedAt,location,description

    	ret += u.getScreenName();
		ret += ",";
		ret += u.getName().replace(",", "");
		ret += ",";
		ret += u.getFriendsCount();
		ret += ",";
		ret += u.getFollowersCount();
		ret += ",";
		if (u.isProtected()) {
			ret += "Protected";
		}
		ret += ",";
		ret += u.getStatusesCount();
		ret += ",";
		ret += u.getFavouritesCount();
		ret += ",";
		ret += u.getCreatedAt();
		ret += ",";
		ret += u.getLocation().replaceAll(",", "");
		ret += ",";
		ret += u.getDescription().replaceAll("\n", "").replaceAll(",", "");

		return ret;
    }
    
}
