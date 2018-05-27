package edu.sjsu.cmpe275.aop.aspect;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import edu.sjsu.cmpe275.aop.TweetStatsImpl;

/**
 * The Class StatsAspect.
 */
@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired TweetStatsImpl stats;
	
	
	/**
	 * Before tweet advice.
	 * This advice records the length of longest attempted message length
	 * Also it updates mostProductiveUserTreeMap on the basis of most successful tweets 
	 * @param joinPoint the join point
	 */
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void beforeTweetAdvice(JoinPoint joinPoint) {
		
		System.out.printf("After the executuion of the method %s\n", joinPoint.getSignature().getName());
		String user = (String) joinPoint.getArgs()[0];
		String message = (String) joinPoint.getArgs()[1];
		System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		
		/*Update lengthOflongestTweet with longest length*/
		if(message.length()>TweetStatsImpl.lengthOflongestTweet){
			TweetStatsImpl.lengthOflongestTweet=message.length();
		}
	
		/*Update TreeMap of most productive user based on successful tweets
		 * That is the tweets whose length is less than or equal to 140 characters*/
		
		if(message.length()<=140){
			if(TweetStatsImpl.mostProductiveUserTreeMap.containsKey(user)){
				TweetStatsImpl.mostProductiveUserTreeMap.put(user, TweetStatsImpl.mostProductiveUserTreeMap.get(user)+message.length());
			}else{
				TweetStatsImpl.mostProductiveUserTreeMap.put(user, message.length());
				}
		}
		
	}
	
	/**
	 * After follow advice.
	 * This advice updates mostFollowedUserTreeMap by updating set of followers for a particular user
	 * @param joinPoint the join point
	 */ 
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..))")
	public void afterFollowAdvice(JoinPoint joinPoint){
		System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String follower=(String)joinPoint.getArgs()[0]; 
		String followee=(String)joinPoint.getArgs()[1];
		
		/*Update a TreeMap of most followed User as key and set of followers in value*/
		if(RetryAspect.runAfterReturning && !(TweetStatsImpl.mostBlockedUserTreeMap.containsKey(follower) 
				&& TweetStatsImpl.mostBlockedUserTreeMap.get(follower).contains(followee))){
			Set<String>setOffollowers=null;
			/*Check if user exists in TreeMap or not
			 * If exist take out and update set of users 
			 * else create an initial set of users*/
			setOffollowers=TweetStatsImpl.mostFollowedUserTreeMap.containsKey(followee)?
					TweetStatsImpl.mostFollowedUserTreeMap.get(followee):new HashSet<String>();
			
			setOffollowers.add(follower);
			TweetStatsImpl.mostFollowedUserTreeMap.put(followee, setOffollowers);
		}
		RetryAspect.runAfterReturning=true;
	}
	
	
	/**
	 * After block advice.
	 * This advice updates mostBlockedUserTreeMap by maintaining a set of blockers for a particular users
	 * Also, this advice removes follower if present from mostFollowedUserTreeMap of the blocking user 
	 * @param joinPoint the join point
	 */
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetService.block(..))")
	public void afterBlockAdvice(JoinPoint joinPoint){
		System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		String user=(String)joinPoint.getArgs()[0];
		String followerToBlock=(String)joinPoint.getArgs()[1];
		
		/*Update a TreeMap of most blocked User as key and set of followers in value*/
		if(RetryAspect.runAfterReturning){
		Set<String>setOfblockers=null;
		/*Check if user exists in TreeMap or not
		 * If exist take out and update set of users 
		 * else create an initial set of users*/
		setOfblockers=TweetStatsImpl.mostBlockedUserTreeMap.containsKey(followerToBlock) ?
				TweetStatsImpl.mostBlockedUserTreeMap.get(followerToBlock):new HashSet<String>();	
		setOfblockers.add(user);
		TweetStatsImpl.mostBlockedUserTreeMap.put(followerToBlock, setOfblockers);
		
		
		/*Remove the follower from the blocking user's set of followers*/
		if(TweetStatsImpl.mostFollowedUserTreeMap.containsKey(user)){
			Set<String>updatedSetOffollowers=null;
			updatedSetOffollowers=TweetStatsImpl.mostFollowedUserTreeMap.get(user);
			for (Iterator<String> iterator = updatedSetOffollowers.iterator(); iterator.hasNext();) {
			    String temp =  iterator.next();
			    if (temp == followerToBlock) {
			        iterator.remove();
			    } 
			}
			
			TweetStatsImpl.mostFollowedUserTreeMap.put(user, updatedSetOffollowers);
			}
		}
		
		RetryAspect.runAfterReturning=true;
	}
	
	
	/**
	 * Most productive user advice.
	 * This advice updates mostProductiveUser with the user with most successful tweet message length  
	 */
	@Before("execution(public String edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostProductiveUser())")
	public void mostProductiveUserAdvice() {
		System.out.println("Before Executing getMostProductiveUser method");
	 	if(TweetStatsImpl.mostProductiveUserTreeMap.size()>0){
	 		
	 	int maxMessageLength=Integer.MIN_VALUE;
	 	maxMessageLength = TweetStatsImpl.mostProductiveUserTreeMap.values().stream().max(Integer::compare).get();
	 	
	 	/*Run the loop till entry value is equal to maxMessageLength 
	 	 * Break after the first match as TreeMap is sorted in Alphabetical order and
	 	 * hence if there is tie first alphabetical user will be set*/
	 	for (Entry<String, Integer> entry : TweetStatsImpl.mostProductiveUserTreeMap.entrySet()) {
			if (entry.getValue() == maxMessageLength) {
				TweetStatsImpl.mostProductiveUser = entry.getKey();
				break; 
				}
	 		}
	 	}
	}
	
	/**
	 * Most followed user advice.
	 * This advice set mostFollowedUser with user having maximum number of followers
	 */
	@Before("execution(public String edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostFollowedUser())")
	public void mostFollowedUserAdvice(){
		System.out.println("Before Executing getMostFollowedUser method");
		int mostFollowerCount=Integer.MIN_VALUE;
		if(TweetStatsImpl.mostFollowedUserTreeMap.size()>0){
			for (Entry<String, Set<String>> entry : TweetStatsImpl.mostFollowedUserTreeMap.entrySet()) {
				//mostFollowedUser is updated only on the first occurence if there is a tie
				if (entry.getValue().size() > mostFollowerCount) {
					mostFollowerCount = entry.getValue().size();
					TweetStatsImpl.mostFollowedUser = entry.getKey(); 
					}
				}
			}
		}
	
	/**
	 * Most blocked user advice.
	 * This advice updates mostBlockedUser with follower who is blocked by most number of users 
	 */
	@Before("execution(public String edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostBlockedFollower())")
	public void mostBlockedUserAdvice(){
		System.out.println("Before Executing getMostBlockedFollower method");
		int mostBlockedFollowerCount=Integer.MIN_VALUE;
		if(TweetStatsImpl.mostBlockedUserTreeMap.size()>0){
			for (Entry<String, Set<String>> entry : TweetStatsImpl.mostBlockedUserTreeMap.entrySet()) {
				//mostBlockedUserTreeMap is updated only on the first occurence if there is a tie
				if (entry.getValue().size() > mostBlockedFollowerCount) {
					mostBlockedFollowerCount = entry.getValue().size();
					TweetStatsImpl.mostBlockedUser = entry.getKey(); 
					}
				}
			}

		}
}
