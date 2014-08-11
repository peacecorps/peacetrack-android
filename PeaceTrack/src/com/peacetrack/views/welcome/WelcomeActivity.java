package com.peacetrack.views.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.peacetrack.R;
import com.peacetrack.views.activities.AddActivityActivity;
import com.peacetrack.views.cohorts.ListCohortsActivity;
import com.peacetrack.views.measurements.AddMeasurementActivity;

/**
 * @author Pooja
 * 
 *******************************************************************
 * Main screen after one time login which user will be seeing often.
 * Contains four buttons to navigate to different screens -
 * -My Cohorts
 * -My Impacts
 * -Quickly add activity
 * -Quickly add measurement
 ********************************************************************
 * 
 */
public class WelcomeActivity extends ActionBarActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_welcome);

	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		/*
		 * Checks if user has entered his/her name in login screen which must have got saved in shared preferences.
		 * Only checking name here because all the fields are mandatory on login screen
		 * and thus it is not possible to save just one field without saving the others
		 * and navigating to different screen
		 * If shared preferences contains this information, dashboard will be the main screen.
		 * User can not go back to login screen once he has submitted the details.
		 */
		if (!preferences.contains(getString(R.string.name))) {
			Intent intent = new Intent(this, LoginActivity.class);
			this.startActivity(intent);
			this.finish();
		}

		else {
			getSupportActionBar().setDisplayShowHomeEnabled(false);
			
			Button cohortsbutton = (Button) findViewById(R.id.cohortsbutton);
			Button impactButton = (Button) findViewById(R.id.impactbutton);
			Button addactivityButton = (Button) findViewById(R.id.addactivitybutton);
			Button addmeasurementButton = (Button) findViewById(R.id.addmeasurementbutton);

			cohortsbutton.setOnClickListener(this);
			impactButton.setOnClickListener(this);
			addactivityButton.setOnClickListener(this);
			addmeasurementButton.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cohortsbutton) {
			Intent intent = new Intent(this, ListCohortsActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.impactbutton) {
			// TODO: Have to add view for my impacts page i.e. where one could
			// see the graphical comparison of measurements.
		} else if (id == R.id.addactivitybutton) {
			Intent intent = new Intent(this, AddActivityActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.addmeasurementbutton) {
			Intent intent = new Intent(this, AddMeasurementActivity.class);
			this.startActivity(intent);
		}

	}

}
