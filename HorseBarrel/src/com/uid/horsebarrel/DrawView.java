package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * 	Start date:11/22/2014
 * DrawView class is used to draw the horse barrel field. It implements onDraw method
 * and redraws the screen. It also implements SensorEventListener for checking
 * accelerometer events. On tilt, accelerometer records the reading and screen is
 * invalidated which calls onDraw method.
 * Class CS 6301.022
 * Professor John Cole
*/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.View;

public class DrawView extends View implements SensorEventListener
{
	
	public Vibrator vibrator;
	Paint pBlack = new Paint();
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Paint pBlue, pRed, pText;
	float x, y, z;
	int width, height;
	private Context mContext;
	private long currentTime;
	private float diff;
	private float currentx;
	private float currenty;
	private int count = 0;
	private int sensitivity;
	private float currentVelocity;
	
	private double currentOneAngle;
	private boolean isOneComplete;
	private boolean isOneHalfComplete;
	
	private double currentTwoAngle;
	private boolean isTwoComplete;
	private boolean isTwoHalfComplete;
	
	private double currentThreeAngle;
	private boolean isThreeComplete;
	private boolean isThreeHalfComplete;
	
	public boolean pauseball;
	
	public boolean gameover;
	
	/**
	 * @author  vijaykrishn vxv140430
	 * This method sets the colors of balls and set styles. 
	 */
	public DrawView(Context context)
	{
		super(context);
		
		pBlack.setColor(Color.BLACK);
		pBlack.setStrokeWidth(4);
		pBlack.setTextSize(35);
		pBlack.setStyle(Style.STROKE);
		pText = new Paint();
		pText.setColor(Color.BLACK);
		pText.setStrokeWidth(4);
		pText.setTextSize(35);
		pBlue = new Paint();
		pBlue.setColor(Color.BLUE);
		pBlue.setStrokeWidth(2.0f);
		pBlue.setTextSize(35);
		pBlue.setStyle(Style.STROKE);
		pRed = new Paint();
		pRed.setColor(Color.RED);
		pRed.setStrokeWidth(2.0f);
		pRed.setTextSize(40);
		pRed.setStyle(Style.STROKE);
		mContext = context;
		currentTime = System.currentTimeMillis();
		currenty = 0;
		currentx = this.getWidth()/2;
		
	}
	/**
	 * @author  pujitha sri lakshmi pxp142730
	 * This view can't get the sensor manager, the main activity must get it
	 * and pass it in.
	 */
	public void setSensor(SensorManager sm)
		{
		mSensorManager = sm;
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer,
		    SensorManager.SENSOR_DELAY_NORMAL);
		float power = mAccelerometer.getPower();
		float resolution = mAccelerometer.getResolution();
		float maxRange = mAccelerometer.getMaximumRange();
		}
	
	/**
	 * @author  pujitha sri lakshmi pxp142730
	 * This method registers accelerometer.
	 */
	public void registerListener()
	{
	mSensorManager.registerListener(this, mAccelerometer,
	    SensorManager.SENSOR_DELAY_NORMAL);
	}

	/**
	 * @author  pujitha sri lakshmi pxp142730
	 * This method unregisters accelerometer.
	 */
	public void unRegisterListener()
	{	
	mSensorManager.unregisterListener(this);
	}
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method draws everything. It may seem wasteful to re-draw the entire screen with
	 * every frame refresh, but it works. Get the ball from our resources, set up
	 * the paddle, and draw green lines around the court.
	 */
	protected void onDraw(Canvas canvas)
	{
		if(!pauseball){
		
		width = this.getWidth();
		height = this.getHeight();
		
		canvas.drawLine(0, 0, this.getWidth(), 0, pBlue);
		canvas.drawLine(0, 0, 0, this.getHeight(), pBlue);
		canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
		    this.getHeight(), pBlue);
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth()/3, this.getHeight() - 1,
				pBlue);

		canvas.drawLine(this.getWidth()* 2/3, this.getHeight() - 1,this.getWidth() , this.getHeight() - 1,
				pBlue);

		
		canvas.drawLine(width / 3, height, width / 3, height - 30, pBlue);
		canvas.drawLine(width * 2 / 3, height, width * 2 / 3, height - 30, pBlue);
		
		if(isOneComplete)
			canvas.drawCircle(width / 4, height / 2, 40, pRed);
		else
			canvas.drawCircle(width / 4, height / 2, 40, pBlue);

		if(isTwoComplete)
			canvas.drawCircle(width * 3 / 4, height / 4, 40, pRed);
		else
			canvas.drawCircle(width * 3 / 4, height / 4, 40, pBlue);
		
		if(isThreeComplete)
			canvas.drawCircle(width * 3 / 4, height * 3/ 4, 40, pRed);
		else
			canvas.drawCircle(width * 3 / 4, height * 3/ 4, 40, pBlue);


		if(count == 0){
			currentx = this.getWidth()/2;
			count++;
		}
		
			sensitivity = 1000;
			diff = diff / 1000; //millisec to seconds
			currentx =  currentx - ((currentVelocity * diff + (1/2) * x * (diff * diff)) * sensitivity);
			currenty = ((-y * (diff * diff) * sensitivity) + currenty) ;
			currentVelocity = x * diff;
			
			//first barrel
			currentOneAngle = calculateAngleCos(currentx,currenty);
			if(isOneHalfComplete)
				currentOneAngle = 360 - currentOneAngle;
			if(currentOneAngle >= 175){
				isOneHalfComplete = true;
			}
			if(currentOneAngle >= 355)
				isOneComplete = true;
			
			//second barrel
			currentTwoAngle = calculateAngleCosThree(currentx,currenty);
			if(isTwoHalfComplete)
				currentTwoAngle = 360 - currentTwoAngle;
			if(currentTwoAngle >= 175){
				isTwoHalfComplete = true;
			}
			if(currentTwoAngle >= 355)
				isTwoComplete = true;
			
			//third barrel
			currentThreeAngle = calculateAngleCosTwo(currentx,currenty);
			if(isThreeHalfComplete)
				currentThreeAngle = 360 - currentThreeAngle;
			if(currentThreeAngle >= 175){
				isThreeHalfComplete = true;
			}
			if(currentThreeAngle >= 355)
				isThreeComplete = true;
			
			
			//here width and height needs to be fixed width
			if(currentx > width-40 && !checkgate()){
				currentx = width-40;
				((MainActivity)mContext).timeSwapBuff = ((MainActivity)mContext).timeSwapBuff  + 5000;
				vibrator.vibrate(500);
				canvas.drawLine(0, 0, this.getWidth(), 0, pRed);
				canvas.drawLine(0, 0, 0, this.getHeight(), pRed);
				canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
				    this.getHeight(), pRed);
				canvas.drawLine(0, this.getHeight() - 1, this.getWidth()/3, this.getHeight() - 1,
						pRed);

				canvas.drawLine(this.getWidth()* 2/3, this.getHeight() - 1,this.getWidth() , this.getHeight() - 1,
						pRed);
			}
			else if(currentx < 0 && !checkgate()){
				currentx = 0;
				((MainActivity)mContext).timeSwapBuff = ((MainActivity)mContext).timeSwapBuff  + 5000;
				vibrator.vibrate(500);
				canvas.drawLine(0, 0, this.getWidth(), 0, pRed);
				canvas.drawLine(0, 0, 0, this.getHeight(), pRed);
				canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
				    this.getHeight(), pRed);
				canvas.drawLine(0, this.getHeight() - 1, this.getWidth()/3, this.getHeight() - 1,
						pRed);

				canvas.drawLine(this.getWidth()* 2/3, this.getHeight() - 1,this.getWidth() , this.getHeight() - 1,
						pRed);
			}
			
			if(currenty > height-40){
				currenty = height-40;
				if(!checkgate()){
				((MainActivity)mContext).timeSwapBuff = ((MainActivity)mContext).timeSwapBuff  + 5000;
				vibrator.vibrate(500);
				canvas.drawLine(0, 0, this.getWidth(), 0, pRed);
				canvas.drawLine(0, 0, 0, this.getHeight(), pRed);
				canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
				    this.getHeight(), pRed);
				canvas.drawLine(0, this.getHeight() - 1, this.getWidth()/3, this.getHeight() - 1,
						pRed);

				canvas.drawLine(this.getWidth()* 2/3, this.getHeight() - 1,this.getWidth() , this.getHeight() - 1,
						pRed);
				}
			}
			else if(currenty < 0){
				currenty = 0;
				if(!checkgate()){
				((MainActivity)mContext).timeSwapBuff = ((MainActivity)mContext).timeSwapBuff  + 5000;
				vibrator.vibrate(500);
				canvas.drawLine(0, 0, this.getWidth(), 0, pRed);
				canvas.drawLine(0, 0, 0, this.getHeight(), pRed);
				canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
				    this.getHeight(), pRed);
				canvas.drawLine(0, this.getHeight() - 1, this.getWidth()/3, this.getHeight() - 1,
						pRed);

				canvas.drawLine(this.getWidth()* 2/3, this.getHeight() - 1,this.getWidth() , this.getHeight() - 1,
						pRed);
				}
			}
			
			if(!gameover){
			
			if(isOneComplete && isTwoComplete && isThreeComplete){
				if(currentx > width /3 && currenty < 30 && currentx < 2* width/3){
					((MainActivity)mContext).stopTimer(false);
					gameover = true;
				}
			}
			
			if(calculateDistance(currentx+20,currenty+20,width/4,height/2) < 60 * 60){
				((MainActivity)mContext).stopTimer(true);
				gameover = true;
				((MainActivity)mContext).showGameOver();
			}
			
			if(calculateDistance(currentx+20,currenty+20,width * 3/4,height/4) < 60 * 60){
				((MainActivity)mContext).stopTimer(true);
				gameover = true;
				((MainActivity)mContext).showGameOver();
			}
			
			if(calculateDistance(currentx+20,currenty+20,width * 3/4,height* 3/4) < 60 * 60){
				((MainActivity)mContext).stopTimer(true);
				gameover = true;
				((MainActivity)mContext).showGameOver();
			}
			
			canvas.drawCircle(currentx+20, height - currenty - 20, 20, pBlue);
			
			}
		}
	}
	
	public boolean checkgate(){
		if(currentx >= (width-60)/3 && currentx <= (width-60) * 2/3 && currenty <=30){
			return true;
		}else
			return false;
	}
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method calculates distance between two points in a plane.
	 */
	private double calculateDistance(float x1, float y1,float x2,float y2){
		return ((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2));
	}
	
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method calculates angle between two lines for first barrel.
	 */
	public double calculateAngleCos(float x,float y){
		
		double sideC = Math.sqrt((width/2-width/4) * (width/2-width/4)  + (0-height/2) * (0-height/2));
		double sideB = Math.sqrt((x-width/4) * (x-width/4)  + (y-height/2) * (y-height/2));
		double sideA = Math.sqrt((x-width/2) * (x-width/2)  + (y-0) * (y-0));
		
		 double angA = Math.toDegrees(Math.acos((sideA * sideA - sideB * sideB - sideC * sideC) / (-2 * sideB * sideC)));
		
		return angA;
	}
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * This method This method calculates angle between two lines for second barrel.
	 */
	
	public double calculateAngleCosTwo(float x,float y){
		
		double sideC = Math.sqrt((width/2-width * 3/4) * (width/2-width * 3/4)  + (0-height/4) * (0-height/4));
		double sideB = Math.sqrt((x-width * 3/4) * (x-width * 3/4)  + (y-height/4) * (y-height/4));
		double sideA = Math.sqrt((x-width/2) * (x-width/2)  + (y-0) * (y-0));
		
		 double angA = Math.toDegrees(Math.acos((sideA * sideA - sideB * sideB - sideC * sideC) / (-2 * sideB * sideC)));
		
		return angA;
	}
	
	/**
	 * @author Vijay krishna vxv140430
	 * This method calculates angle between two lines for three barrel.
	 */
	
	public double calculateAngleCosThree(float x,float y){
		
		double sideC = Math.sqrt((width/2-width * 3/4) * (width/2-width * 3/4)  + (0-height * 3/4) * (0-height * 3/4));
		double sideB = Math.sqrt((x-width * 3/4) * (x-width * 3/4)  + (y-height * 3/4) * (y-height * 3/4));
		double sideA = Math.sqrt((x-width/2) * (x-width/2)  + (y-0) * (y-0));
		
		 double angA = Math.toDegrees(Math.acos((sideA * sideA - sideB * sideB - sideC * sideC) / (-2 * sideB * sideC)));
		
		return angA;
	}

	
	
	/**
	 * @author Vijay krishna vxv140430
	 * This method is called on change of sensor. For accelerometer,
	 * we update the values of x,y,z
	 */
	public void onSensorChanged(SensorEvent event)
	{
	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
		
		diff = System.currentTimeMillis() - currentTime;
		currentTime = System.currentTimeMillis();
		x = (event.values[0]);
		y = (event.values[1]);
		z = (event.values[2]);
		this.updatePoint(x, y);
		this.invalidate();
		}
	}

	/**
	 *@author Vijay krishna vxv140430
	 * This method updates the measurements for the point.
	 * 
	 * @param x
	 *            the x-axis
	 * @param y
	 *            the y-axis
	 * @param color
	 *            the color
	 */
	public void updatePoint(float x, float y)
	{

		// Bound the y-axis to +/- the gravity of earth
		if (x > SensorManager.GRAVITY_EARTH)
		{
			x = SensorManager.GRAVITY_EARTH;
		}
		if (x < -SensorManager.GRAVITY_EARTH)
		{
			x = -SensorManager.GRAVITY_EARTH;
		}

		// Divide by the length of our axis.
		this.x = (x) ;

		// Bound the y-axis to +/- the gravity of earth
		if (y > SensorManager.GRAVITY_EARTH)
		{
			y = SensorManager.GRAVITY_EARTH;
		}
		if (y < -SensorManager.GRAVITY_EARTH)
		{
			y = -SensorManager.GRAVITY_EARTH;
		}

		// Normalize y to 1 and then scale to half the length of the y-axis.
		this.y = (y);

		this.invalidate();
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	

}
