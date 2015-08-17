/**
 * 
 */
package com.peacetrack.views.measurements;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.peacetrack.R;
import com.peacetrack.backend.cohorts.CohortsDAO;
import com.peacetrack.backend.measurements.MeasurementDAO;
import com.peacetrack.models.cohorts.Cohort;
import com.peacetrack.models.measurements.Measurement;
import com.peacetrack.views.cohorts.CohortDetailsActivity;
import com.peacetrack.views.cohorts.ListCohortsActivity;
import com.peacetrack.views.welcome.WelcomeActivity;

import java.util.ArrayList;

public class AllMeasurementsActivity extends ActionBarActivity {

	private ListView measurementsListView;
	private ArrayList<Measurement> allMeasurements;
	private ArrayAdapter<String> adapter;
	private String[] measurementTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listmeasurements);

		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		initialize();
		bindListItemClickListener();
	}

	private void initialize() {
		measurementsListView = (ListView) findViewById(R.id.measurementslistView);

		MeasurementDAO measurementDAO = new MeasurementDAO(getApplicationContext());
		allMeasurements = measurementDAO.getAllMeasurements();
		int i = 0;
		measurementTitles = new String[allMeasurements.size()];

		for (Measurement measurement : allMeasurements) {
			measurementTitles[i++] = measurement.getTitle();
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, measurementTitles);
		measurementsListView.setAdapter(adapter);
	}

	private void bindListItemClickListener() {
		measurementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String title = adapter.getItem(position).toString();
				int measurementId = -1;
				for (int i = 0; i < allMeasurements.size(); i++) {
					Measurement measurement = allMeasurements.get(i);
					if (title.equals(measurement.getTitle())) {
						measurementId = measurement.getId();
						break;
					}
				}

				Intent intent = new Intent(AllMeasurementsActivity.this, MeasurementDetailsActivity.class);
				intent.putExtra("measurementId", measurementId);
				startActivity(intent);
			}
		});
	}

	/*
	 * Select the new screen when any icon in action bar is selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, WelcomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else if (itemId == R.id.action_addmeasurement) {
			Intent intent = new Intent(this, AddMeasurementActivity.class);
			startActivity(intent);
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
