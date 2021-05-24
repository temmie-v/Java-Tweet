package proj1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public final class ListSearch {

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {

    	try {
    		long lis = list_id;
            String path = "filepath/list.csv";
    		File file = new File(path);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter
            						(new FileOutputStream(file),"Shift-JIS")));
            
            pw.write("ID,@Screen Name,Name,Following,Followers,Protected,Tweets,Likes,CreatedAt,location,description\n");
            System.out.println("Listing list : " + lis);
    	

            Twitter twitter = new TwitterFactory().getInstance();
            long cursor = -1;
            PagableResponseList<User> usres;
            do {
                usres = twitter.getUserListMembers(lis, cursor);
                for (User list : usres) {
               		String unit = "";
            		unit += Long.toString(list.getId());
            		unit += ",@";
  	    	    	unit += list.getScreenName();
  	    	    	unit += ",";
	    			unit += list.getName().replace(",", "");
	    			unit += ",";
	    			unit += list.getFriendsCount();
	    			unit += ",";
	    			unit += list.getFollowersCount();
	    			unit += ",";
	    			if (list.isProtected()) {
	    				unit += "Protected";
	    			}
	    			unit += ",";
	    			unit += list.getStatusesCount();
	    			unit += ",";
	    			unit += list.getFavouritesCount();
	    			unit += ",";
	    			unit += list.getCreatedAt();
	    			unit += ",";
	    			unit += list.getLocation().replaceAll(",", "");
	    			unit += ",";
	    			unit += list.getDescription().replaceAll("\n", "").replaceAll(",", "");

	    			unit += "\n";
	    			pw.write(unit);

                    System.out.println(unit);
                }
            } while ((cursor = usres.getNextCursor()) != 0);
            pw.close();
            System.out.println("\nSuccessfully finished listing. ->Desktop\\list.csv");
            System.exit(0);

    	} catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get list members: " + te.getMessage());
            System.exit(-1);
        }
    }
}
