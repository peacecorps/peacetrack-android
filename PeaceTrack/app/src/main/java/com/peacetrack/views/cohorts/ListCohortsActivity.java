/**
 * 
 */
package com.peacetrack.views.cohorts;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.peacetrack.R;
import com.peacetrack.backend.cohorts.CohortsDAO;
import com.peacetrack.models.cohorts.Cohorts;
import com.peacetrack.views.welcome.WelcomeActivity;

public class ListCohortsActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {

	private ListView cohortsListView;
	private ArrayList<Cohorts> allCohorts;
	private ArrayAdapter<String> adapter;
	private String[] cohortNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listcohorts);

		initialize();
	}

	private void initialize() {
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		cohortsListView = (ListView) findViewById(R.id.cohortslistView);
		createCohortsList();
		bindListItemClickListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.allcohortsmenu, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setOnQueryTextListener(this);

		getSupportActionBar().setDisplayShowTitleEnabled(true);

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
			Intent intent = new Intent(this, WelcomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else if (itemId == R.id.action_addcohort) {
			Intent intent = new Intent(this, AddCohortActivity.class);
			startActivity(intent);
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void createCohortsList() {
		CohortsDAO cohortsDAO = new CohortsDAO(getApplicationContext());
		allCohorts = cohortsDAO.getAllCohorts();
		int i = 0;
		cohortNames = new String[allCohorts.size()];

		for (Cohorts cohort : allCohorts) {
			cohortNames[i++] = cohort.getName();
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, cohortNames);
		cohortsListView.setAdapter(adapter);
	}

	private void bindListItemClickListener() {
		cohortsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String name = adapter.getItem(position).toString();
				int cohort_id = -1;
				for (int i = 0; i < allCohorts.size(); i++) {
					Cohorts cohort = allCohorts.get(i);
					if (name.equals(cohort.getName())) {
						cohort_id = cohort.getId();
						break;
					}
				}

				Intent intent = new Intent(ListCohortsActivity.this, CohortDetailsActivity.class);
				intent.putExtra("cohort_id", cohort_id);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		adapter.getFilter().filter(newText);
		return true;
	}
}
