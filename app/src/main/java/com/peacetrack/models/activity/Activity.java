package com.peacetrack.models.activity;

/**
 * Created by chahat on 6/11/17.
 */

import android.database.sqlite.SQLiteDatabase;

public class Activity {

    // Instance Properties
    private int id; // Used to modify existing Activity

    private String title; // title of Activity

    private String description; // Description is Optional

    private String relevantCohort;

    private String date;

    private String time;

    private String activityOutput;

    private String associateOutputData;

    // Database Table
    public static final String ACTIVITY_TABLE = "activity";

    // Database Table column names
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_TITLE = "name";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_COHORT = "relevant_cohort";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ACTIVITY_OUTPUT = "activity_output";
    public static final String COLUMN_ASSOCIATE_OUTPUT = "associate_output";

    // Database Creation - SQL statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + ACTIVITY_TABLE + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null, " +
            COLUMN_DESCRIPTION + " text, " +
            COLUMN_COHORT + " text not null, " +
            COLUMN_DATE + " text not null, " +
            COLUMN_TIME + " text not null, " +
            COLUMN_ACTIVITY_OUTPUT + " text, " +
            COLUMN_ASSOCIATE_OUTPUT + " text" +
            ");";

    // Used to create the table
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Used to upgrade the table
    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("drop table if exists " + ACTIVITY_TABLE);
        onCreate(database);
    }

    public Activity() {
        super();
        // TODO Auto-generated constructor stub
    }

    // Getters and Setters for name and description


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelevantCohort() {
        return relevantCohort;
    }

    public void setRelevantCohort(String relevantCohort) {
        this.relevantCohort = relevantCohort;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivityOutput() {
        return activityOutput;
    }

    public void setActivityOutput(String activityOutput) {
        this.activityOutput = activityOutput;
    }

    public String getAssociateOutputData() {
        return associateOutputData;
    }

    public void setAssociateOutputData(String associateOutputData) {
        this.associateOutputData = associateOutputData;
    }
}
