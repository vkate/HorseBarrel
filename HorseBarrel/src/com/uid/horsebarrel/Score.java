package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * 	Start date:11/22/2014
 *This class is used to get the score and set the scores 
 * Class CS 6301.022
 * Professor John Cole
*/

public class Score implements Comparable<Score>{
	  
    private double scores;
    private String name;
    public Score(String name, double scores) {
	 	super();
 	    this.scores = scores;
 	    this.name = name;
		}
 
 public String getName(){
	 return name;
 }
public double getScores() {
	    return scores;
	    }


/**
 * This method compares the scores and return true if the current score is highest.
 * @author pujitha sri lakshmi pxp142730
 * 
 *  */

public int compareTo(Score compCon) {
	if(compCon.getScores() > this.scores)
		return 1;
	else if(compCon.getScores() < this.scores)
		return -1;
	else
		return 0;
	}
}
