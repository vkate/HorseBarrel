package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 *  * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * Start date:11/22/2014
 * This class is used to create a dummy view for initial display of canvas.
 * Class CS 6301.022
 * Professor John Cole
*/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.view.View;





public class DummyView extends View 
{
	
	Paint pBlack = new Paint();
	private Paint pBlue, pRed, pText;
	float x, y, z;
	float width, height;
	private Context mContext;

	
	/**
	 * @author pujitha sri lakshmi  pxp142730
	 * This creates the dummyview
	 */	
	
	public DummyView(Context context)
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
		
	}
	
	
	/**
	 * @author pujitha sri lakshmi  pxp142730
	 * This method draws everything. It may seem wasteful to re-draw the entire screen with
	 * every frame refresh, but it works. Get the ball from our resources, set up
	 * the paddle, and draw green lines around the court.
	 */
	protected void onDraw(Canvas canvas)
	{
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
		
			canvas.drawCircle(width / 4, height / 2, 40, pBlue);

			canvas.drawCircle(width * 3 / 4, height / 4, 40, pBlue);
		
			canvas.drawCircle(width * 3 / 4, height * 3/ 4, 40, pBlue);
			canvas.drawCircle(width/2, height-20, 20, pBlue);
	}



}
