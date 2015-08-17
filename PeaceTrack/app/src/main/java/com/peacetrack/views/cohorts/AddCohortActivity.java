/**
 * 
 */
package com.peacetrack.views.cohorts;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peacetrack.R;
import com.peacetrack.backend.cohorts.CohortsDAO;
import com.peacetrack.models.cohorts.Cohort;

public class AddCohortActivity extends ActionBarActivity {

	private boolean isEdit;
	private String oldName;

	private Button addCohortButton;
	private EditText nameEditText;
	private EditText descriptionEditText;

	private Cohort cohort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcohort);

		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		initialize();
		if(isEdit) {
			setEditableElements();
		}
		bindAddButtonListener();
	}

	private void initialize() {
		isEdit = getIntent().getBooleanExtra("isEdit", false);
		if(isEdit) {
			int cohortId = getIntent().getIntExtra("cohortId", 0);
			CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
			cohort = cohortsDAO.getCohortWithID(cohortId);
			oldName = cohort.getName();
		}
		addCohortButton = (Button) findViewById(R.id.savecohortbutton);
		nameEditText = (EditText) findViewById(R.id.activityTitle);
		descriptionEditText = (EditText) findViewById(R.id.activityDescription);
	}

	private void setEditableElements() {
		nameEditText.setText(cohort.getName());
		descriptionEditText.setText(cohort.getDescription());
	}

	private void bindAddButtonListener() {
		if(!isEdit) {
			addCohortButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					cohort = new Cohort();
					String name = nameEditText.getText().toString();
					if (name.length() == 0) {
						Toast.makeText(AddCohortActivity.this,
								getString(R.string.cohortnamecheck), Toast.LENGTH_SHORT)
								.show();
						return;
					}

					if (checkExistingCohort(name)) {
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
		else {
			addCohortButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String name = nameEditText.getText().toString();
					if (name.length() == 0) {
						Toast.makeText(AddCohortActivity.this,
								getString(R.string.cohortnamecheck), Toast.LENGTH_SHORT)
								.show();
						return;
					}

					if (!name.equals(oldName) && checkExistingCohort(name)) {
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

	private void saveCohort() {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		if(!isEdit)
			cohortsDAO.addCohort(cohort);
		else
			cohortsDAO.updateCohort(cohort);
		finish();
	}

	private boolean checkExistingCohort(String name) {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		ArrayList<Cohort> allCohorts = cohortsDAO.getAllCohorts();

		for (int i = 0; i < allCohorts.size(); i++) {
			if (allCohorts.get(i).getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
}
