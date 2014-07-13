/**
 * @author Pooja
 */
package com.peacetrack.models.indicators;

/*
 * ***************************************
 * Models the representation of indicators
 * ***************************************
 */
public class Indicators {

	// Instance properties
	private String post;
	private String sector;
	private String project;
	private String goal;
	private String objective;
	private String indicator;

	// Database table
	public static final String INDICATORS_TABLE = "indicators";

	// Database table column names
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_POST = "post";
	public static final String COLUMN_SECTOR = "sector";
	public static final String COLUMN_PROJECT = "project";
	public static final String COLUMN_GOAL = "goal";
	public static final String COLUMN_OBJECTIVE = "objective";
	public static final String COLUMN_INDICATOR = "indicator";

	/*// Database creation sql statement
	private static final String DATABASE_CREATE = "create table if not exists "			
			+ INDICATORS_TABLE + "(" + COLUMN_ID + " integer primary key autoincrement," 
			+ COLUMN_POST + " text, " + COLUMN_SECTOR
			+ " text, " + COLUMN_PROJECT + " text, " + COLUMN_GOAL + " text, "
			+ COLUMN_OBJECTIVE + " text, " + COLUMN_INDICATOR + " text" + ");";

	// used to create the table
	public static void onCreate(SQLiteDatabase database) {
		try {
			database.execSQL(DATABASE_CREATE);
		} catch (SQLException sqlException) {
			throw new Error("Error in creating database ");
		}

	}

	// used to upgrade the table
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("drop table if exists " + INDICATORS_TABLE);
		onCreate(database);
	}
*/
	// Getters and Setters
	public Indicators() {
		super();
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

}
