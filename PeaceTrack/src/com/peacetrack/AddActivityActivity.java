/**
 * 
 */
package com.peacetrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author Pooja
 *
 */
public class AddActivityActivity extends ActionBarActivity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.activity_addactivity);
	}
	
	@Override
	  public void onResume() {
		super.onResume();
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView textView = (TextView) findViewById(R.id.associatedoutputdata);
		textView.setFocusable(false);
		textView.setClickable(true);
		textView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddActivityActivity.this, AddIndicatorsActivity.class);
				AddActivityActivity.this.startActivity(intent);
				
			}
		});
	}

	/*public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.addmenu, menu);
		
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		return true;
		
	}*/
	
	/*
	 * Select the new screen when any icon in action bar is selected.
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, AllActivitiesActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

}
