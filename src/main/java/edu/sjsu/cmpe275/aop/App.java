package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The Class App.
 */
public class App {
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

        try {
        	stats.resetStatsAndSystem();
        	/**tweeter.tweet("ram", "kjasekfhksdhfkshdfkhksdhfkh  jh afmnabfja dfcjbaf ajbcambcka c,bkanc,anc kaj c,anckca a kjandnakd akjbx,ambcka akjbcacbka c,abcka cabc ambcam ma");
        	tweeter.tweet("Alex","Anurag");
        	tweeter.follow("Alex", "Alex");
        	tweeter.follow("Carl", "Bob");
        	tweeter.follow("Bob", "Alex");
        	tweeter.follow("Bo", "Alex");
        	tweeter.block("Bob", "Alex");*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweetAttempted());
        System.out.println("Most unpopular follower " + stats.getMostBlockedFollower());
        ctx.close();
    }
}
