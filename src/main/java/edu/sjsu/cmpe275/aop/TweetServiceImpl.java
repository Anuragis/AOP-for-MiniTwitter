package edu.sjsu.cmpe275.aop;


import java.io.IOException;


public class TweetServiceImpl implements TweetService {


    private int passAfterAttempts = 0;
	/**
	 * @throws IllegalArgumentException if the message is more than 140 characters as measured by string
	 * length or either parameter is null or its length is zero.
	 * @throws IOException if there is a network failure
	 */
    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		if (passAfterAttempts-- > 0) throw new IOException();
    	if (user == null || message == null ||
                user.length() == 0 || message.length() == 0 ||
                message.length() > 140) throw new IllegalArgumentException();

    	System.out.printf("User %s tweeted message: %s\n", user, message);
    }

	/**
	 * @throws IllegalArgumentException if either parameter is null or empty, or when a user
	 * attempts to follow himself.
	 * @throws IOException if there is a network failure
	 */
    public void follow(String follower, String followee) throws IllegalArgumentException, IOException {
		if (passAfterAttempts-- > 0 ) throw new IOException();
        if (follower == null || followee == null ||
                follower.length() == 0 || followee.length() == 0 ) throw new IllegalArgumentException();

       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	/**
	 * @throws IllegalArgumentException if either parameter is null or empty, or when a user
	 * attempts to block himself.
	 * @throws IOException if there is a network failure
	 */
	public void block(String user, String follower) throws IllegalArgumentException, IOException {
		if (passAfterAttempts-- > 0) throw new IOException();
        if (user == null || follower == null ||
                user.length() == 0 || follower.length() == 0 ) throw new IllegalArgumentException();

       	System.out.printf("User %s blocked user %s \n", user, follower);
	}


	/**
	 * assign n for throw IOException test.
	 * @param n
	 */
	public void setPassAfterAttempts(int n, String placeHolder) {

	    passAfterAttempts = n;

	}

}
