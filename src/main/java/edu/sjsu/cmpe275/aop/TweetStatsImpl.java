package edu.sjsu.cmpe275.aop;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TweetStatsImpl implements TweetStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	public static  int lengthOflongestTweet=0;
	public static String mostFollowedUser=null;
	public static String mostProductiveUser=null;
	public static String mostBlockedUser=null;
	
	public static Map<String, Integer> mostProductiveUserTreeMap=new TreeMap<String, Integer>();
	public static Map<String, Set<String>> mostFollowedUserTreeMap=new TreeMap<String, Set<String>>();
	public static Map<String,Set<String>> mostBlockedUserTreeMap=new TreeMap<String,Set<String>>();
	
	@Override
	public void resetStatsAndSystem() {
		System.out.println("Resetting all stats");
		
		lengthOflongestTweet=0;
		mostFollowedUser=null;
		mostProductiveUser=null;
		mostBlockedUser=null;
		
		mostProductiveUserTreeMap.clear();
		mostFollowedUserTreeMap.clear();
		mostBlockedUserTreeMap.clear();
	}
    
	@Override
	public int getLengthOfLongestTweetAttempted() {
		return lengthOflongestTweet;
	}

	@Override
	public String getMostFollowedUser() {
		return mostFollowedUser;
	}

	@Override
	public String getMostProductiveUser() {
		return mostProductiveUser;
	}

	@Override
	public String getMostBlockedFollower() {
		return mostBlockedUser;
	}
}



