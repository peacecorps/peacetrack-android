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
 *******************************************
 * Screen to edit an existing cohort.
 * Values will be pre filled in this screen
 * and will be in edit mode.
 *******************************************
 *
 */
public class EditCohortActivity extends ActionBarActivity implements
		OnItemSelectedListener {

	private String cohortName;
	private String cohortDescription;
	private int cohortId;
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

		Button editCohortButton = (Button) findViewById(R.id.savecohortbutton);
		final EditText nameEditText = (EditText) findViewById(R.id.cohortname);
		final EditText descriptionEditText = (EditText) findViewById(R.id.cohortdescription);
		Intent intent = getIntent();
		cohortName = intent.getStringExtra("name");
		cohortDescription = intent.getStringExtra("description");
		cohortId = intent.getIntExtra("id", 0);
		nameEditText.setText(cohortName);
		descriptionEditText.setText(cohortDescription);

		editCohortButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cohort = new Cohort();
				String newName = nameEditText.getText().toString();
				String newDescription = descriptionEditText.getText().toString();
				
				if (nameEditText.getText().length() == 0) {
					Toast.makeText(EditCohortActivity.this,
							getString(R.string.namecheck), Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (!cohortName.equalsIgnoreCase(newName) && isExistingCohortName(newName)) {
					Toast.makeText(EditCohortActivity.this,
							getString(R.string.duplicatecohortcheck),
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				cohort.setName(newName);
				cohort.setDescription(newDescription);
				cohort.setId(cohortId);

				updateCohort();
				Intent intent = new Intent(EditCohortActivity.this,
						ListCohortsActivity.class);
				EditCohortActivity.this.startActivity(intent);

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
			Intent intent = new Intent(this, ListCohortsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else {
		}
		return super.onOptionsItemSelected(item);

	}

	private void updateCohort() {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		cohortsDAO.updateCohort(cohort);
		finish();
	}

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
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
