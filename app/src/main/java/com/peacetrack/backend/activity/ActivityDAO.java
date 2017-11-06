package com.peacetrack.backend.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.animation.AccelerateInterpolator;

import com.peacetrack.backend.CommonDatabaseHelper;
import com.peacetrack.models.activity.Activity;
import com.peacetrack.models.cohorts.Cohorts;

import java.util.ArrayList;

/**
 * Created by chahat on 6/11/17.
 */

public class ActivityDAO {

    private CommonDatabaseHelper commonDatabaseHelper;

    private SQLiteDatabase readDatabase;

    private SQLiteDatabase writeDatabase;


    public ActivityDAO(Context context) {
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

    public void addActivity(Activity activity) {
        openDB();

        ContentValues contentValues = new ContentValues(7);
        contentValues.put(Activity.COLUMN_TITLE, activity.getTitle());
        contentValues.put(Activity.COLUMN_DESCRIPTION, activity.getDescription());
        contentValues.put(Activity.COLUMN_COHORT, activity.getRelevantCohort());
        contentValues.put(Activity.COLUMN_DATE, activity.getDate());
        contentValues.put(Activity.COLUMN_TIME, activity.getTime());
        contentValues.put(Activity.COLUMN_ACTIVITY_OUTPUT, activity.getActivityOutput());
        contentValues.put(Activity.COLUMN_ASSOCIATE_OUTPUT, activity.getAssociateOutputData());

        writeDatabase.insert(Activity.ACTIVITY_TABLE, null, contentValues);

        closeDB();
    }

    public ArrayList<Activity> getAllActivity() {
        openDB();

        ArrayList<Activity> allActivity = null;
        String[] columns = new String[8];
        columns[0] = Activity.COLUMN_ID;
        columns[1] = Activity.COLUMN_TITLE;
        columns[2] = Activity.COLUMN_DESCRIPTION;
        columns[3] = Activity.COLUMN_COHORT;
        columns[4] = Activity.COLUMN_DATE;
        columns[5] = Activity.COLUMN_TIME;
        columns[6] = Activity.COLUMN_ACTIVITY_OUTPUT;
        columns[7] = Activity.COLUMN_ASSOCIATE_OUTPUT;

        Cursor returnData = readDatabase.query(Activity.ACTIVITY_TABLE, columns,
                null, null, null, null, null);

        allActivity = extractActivity(returnData);
        closeDB();
        return allActivity;
    }

    private ArrayList<Activity> extractActivity(Cursor returnData) {
        // The output ArrayList is initialized
        ArrayList<Activity> output = new ArrayList<Activity>();
        // Move the counter to the first item in the return data
        returnData.moveToFirst();
        int count = 0;
        // While there are still values in the return data
        while (!returnData.isAfterLast()) {
            // Add the new Project to the ArrayList
            Activity activity = new Activity();
            activity.setId(Integer.parseInt(returnData.getString(0)));
            activity.setTitle(returnData.getString(1));
            activity.setDescription(returnData.getString(2));
            activity.setRelevantCohort(returnData.getString(3));
            activity.setDate(returnData.getString(4));
            activity.setTime(returnData.getString(5));
            activity.setActivityOutput(returnData.getString(6));
            activity.setAssociateOutputData(returnData.getString(7));
            output.add(count, activity);
            // Advance the Cursor
            returnData.moveToNext();
            // Advance the counter
            count++;
        }
        // Return the ArrayList
        return output;
    }
}
