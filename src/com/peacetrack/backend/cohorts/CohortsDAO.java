package com.peacetrack.backend.cohorts;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.peacetrack.backend.CommonDatabaseHelper;
import com.peacetrack.models.cohorts.Cohort;

/**
 * @author Pooja
 * 
 ******************************************
 * DAO object to update/delete/add cohorts
 ******************************************
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

	/**
	 * Method to get all the cohorts from the local database
	 * @return
	 */
	public ArrayList<Cohort> getAllCohorts() {
		openDB();

		ArrayList<Cohort> allCohorts = null;
		String[] columns = new String[3];
		columns[0] = Cohort.COLUMN_ID;
		columns[1] = Cohort.COLUMN_NAME;
		columns[2] = Cohort.COLUMN_DESCRIPTION;

		Cursor returnData = readDatabase.query(Cohort.COHORTS_TABLE, columns,
				null, null, null, null, null);

		allCohorts = extractCohorts(returnData);
		closeDB();
		return allCohorts;
	}

	/**
	 * Method to get a particular cohort given its ID. Mainly used to modify the
	 * existing cohort.
	 * @param id
	 * @return
	 */
	public Cohort getCohortWithID(int id) {
		openDB();

		Cohort cohort = new Cohort();
		String[] columns = new String[3];
		columns[0] = Cohort.COLUMN_ID;
		columns[1] = Cohort.COLUMN_NAME;
		columns[2] = Cohort.COLUMN_DESCRIPTION;
		String where = Cohort.COLUMN_ID + "=" + id;

		Cursor returnData = readDatabase.query(Cohort.COHORTS_TABLE, columns,
				where, null, null, null, null);
		returnData.moveToFirst();

		cohort.setId(Integer.parseInt(returnData.getString(0)));
		cohort.setName(returnData.getString(1));
		cohort.setDescription(returnData.getString(2));

		closeDB();

		return cohort;
	}

	/**
	 * Method used to add a new cohort.
	 * @param cohort
	 */
	public void addCohort(Cohort cohort) {
		openDB();

		ContentValues contentValues = new ContentValues(2);
		contentValues.put(Cohort.COLUMN_NAME, cohort.getName());
		contentValues.put(Cohort.COLUMN_DESCRIPTION, cohort.getDescription());

		writeDatabase.insert(Cohort.COHORTS_TABLE, null, contentValues);

		closeDB();
	}

	/**
	 * Method to update/modify an existing cohort. This will use cohort ID in
	 * where clause.
	 * @param cohort
	 */
	public void updateCohort(Cohort cohort) {
		openDB();

		ContentValues contentValues = new ContentValues(2);
		contentValues.put(Cohort.COLUMN_NAME, cohort.getName());
		contentValues.put(Cohort.COLUMN_DESCRIPTION, cohort.getDescription());

		String where = Cohort.COLUMN_ID + "=" + cohort.getId();
		writeDatabase.update(Cohort.COHORTS_TABLE, contentValues, where, null);

		closeDB();
	}

	/**
	 * Method to delete any existing cohort from local database.
	 * @param cohort
	 * @return
	 */
	public int deleteCohort(Cohort cohort) {
		openDB();

		String where = Cohort.COLUMN_ID + "=" + cohort.getId();
		// Number of rows removed
		int numRemoved = writeDatabase.delete(Cohort.COHORTS_TABLE, where,
				null);

		closeDB();
		return numRemoved;
	}

	/**
	 * Method to extract cohorts from the query.
	 * @param returnData
	 * @return
	 */
	private ArrayList<Cohort> extractCohorts(Cursor returnData) {
		// The output ArrayList is initialized
		ArrayList<Cohort> output = new ArrayList<Cohort>();
		// Move the counter to the first item in the return data
		returnData.moveToFirst();
		int count = 0;
		// While there are still values in the return data
		while (!returnData.isAfterLast()) {
			// Add the new Project to the ArrayList
			Cohort cohort = new Cohort();
			cohort.setId(Integer.parseInt(returnData.getString(0)));
			cohort.setName(returnData.getString(1));
			cohort.setDescription(returnData.getString(2));
			output.add(count, cohort);
			// Advance the Cursor
			returnData.moveToNext();
			// Advance the counter
			count++;
		}
		// Return the ArrayList
		return output;
	}

}
