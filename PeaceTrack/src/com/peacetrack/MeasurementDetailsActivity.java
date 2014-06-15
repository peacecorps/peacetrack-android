/**
 * 
 */
package com.peacetrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Pooja
 *
 */
public class MeasurementDetailsActivity extends ActionBarActivity implements OnClickListener {
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.activity_measurementdetails);
	    
	}

	@Override
	  public void onResume() {
		super.onResume();
		
		Button addnewactivitybutton = (Button)findViewById(R.id.addnewactivitybutton);
		Button addnewmeasurementbutton = (Button)findViewById(R.id.addnewmeasurementbutton);
		
	}
	@Override
	public void onClick(View v) {

switch(v.getId()) {
		
		case R.id.addnewactivitybutton:    //Add New Activity Button
			Intent intent = new Intent(this,AddActivityActivity.class);
			this.startActivity(intent);
			break;
		case R.id.addnewmeasurementbutton:    //Add new Measurement Button
			intent = new Intent(this,AddMeasurementActivity.class);
			this.startActivity(intent);
			break;
		
		}
	}

}
