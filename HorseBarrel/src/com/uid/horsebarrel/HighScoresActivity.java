package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 *  This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * Class CS 6301.022
 * Start date:11/22/2014
 * Professor John Cole
 * This is an android app that allows user to see the list of high scores. The contact
 * is written to a text file stored inside Scores folder created inside downloads folder. 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;





/**
 * Mainactivity describes the starting point of the project.
 * This activity extends the ActionBar.
 */


public class HighScoresActivity extends ActionBarActivity {

	Score scoreObj;
	String name; 
	long time;
	private List<Score> scores = new ArrayList<Score>();
    protected void onCreate(Bundle savedInstanceState){
    	Intent intent = getIntent();
    	name = intent.getStringExtra("name");
    	time = Long.parseLong((String) ((intent.getStringExtra("time")==null)?"0":intent.getStringExtra("time")));
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        try{
        	populateContacts();
        }catch (IOException err){
        	
        }
        
        populateListview();

    }
    
    /**
     * @author vijaykrishn vxv140430
     * This method populate the scores after reading from the file.
     */
    
    private void populateContacts() throws IOException {
    	fileop obj = new fileop();
    	scores = obj.readFile();
	}
    
    /**
     * @author vijaykrishn vxv140430
     * This sets the list view to the activity.
     */
    
    private void populateListview() {
		ArrayAdapter<Score> adapter = new MyContactAdapter();
		ListView list = (ListView)findViewById(R.id.Scores);
		list.setAdapter(adapter);
	}
    
    /**
     * @author vijaykrishn
     * Inner class to create our custom defined ArrayAdapter by overriding the methods.
     */
    
    private class MyContactAdapter extends ArrayAdapter<Score>{
		public MyContactAdapter(){
			super(HighScoresActivity.this, R.layout.item_view,scores);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if(itemView==null)
			{
				itemView= getLayoutInflater().inflate(R.layout.item_view, parent,false);
			}
			Score current = scores.get(position);
			
			
			if (position <10)
			{	
			TextView textContact = (TextView) itemView.findViewById(R.id.contactField);
			textContact.setText(Double.toString(current.getScores()));
			TextView name = (TextView) itemView.findViewById(R.id.namesField);
			name.setText(current.getName());
			
			
			}
			return  itemView;
		}
	
}
}
