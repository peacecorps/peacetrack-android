package com.peacetrack.views.cohorts;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peacetrack.R;
import com.peacetrack.backend.cohorts.CohortsDAO;
import com.peacetrack.models.cohorts.Cohort;

/**
 * @author Pooja
 * 
 ***************************************************
 * Screen to add new cohort from all cohorts screen
 * Take two parameters as input-
 * - Cohort Title
 * - Optional Description
 ***************************************************
 *
 */
public class AddCohortActivity extends ActionBarActivity implements
		OnItemSelectedListener {

	private Cohort cohort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcohort);
	}

	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Button addCohortButton = (Button) findViewById(R.id.savecohortbutton);
		final EditText nameEditText = (EditText) findViewById(R.id.cohortname);
		final EditText descriptionEditText = (EditText) findViewById(R.id.cohortdescription);

		addCohortButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cohort = new Cohort();
				String name = nameEditText.getText().toString();
				/*
				 *  Check on cohort title so that it is not left blank and 
				 *  if so user will get a pop up message asking him/her to enter a name. 
				 */
				
				if (nameEditText.getText().length() == 0) {
					Toast.makeText(AddCohortActivity.this,
							getString(R.string.namecheck), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				/*
				 * Cohort name is unique in a local database.
				 * Check on the name of cohort entered so that user does not end up 
				 * entering a cohort name that already exists in local database.
				 * If so, user will get a message asking him/her to choose a different name.
				 */
				if (isExistingCohortName(name)) {
					Toast.makeText(AddCohortActivity.this,
							getString(R.string.duplicatecohortcheck),
							Toast.LENGTH_SHORT).show();
					return;
				}
				cohort.setName(name);
				cohort.setDescription(descriptionEditText.getText().toString());

				saveCohort();
				
				Intent intent = new Intent(AddCohortActivity.this,
						ListCohortsActivity.class);
				AddCohortActivity.this.startActivity(intent);

			}
		});

	}

	/*
	 * public boolean onCreateOptionsMenu(Menu menu) { MenuInflater menuInflater
	 * = getMenuInflater(); menuInflater.inflate(R.menu.addmenu, menu);
	 * 
	 * getSupportActionBar().setDisplayShowTitleEnabled(true); return true;
	 * 
	 * }
	 */

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
		} else {
		}
		return super.onOptionsItemSelected(item);

	}

	/**
	 * Calls addcohort method from cohorts dao to save a cohort into local database
	 */
	private void saveCohort() {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		cohortsDAO.addCohort(cohort);
		finish();
	}

	/**
	 * Checks if the given cohort name exists in the local database already.
	 * Returns true or false accordingly.
	 * @param name
	 * @return
	 */
	private boolean isExistingCohortName(String name) {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		ArrayList<Cohort> allCohorts = cohortsDAO.getAllCohorts();

		for (int i = 0; i < allCohorts.size(); i++) {
			if (allCohorts.get(i).getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
