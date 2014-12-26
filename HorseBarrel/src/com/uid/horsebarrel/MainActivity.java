package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * 
 * 	Start date:11/22/2014
 *This class is usis the main activity used to play the game
 *It initializes drawview.
 * Class CS 6301.022
 * Professor John Cole
*/



import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	DrawView drawView;
	RelativeLayout.LayoutParams params;
	private boolean startClicked;

	
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method initializes canvas and adds call back method for start button.
	 */
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		drawView = new DrawView(this);
		//drawView.setBackgroundColor(Color.GREEN);
		SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		drawView.setSensor(sm);
		drawView.vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		params = new RelativeLayout.LayoutParams(30, 40);
		params.leftMargin = 50;
		params.topMargin = 60;
		DummyView iv = new DummyView(this);
		RelativeLayout v = (RelativeLayout)findViewById(R.id.relativeLayoutField);
		v.addView(iv);
		iv.invalidate();
		
		timerValue = (TextView) findViewById(R.id.timer);

		startButton = (Button) findViewById(R.id.start);

		startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				RelativeLayout v = (RelativeLayout)findViewById(R.id.relativeLayoutField);
				v.removeAllViewsInLayout();
				v.addView(drawView);
				startTime = SystemClock.uptimeMillis();
				customHandler.postDelayed(updateTimerThread, 0);
				startClicked = true;
			}
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}	
		return super.onOptionsItemSelected(item);
	}
	
    protected void onResume() {
        super.onResume();
        drawView.registerListener();
        if(startClicked){
	        startTime = SystemClock.uptimeMillis();
	        customHandler.postDelayed(updateTimerThread, 1000);
	    }
        drawView.pauseball = false;
    }

    protected void onPause() {
        super.onPause();
        drawView.unRegisterListener();
        drawView.pauseball = true;
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }
	
	public DrawView getDrawView(){
		return drawView;
	}
	
	private TextView timerValue;
	private Handler customHandler = new Handler();
	private long startTime = 0L;
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	private Button startButton;
	
	
	/**
	 * @author  vijaykrishn vxv140430 
	 * This method stops the timer and starts EnterName activity
	 */
	
	public void stopTimer(boolean onlyStop){
		timeSwapBuff += timeInMilliseconds;
		customHandler.removeCallbacks(updateTimerThread);
		if(!onlyStop){
			Intent intent = new Intent(this, EnterName.class );
			intent.putExtra("time", timeSwapBuff+"");
	    	startActivity(intent);
	    	finish();
		}
	}
	
	/**
	 * Thread for timer.
	 */
	private Runnable updateTimerThread = new Runnable() {
		public void run() {
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;
			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			int milliseconds = (int) (updatedTime % 1000);
			timerValue.setText("" + mins + ":"
					+ String.format("%02d", secs) + ":"
					+ String.format("%03d", milliseconds));
			customHandler.postDelayed(this, 0);
		}
	};

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showGameOver() {
		try{
			GameOverConfirmation confirmation = new GameOverConfirmation();
			FragmentManager fm = getFragmentManager();
			confirmation.show(fm, "Delete_confirm");
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}

}
