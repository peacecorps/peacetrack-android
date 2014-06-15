package com.peacetrack;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.os.Build;

import com.peacetrack.R;

/**
 * @author Pooja
 * 
 * First screen that volunteer sees, which takes his/her name, country and sector as an input.
 *
 */
public class LoginActivity extends ActionBarActivity implements OnItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	}
	
	@Override
	  public void onResume() {
		super.onResume();
		Button loginButton = (Button)findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO condition for navigation to next page after clicking button
				Intent i = new Intent(LoginActivity.this,WelcomeActivity.class);
				LoginActivity.this.startActivity(i);
				
			}
		});
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
