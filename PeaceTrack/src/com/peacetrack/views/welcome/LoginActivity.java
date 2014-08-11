package com.peacetrack.views.welcome;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.peacetrack.R;
import com.peacetrack.backend.indicators.IndicatorsDBHandler;

/**
 * @author Pooja
 * 
 ************************************************************
 * First screen that volunteer sees, which takes his/her  
 * name,country and sector as an input.
 * This is one time screen and user can not go back to this screen
 * once he has submitted his/her details.
 ***********************************************************
 * 
 */
public class LoginActivity extends ActionBarActivity implements
		OnItemSelectedListener {

	private IndicatorsDBHandler indicatorsDBHandler;
	private List<String> sectorList;
	private ArrayAdapter<String> sectorDataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public void onResume() {
		super.onResume();
		Button loginButton = (Button) findViewById(R.id.loginButton);

		indicatorsDBHandler = new IndicatorsDBHandler(this);

		final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
		final EditText emailEditText = (EditText) findViewById(R.id.nameEditEmail);

		final Spinner postSpinner = (Spinner) findViewById(R.id.post_spinner);
		List<String> postList = indicatorsDBHandler.getAllPosts();
		ArrayAdapter<String> postDataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, postList);
		postDataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		postSpinner.setAdapter(postDataAdapter);
		postSpinner.setOnItemSelectedListener(this);

		final Spinner sectorSpinner = (Spinner) findViewById(R.id.sector_spinner);
		sectorList = indicatorsDBHandler.getAllSectors();
		sectorDataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, sectorList);
		sectorDataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sectorSpinner.setAdapter(sectorDataAdapter);

		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Checking if user enters the some name for cohort and email is valid or not.
				 * If not, user gets a message asking him/her to enter correct details.
				 */
				if (nameEditText.getText().length() == 0
						&& (emailEditText.getText().length() == 0 || !isEmailValid(emailEditText
								.getText()))) {
					Toast.makeText(LoginActivity.this,
							getString(R.string.nameandemailcheck),
							Toast.LENGTH_SHORT).show();
					return;
				}
				/*
				 * Checking if user enters the some name for cohort or not.
				 * If not, user gets a prompt message asking him/her to enter.
				 */
				if (nameEditText.getText().length() == 0) {
					Toast.makeText(LoginActivity.this,
							getString(R.string.namecheck), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				/*
				 * Checking if email is not empty and valid.
				 * If not, user gets a message asking to enter correct mail id.
				 */
				if (emailEditText.getText().length() == 0
						|| !isEmailValid(emailEditText.getText())) {
					Toast.makeText(LoginActivity.this,
							getString(R.string.emailcheck), Toast.LENGTH_SHORT)
							.show();
					return;
				}

				/*
				 * This would make sure that this login page launches only once
				 * when the user downloads the application. Add a check on these
				 * preferences in main activity i.e. welcome activity. If and
				 * only if these shared preferences are missing , will the
				 * application launch the first login page.
				 */
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString(getString(R.string.name), nameEditText
						.getText().toString());
				editor.putString(getString(R.string.email), emailEditText
						.getText().toString());
				editor.putString(getString(R.string.post), postSpinner
						.getSelectedItem().toString());
				editor.putString(getString(R.string.sector), sectorSpinner
						.getSelectedItem().toString());
				editor.commit();

				Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				LoginActivity.this.startActivity(i);
				LoginActivity.this.onBackPressed();
			}
		});

	}

	/**
	 * Method to check if the email address entered is valid.
	 * @param email
	 * @return
	 */
	boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.post_spinner:
			// if the post changes, update the associated sectors
			String selectedPost = parent.getItemAtPosition(position).toString();
			List<String> newSectors = indicatorsDBHandler
					.getAllSectorsForPost(selectedPost);
			sectorList.clear();
			sectorList.addAll(newSectors);
			sectorDataAdapter.notifyDataSetChanged();
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}
