/**
 * 
 */
package com.peacetrack;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * @author Pooja
 *
 */
public class AllCohortsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_listcohorts);
	}

	@Override
	  public void onResume() {
		super.onResume();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
	    menuInflater.inflate(R.menu.allcohortsmenu, menu);
	    
	    //When user types i.e. query for the item in the search bar, how would the search bar behave
	    
	    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
	      @Override
	      public boolean onQueryTextSubmit(String query) {
	        //TODO 
	        return true;
	      }

	      @Override
	      public boolean onQueryTextChange(String newText) {
	        //TODO 
	        return true;
	      }
	    };
	    
	    
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    
	    
	    final MenuItem addCohort = menu.findItem(R.id.action_addcohort);

	    getSupportActionBar().setDisplayShowTitleEnabled(true);
	    return true;
	}
	
	/*
	 * Select the new screen when any icon in action bar is selected.
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case android.R.id.home:
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        case R.id.action_addcohort:
            intent = new Intent(this, AddCohortActivity.class);
            startActivity(intent);
            break;
        default:
            return super.onOptionsItemSelected(item);
    }
		return true;
	}

	
	//TODO Populate Cohorts data to the next screen from here.

}
