package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * 	Start date:11/22/2014
 *This class is used to start the game with three buttons.
 * Class CS 6301.022
 * Professor John Cole
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class InitActivity extends ActionBarActivity {

	DrawView drawView; 
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * overriden method
	 */	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
	}

	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * overridden method
	 */		
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.init, menu);
		return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {
	
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method calls the MainActivity.
	 */	
	
	public void play(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method calls high score Activity.
	 */	
	
	public void showHighScores(View view){
		Intent intent = new Intent(this, HighScoresActivity.class);
		startActivity(intent);
	}
	
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * To exit to home screen
	 * @param view
	 */
	public void exit(View view){
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
    	finish();
    	//drawView.unRegisterListener();
	}
}
