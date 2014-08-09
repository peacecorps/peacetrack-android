package com.peacetrack.models.cohorts;

import android.database.sqlite.SQLiteDatabase;

/*
 * @author Pooja
 * 
 * *************************************
 * Models the representation Of Cohorts
 * **************************************
 */

public class Cohorts {

	// Instance Properties
	private int id; // Used to modify existing cohort

	private String name; // Name of the cohort is mandatory and should be unique

	private String description; // Description is Optional

	// Database Table
	public static final String COHORTS_TABLE = "cohorts";

	// Database Table column names
	public static final String COLUMN_ID = "id";

	public static final String COLUMN_NAME = "name";

	public static final String COLUMN_DESCRIPTION = "description";

	// Database Creation - SQL statement
	private static final String DATABASE_CREATE = "create table if not exists "
			+ COHORTS_TABLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null, " + COLUMN_DESCRIPTION + " text" + ");";

	// Used to create the table
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// Used to upgrade the table
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("drop table if exists " + COHORTS_TABLE);
		onCreate(database);
	}

	public Cohorts() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters for name and description
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return getName();
	}

}
