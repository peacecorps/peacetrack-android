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
public class WelcomeActivity extends ActionBarActivity implements OnClickListener{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.activity_welcome);
	    
	}

	@Override
	  public void onResume() {
		super.onResume();
		Button cohortsbutton = (Button)findViewById(R.id.cohortsbutton);
		Button activitybutton = (Button)findViewById(R.id.activitybutton);
		Button measurementbutton = (Button)findViewById(R.id.measurementbutton);
		
		cohortsbutton.setOnClickListener(WelcomeActivity.this);
		activitybutton.setOnClickListener(WelcomeActivity.this);
		measurementbutton.setOnClickListener(WelcomeActivity.this);
	
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		case R.id.cohortsbutton:    //Cohorts
			Intent intent = new Intent(WelcomeActivity.this,AllCohortsActivity.class);
			WelcomeActivity.this.startActivity(intent);
			break;
		case R.id.activitybutton:    //Activities
			intent = new Intent(WelcomeActivity.this,AllActivitiesActivity.class);
			WelcomeActivity.this.startActivity(intent);
			break;
		case R.id.measurementbutton:    //Measurements
			intent = new Intent(WelcomeActivity.this,AllMeasurementsActivity.class);
			WelcomeActivity.this.startActivity(intent);
			break;
			
		}
		
	}
	
}
