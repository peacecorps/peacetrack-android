/**
 * 
 */
package com.peacetrack.views.cohorts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.peacetrack.R;
import com.peacetrack.backend.cohorts.CohortsDAO;
import com.peacetrack.models.cohorts.Cohorts;
import com.peacetrack.views.activities.AddActivityActivity;
import com.peacetrack.views.measurements.AddMeasurementActivity;

/**
 * Show the cohort details
 */
public class CohortDetailsActivity extends ActionBarActivity implements
		OnClickListener {

	private Cohorts cohort;
	private Button addNewActivityButton;
	private Button addNewMeasurementButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cohortdetails_new);

		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		initialize();
		bindButtonListeners();
		setViewElements();
	}

	private void initialize() {
		int cohort_id = getIntent().getIntExtra("cohort_id", 0);
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		cohort = cohortsDAO.getCohortWithID(cohort_id);

		addNewActivityButton = (Button) findViewById(R.id.addnewactivitybutton);
		addNewMeasurementButton = (Button) findViewById(R.id.addnewmeasurementbutton);
	}

	private void bindButtonListeners() {
		addNewActivityButton.setOnClickListener(this);
		addNewMeasurementButton.setOnClickListener(this);
	}

	private void setViewElements() {
		TextView name = (TextView) findViewById(R.id.cohortNameTextView);
		TextView description = (TextView) findViewById(R.id.cohortDescriptionTextView);

		name.setText(cohort.getName());
		String des = cohort.getDescription();
		if(des.equals("")) {
			description.setText("None");
		}
		else {
			description.setText(des);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.cohortdetailsmenu, menu);

		return true;
	}

	/*
	 * Select the new screen when any icon in action bar is selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, ListCohortsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else if (itemId == R.id.action_edit) {
			Intent intent = new Intent(this, AddCohortActivity.class);
			intent.putExtra("cohortId", cohort.getId());
			intent.putExtra("isEdit", true);
			startActivity(intent);
		} else if(itemId == R.id.action_delete) {
			showDeleteCohortDialog();
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void showDeleteCohortDialog() {
		// If network not available then show an error message
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				CohortDetailsActivity.this);
		// set title
		alertDialogBuilder.setTitle(getString(R.string.cohortDeleteTitle));
		// set dialog message
		alertDialogBuilder
				.setMessage(getString(R.string.cohortDeleteMessage))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int id) {
								deleteCohort();
								Class actClass = null;
								try {
									actClass = Class.forName("com.peacetrack.views.cohorts.ListCohortsActivity");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								Intent intent = new Intent(CohortDetailsActivity.this, actClass);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
						})
				.setNegativeButton(getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int id) {
								dialog.dismiss();
							}
						});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	private void deleteCohort() {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		cohortsDAO.deleteCohort(cohort);
		finish();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.addnewactivitybutton) {
			Intent intent = new Intent(this, AddActivityActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.addnewmeasurementbutton) {
			Intent intent = new Intent(this, AddMeasurementActivity.class);
			this.startActivity(intent);
		}
	}
}
