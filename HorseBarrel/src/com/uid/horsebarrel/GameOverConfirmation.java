package com.uid.horsebarrel;

/** 
 * @author vamsi katepalli vxk142730
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 *  * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 *Start date:11/22/2014
 * Class CS 6301.022
 * Professor John Cole
 * This  class is used to show the confirmation dialog and end the game.
 *
*/



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * @author Vamsi Katepalli Vxk142730
 * 
 */

@SuppressLint("NewApi")
public class GameOverConfirmation extends DialogFragment implements OnEditorActionListener{
	
	/**
	 * This interface is implemented by activity for callback function. 
	 *
	 */
	public interface DeleteDialogListener {
        void onFinishEditDialog(String inputText);
    }
	
	/**
	 * @author Vamsi Katepalli Vxk142730
	 * overridden method with callback function
	 */
  
    @Override
    
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.game_over)
               .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   MainActivity activity = (MainActivity) getActivity();
                	   activity.finish();
                	   Intent intent = activity.getIntent();
                	   startActivity(intent);
                   }
               });
        
        return builder.create();
    }
    /**
     * @author Vamsi Katepalli Vxk142730
     * 
     */

    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
        }
        return false;
    }
}
