package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 *  * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * Start date:11/22/2014
 *This class is used to enter name and save the score of the user.
 * Class CS 6301.022
 * Professor John Cole
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterName extends Activity{

	String timePlayed;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Intent intent = getIntent();
	        timePlayed = intent.getStringExtra("time");
	        setContentView(R.layout.activity_enter_name);
	}
	
	
	
	/**
	 * @author  vijaykrishn vxv140430 
	 * This method show list of high scores.It calls the HighScoreActivity.
	 * .
	 */	
	public void showScores(View view){
	
		Intent intent = new Intent(this, HighScoresActivity.class);
		EditText nameText = (EditText) findViewById(R.id.username);
		String name = nameText.getText().toString();
		Score scoreObj = new Score(name, Double.parseDouble(timePlayed)/1000);
    	fileop file = new fileop();
    	file.writeFile(scoreObj);
    	startActivity(intent);
    	finish();
	}

}
