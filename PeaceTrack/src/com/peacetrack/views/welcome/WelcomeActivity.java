/**
 * 
 */
package com.peacetrack.views.welcome;

import com.peacetrack.R;
import com.peacetrack.R.id;
import com.peacetrack.R.layout;
import com.peacetrack.views.activities.AllActivitiesActivity;
import com.peacetrack.views.cohorts.AllCohortsActivity;
import com.peacetrack.views.measurements.AllMeasurementsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Pooja
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
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if(!preferences.contains(getString(R.string.name))) {
			Intent intent = new Intent(this,LoginActivity.class);
			this.startActivity(intent);
			this.finish();
		}

		else {
			Button cohortsbutton = (Button) findViewById(R.id.cohortsbutton);
			Button activitybutton = (Button) findViewById(R.id.activitybutton);
			Button measurementbutton = (Button) findViewById(R.id.measurementbutton);

			cohortsbutton.setOnClickListener(WelcomeActivity.this);
			activitybutton.setOnClickListener(WelcomeActivity.this);
			measurementbutton.setOnClickListener(WelcomeActivity.this);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cohortsbutton) {
			Intent intent = new Intent(WelcomeActivity.this,
					AllCohortsActivity.class);
			WelcomeActivity.this.startActivity(intent);
		} else if (id == R.id.activitybutton) {
			Intent intent = new Intent(WelcomeActivity.this,
					AllActivitiesActivity.class);
			WelcomeActivity.this.startActivity(intent);
		} else if (id == R.id.measurementbutton) {
			Intent intent = new Intent(WelcomeActivity.this,
					AllMeasurementsActivity.class);
			WelcomeActivity.this.startActivity(intent);
		}

	}

}
