# AOP-for-MiniTwitter

The tweet service is defined as follows:

package edu.sjsu.cmpe275.lab1;

import java.io.IOException;

public interface TweetService {
   /**
    * @throws IllegalArgumentException if the message is more than 140 characters as measured by string length.
    * @throws IOException if there is a network failure
    */
   void tweet(String user, String message) throws IllegalArgumentException, IOException;

   /**
    * @throws IOException if there is a network failure
    */
   void follow(String follower, String followee) throws IOException;

   /**
    * @throws IOException if there is a network failure
    */
   void block(String user, String followee) throws IOException;

}

Since network failure happens relatively frequently,we add the feature to automatically retry for up to three times for a network failure (indicated by an IOException). (Please note the three retries are in addition to the original failed invocation.) You are also asked to implement the following TweetStats service:

package edu.sjsu.cmpe275.lab1;

public interface TweetStats {
   
   /**
    * reset all the four measurements.
    */
   void resetStatsandSystem();
   
   /**
    * @return the length of longest message attempted since the beginning or last reset. Can be more than 140. If no messages were attempted, return 0.
    * Failed messages are counted for this as well.
    */
   int getLengthOfLongestTweetAttempted();
   /**
    * @return the user who has been followed by the biggest number of different users since the beginning or last reset. If there is a tie,
    * return the 1st of such users based on alphabetical order. If the follow action did not succeed, it does not count toward the stats. If no users were successfully followed, return null.  
    */
   String getMostFollowedUser();

   /**
    * @return the user who has been followed by the biggest number of user following attempts since the beginning or last reset. If there is a tie,
    * return the 1st of such users based on alphabetical order. If the follow action did not succeed, it does not count toward the stats. If no users were successfully followed, return null.  This includes any request that has reached the server, no matter it’s repeated or blocked or not.
    */
   String getMostFollowedUserByAttempts();

   /**
    * The most productive user is determined by the total length of all the messages successfully tweeted since the beginning
    * or last reset. If there is a tie, return the 1st of such users based on alphabetical order. If no users successfully tweeted, return null.
    * @return the most productive user.
    */
   String getMostProductiveUser();

/**
     * @return the user who has been blocked by the biggest number of
     *     	different users since the beginning or last reset. If there is a
     *     	tie, return the 1st of such users based on alphabetical order.
     *     	If no follower has been successfully blocked by anyone, return null.
     */
    String getMostBlockedFollower();
}
