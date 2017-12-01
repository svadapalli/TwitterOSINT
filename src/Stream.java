import java.io.FileWriter;
import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Stream {
    public static void main(String[] args) throws TwitterException {

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true);
    cb.setOAuthConsumerKey("Zk7Op8HmLNviGu2FFXx1O8bHU");
    cb.setOAuthConsumerSecret("6LveVG8pa49HHilQ0j2tqKR1s5vsOjk8TVvU9j55c6lmcs3w3i");
    cb.setOAuthAccessToken("782090653668896769-GnM8KNdMTwKRSU1pAUaL5ODcVGGyd97");
    cb.setOAuthAccessTokenSecret("BmMDBlpaVAlgcE4PZM3xzHPJBgxGzOEXRZtSABneSPbGj");

    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    StatusListener listener = new StatusListener() {

        public void onStatus(Status status) {
        	if(status.isRetweet() == false){
        		String text = status.getText();
        		String tweet = text.replaceAll("#", "").replaceAll("@", "").replaceAll("https?://\\S+\\s?", "");
        		JSONArray annotations = new JSONArray();
        		annotations = annotator.main(tweet);
        		JSONObject obj = new JSONObject();
        		try {
					obj.put("created_at", status.getCreatedAt());
					obj.put("screen_name", status.getUser().getScreenName());
					obj.put("text", status.getText());
					obj.put("annotations", annotations);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		//System.out.print(obj);
        		try {
					FileWriter file = new FileWriter("/root/Documents/Masters_Project/twitDB.json", true);
					file.write(obj.toString());
					file.write('\n');
					file.write('\n');
		            file.flush();
		            file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		//System.out.println("{\"created_at\":"+"\""+status.getCreatedAt()+"\""+", \"screen_name\":"+"\"" + status.getUser().getScreenName()+"\""+", \"text\":"+"\""+ status.getText()+"\""+", \n\"annotations\":"+ annotations+"}");
        	}
        }

        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        public void onScrubGeo(long userId, long upToStatusId) {
            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        public void onException(Exception ex) {
            ex.printStackTrace();
        }

		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO Auto-generated method stub
			
		}
    };

    FilterQuery fq = new FilterQuery();
    String keywords[] = {"malware"};
    String lang [] = {"en"};
    
    fq.language(lang);
    fq.track(keywords);

    twitterStream.addListener(listener);
    twitterStream.filter(fq);      
}
}