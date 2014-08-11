package com.peacetrack.views.cohorts;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;

import com.peacetrack.R;
import com.peacetrack.backend.cohorts.CohortsDAO;
import com.peacetrack.models.cohorts.Cohort;
import com.peacetrack.views.welcome.WelcomeActivity;

/**
 * @author Pooja
 * 
 ***************************************************
 * Screen to list all the cohorts in local database.
 * It has some additional buttons - edit,delete .
 ***************************************************
 *
 */
public class ListCohortsActivity extends ActionBarActivity {
	
	private ListView cohortsListView;

	private ArrayList<Cohort> allCohorts;

	private ArrayAdapter<String> adapter;

	private List<String> cohortNames;

	private CohortsDAO cohortsDAO;

	private ContextMenuInfo tempmenuinfo;

	private int checkedCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listcohorts);
		registerForContextMenu(findViewById(R.id.cohortslistView));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		tempmenuinfo = menuInfo;
	}

	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		cohortsListView = (ListView) findViewById(R.id.cohortslistView);
		createCohortsList();

		cohortsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		cohortsListView
				.setMultiChoiceModeListener(new MultiChoiceModeListener() {

					@Override
					public void onItemCheckedStateChanged(ActionMode mode,
							int position, long id, boolean checked) {
						/*
						 *  Here you can do something when items are selected/de-selected,
						 *  such as update the title in the CAB Capture total checked items
						 */
						checkedCount = cohortsListView
								.getCheckedItemCount();
						// Set the CAB title according to total checked items
						mode.setTitle(checkedCount + " Selected");
						
						mode.invalidate();
					}

					@Override
					public boolean onActionItemClicked(ActionMode mode,
							MenuItem item) {
						// Respond to clicks on the actions in the CAB
						switch (item.getItemId()) {
						case R.id.action_delete:
							deleteSelectedItems();
							mode.finish(); // Action picked, so close the CAB
							return true;
						case R.id.action_edit:
							SparseBooleanArray checkedPositions = cohortsListView.getCheckedItemPositions();
							Cohort cohort = allCohorts.get(checkedPositions.keyAt(0));
							
							Intent intent = new Intent(	ListCohortsActivity.this,EditCohortActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							
							/*
							 * Parameters passed here could be used in another activity EditCohortActivity.
							 */
							intent.putExtra("name", cohort.getName());
							intent.putExtra("description",cohort.getDescription());
							intent.putExtra("id",cohort.getId() );
							
							ListCohortsActivity.this.startActivity(intent);
							mode.finish();
							return true;
						default:
							return false;
						}
					}

					@Override
					public boolean onCreateActionMode(ActionMode mode, Menu menu) {
						// Inflate the menu for the CAB
						MenuInflater inflater = mode.getMenuInflater();
						inflater.inflate(R.menu.cab_allcohortsmenu, menu);
						return true;
					}

					@Override
					public void onDestroyActionMode(ActionMode mode) {
						// Here you can make any necessary updates to the
						// activity when
						// the CAB is removed. By default, selected items are
						// deselected/unchecked.
					}

					@Override
					public boolean onPrepareActionMode(ActionMode mode,
							Menu menu) {
						/*
						 * Here you can perform updates to the CAB due to
						 * an invalidate() request
						 * If user selects more than one row in all cohorts screen,
						 * edit option will be hidden.
						 */
						if (checkedCount == 1){
						       MenuItem item = menu.findItem(R.id.action_edit);
						       item.setVisible(true);
						       return true;
						   } else {
						       MenuItem item = menu.findItem(R.id.action_edit);
						       item.setVisible(false);
						       return true;
						   }
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.allcohortsmenu, menu);

		/*
		 *  When user types i.e. query for the item in the search bar, how would
		 *  the search bar behave
		 */
		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				Filter filter = adapter.getFilter();
				filter.filter(newText);
				return true;
			}
		};

		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		
		cohortsListView.setTextFilterEnabled(false);
		searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(false);
		searchView.setOnQueryTextListener(queryTextListener);
		
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		return true;
	}

	/**
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

	/**
	 * Create a list of cohorts from all the cohorts in cohorts dao.
	 * Everytime user comes to all cohorts screen after add/delete/upgrade
	 * this method is called and hence the list of cohorts is updated accordingly.
	 * 
	 */
	public void createCohortsList() {
		cohortsDAO = new CohortsDAO(getApplicationContext());
		allCohorts = cohortsDAO.getAllCohorts();

		cohortNames = new ArrayList<String>();

		for (Cohort cohort : allCohorts) {
			cohortNames.add(cohort.getName());
		}

		// Ideally all cohorts should go in the adapter

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, cohortNames);
		cohortsListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	/**
	 * Delete selected(long press) items.
	 * Using a loop as multiple items can be deleted at a time.
	 */
	private void deleteSelectedItems() {
		
		SparseBooleanArray checkedPositions = cohortsListView
				.getCheckedItemPositions();

		for (int i = 0; i < checkedPositions.size(); ++i) {
			int position = checkedPositions.keyAt(i);
			Cohort cohort = allCohorts.get(position);
			cohortsDAO.deleteCohort(cohort);
			allCohorts.remove(position);
			cohortNames.remove(position);
		}

		adapter.notifyDataSetChanged();
		
		/*AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Delete Cohort");
		alert.setMessage("Are you sure you want to delete this?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {

			SparseBooleanArray checkedPositions = cohortsListView
					.getCheckedItemPositions();

			for (int i = 0; i < checkedPositions.size(); ++i) {
				int position = checkedPositions.keyAt(i);
				Cohort cohort = allCohorts.get(position);
				cohortsDAO.deleteCohort(cohort);
				allCohorts.remove(position);
				cohortNames.remove(position);
			}

			//adapter.notifyDataSetChanged();
			
		  }
		});

		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
		adapter.notifyDataSetChanged();*/
		
	}

	
}
