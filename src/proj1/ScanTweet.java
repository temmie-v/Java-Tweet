package proj1;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;

class ScanTweet {
    public static void main (String[] args) throws IOException{
        try {
            long myid = user_id;
			
            Twitter twitter = new TwitterFactory().getInstance();
            Paging page = new Paging();
            page.setCount(100);
            List <Status> statuses = twitter.getHomeTimeline(page);
            Collections.reverse(statuses);
            for (Status status : statuses) {
                String unit = "";
                unit += status.getUser().getName()
                    + " (@" + status.getUser().getScreenName()
                    + ")  FF : "
                    + status.getUser().getFriendsCount()
                    + " / "
                    + status.getUser().getFollowersCount();
                    if (status.getUser().isProtected()) {
                        unit += " π";
                    }
                    if (status.getUser().getId() == myid) {
                        unit += "  <me> ";
                    }
                unit += "\n"
                    + status.getCreatedAt() + "\n"
                    + status.getText() + "\n"
                    + "Likes : " + status.getFavoriteCount()
                    + ", RT : " + status.getRetweetCount() + "\n";
                System.out.println(unit);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        while (true) {
            Scanner input = new Scanner(System.in);
            String tweetString = "";
            System.out.println("\n-----");
            System.out.println("1θ‘γ§ε₯ε. ζΉθ‘γ―\\n (η΅δΊ:null -> -1)");
            String str = input.nextLine();
            int len = str.length();
            int recnt = 0;
            if (len > 300) {
                System.out.println("ε­ζ°θΆι");
	    } else {
                for (int i = 0; i < len - 1; i++) {
                    if (str.charAt(i) == '\\' && str.charAt(i + 1) == 'n') {
                        tweetString += str.substring(recnt, i) + '\n';
                        recnt = i + 2;
                    }
                }
                tweetString += str.substring(recnt);

                System.out.println(len + "ε­. γγ¬γγ₯γΌ...\n\t" + tweetString);
                System.out.print("ζ·»δ»γγη»ε(0~4ζ 0γ―ιδΏ‘ θ² γ?ε ΄εη΅δΊ/γγη΄γ) : ");
                int pic = input.nextInt();
                if (len == 0 && pic == -1) {
                    input.close();
                    System.gc();
                    System.out.println("η΅δΊ");
                    break;
                } else if (pic < 0) {
                    continue;
                }
                String[] address = new String[pic];
                if (pic > 0) {
                    for (int i = 0; i < pic; i++) {
                        JFileChooser chooser = new JFileChooser();
                        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            File selectedfile = chooser.getSelectedFile();
                            String id = selectedfile.getPath();
                            i++; System.out.println("No." + i + " : " + id); i--;
                            address[i] = id;
                        } else {
                            System.out.println("εεΎε€±ζ. ζ·»δ»γγΎγγ");
                            pic = 0;
                            //break tweet;
                        }
                    }
                    System.out.print("ζη¨Ώγγ (Yes-1 / No-0) : ");
                    int send = input.nextInt();
                    if (send == 0) {
                        continue;
                    }
                }
                try {
                    Twitter twitter = new TwitterFactory().getInstance();
                    StatusUpdate update = new StatusUpdate(tweetString);
                    if (pic > 0) {
                        long[] setID = new long[pic];
                        for (int i = 0; i < pic; i++) {
                            UploadedMedia media = twitter.uploadMedia(new File(address[i]));
                            setID[i] = media.getMediaId();
                        }
                        update.setMediaIds(setID);
                    }
                    Status status = twitter.updateStatus(update);
                    System.out.println("... Tweeted at " + status.getCreatedAt());
                    System.out.println(status.getUser().getStatusesCount() + " -th Tweet.");
                } catch (TwitterException e) {
                    e.printStackTrace();
                    System.out.println("Tweetγγη΅δΊγγΎγγ");
                }
            }
            System.out.println("ζ¬‘γ?TweetγηζγγΎγγ");
        }
    }
}
