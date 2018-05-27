package edu.sjsu.cmpe275.aop;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface TweetService.
 */
public interface TweetService {
	// Please do NOT change this file.
	
    /**
	 * Tweet.
	 *
	 * @param user the user
	 * @param message the message
	 * @throws IllegalArgumentException if the message is more than 140 characters as measured by string
	 * length or either parameter is null or its length is zero.
	 * @throws IOException if there is a network failure
	 */
    void tweet(String user, String message) throws IllegalArgumentException, IOException;

    /**
     * Follow.
     *
     * @param follower the follower
     * @param followee the followee
     * @throws IllegalArgumentException if either parameter is null or empty, or when a user
     * attempts to follow himself.
     * @throws IOException if there is a network failure
     */
    void follow(String follower, String followee)  throws IllegalArgumentException, IOException;
    
    /**
     * Block.
     *
     * @param user the user
     * @param followee the followee
     * @throws IOException if there is a network failure
     * @throws IllegalArgumentException if either parameter is null or empty, or when a user
     * attempts to block himself.
     */
    void block(String user, String followee) throws IOException;
    
    void setPassAfterAttempts(int n, String placeHolder);
}
