/**
 * 
 */
package com.peacetrack.backend.cohorts;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.peacetrack.backend.CommonDatabaseHelper;
import com.peacetrack.models.cohorts.Cohorts;

/*
 * @author Pooja
 *
 * ****************************************
 * DAO object to update/delete/add cohorts
 * ****************************************
 * 
 */
public class CohortsDAO {

	private CommonDatabaseHelper commonDatabaseHelper;

	private SQLiteDatabase readDatabase;

	private SQLiteDatabase writeDatabase;

	/**
	 * @param commonDatabaseHelper
	 * @param readDatabase
	 * @param writeDatabase
	 * @param context
	 */
	public CohortsDAO(Context context) {
		super();
		this.commonDatabaseHelper = CommonDatabaseHelper.getInstance(context);
		this.readDatabase = commonDatabaseHelper.getReadableDatabase();
		this.writeDatabase = commonDatabaseHelper.getWritableDatabase();

	}

	private void openDB() {
		if (!readDatabase.isOpen()) {
			readDatabase = commonDatabaseHelper.getReadableDatabase();
		}
		if (!writeDatabase.isOpen()) {
			writeDatabase = commonDatabaseHelper.getWritableDatabase();
		}
	}

	private void closeDB() {
		if (readDatabase.isOpen()) {
			readDatabase.close();
		}
		if (writeDatabase.isOpen()) {
			writeDatabase.close();
		}
	}

	/*
	 * Method to get all the cohorts from the local database
	 */
	public ArrayList<Cohorts> getAllCohorts() {
		openDB();

		ArrayList<Cohorts> allCohorts = null;
		String[] columns = new String[3];
		columns[0] = Cohorts.COLUMN_ID;
		columns[1] = Cohorts.COLUMN_NAME;
		columns[2] = Cohorts.COLUMN_DESCRIPTION;

		Cursor returnData = readDatabase.query(Cohorts.COHORTS_TABLE, columns,
				null, null, null, null, null);

		allCohorts = extractCohorts(returnData);
		closeDB();
		return allCohorts;
	}

	/*
	 * Method to get a particular cohort given its ID. Mainly used to modify the
	 * existing cohort.
	 */
	public Cohorts getCohortWithID(int id) {
		openDB();

		Cohorts cohort = new Cohorts();
		String[] columns = new String[3];
		columns[0] = Cohorts.COLUMN_ID;
		columns[1] = Cohorts.COLUMN_NAME;
		columns[2] = Cohorts.COLUMN_DESCRIPTION;
		String where = Cohorts.COLUMN_ID + "=" + id;

		Cursor returnData = readDatabase.query(Cohorts.COHORTS_TABLE, columns,
				where, null, null, null, null);
		returnData.moveToFirst();

		cohort.setId(Integer.parseInt(returnData.getString(0)));
		cohort.setName(returnData.getString(1));
		cohort.setDescription(returnData.getString(2));

		closeDB();

		return cohort;
	}

	/*
	 * Method used to add a new cohort.
	 */
	public void addCohort(Cohorts cohort) {
		openDB();

		ContentValues contentValues = new ContentValues(2);
		contentValues.put(Cohorts.COLUMN_NAME, cohort.getName());
		contentValues.put(Cohorts.COLUMN_DESCRIPTION, cohort.getDescription());
		// TODO:check that this cohort does not exist already
		writeDatabase.insert(Cohorts.COHORTS_TABLE, null, contentValues);

		closeDB();
	}

	/*
	 * Method to update/modify an existing cohort. This will use cohort ID in
	 * where clause.
	 */
	public void updateProject(Cohorts cohort) {
		openDB();

		ContentValues contentValues = new ContentValues(2);
		contentValues.put(Cohorts.COLUMN_NAME, cohort.getName());
		contentValues.put(Cohorts.COLUMN_DESCRIPTION, cohort.getDescription());

		String where = Cohorts.COLUMN_ID + "=" + cohort.getId();
		writeDatabase.update(Cohorts.COHORTS_TABLE, contentValues, where, null);

		closeDB();
	}

	public int deleteCohort(Cohorts cohort) {
		openDB();

		String where = Cohorts.COLUMN_ID + "=" + cohort.getId();
		// Number of rows removed
		int numRemoved = writeDatabase.delete(Cohorts.COHORTS_TABLE, where,
				null);

		closeDB();
		return numRemoved;
	}

	/*
	 * Method to extract cohorts from the query.
	 */
	private ArrayList<Cohorts> extractCohorts(Cursor returnData) {
		// The output ArrayList is initialized
		ArrayList<Cohorts> output = new ArrayList<Cohorts>();
		// Move the counter to the first item in the return data
		returnData.moveToFirst();
		int count = 0;
		// While there are still values in the return data
		while (!returnData.isAfterLast()) {
			// Add the new Project to the ArrayList
			Cohorts cohorts = new Cohorts();
			cohorts.setId(Integer.parseInt(returnData.getString(0)));
			cohorts.setName(returnData.getString(1));
			cohorts.setDescription(returnData.getString(2));
			output.add(count, cohorts);
			// Advance the Cursor
			returnData.moveToNext();
			// Advance the counter
			count++;
		}
		// Return the ArrayList
		return output;
	}

}
