package com.cse120.ontask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cse120.ontask.com.cse120.ontask.task.Date;
import com.cse120.ontask.com.cse120.ontask.task.Frequency;
import com.cse120.ontask.com.cse120.ontask.task.Task;
import com.cse120.ontask.com.cse120.ontask.task.Urgency;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskDB.db";
    private static final String TASK_TABLE = "task_table";

    //Table Columns
    public static final String COLUMN_ID = "task_id";
    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK_DESCRIPTION = "description";
    public static final String COLUMN_TASK_FREQUENCY = "frequency";
    public static final String COLUMN_TASK_DEADLINE = "deadline";
    public static final String COLUMN_TASK_URGENCY = "urgency";

    public DBHandler(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Delete the database if it exists on startup -- For debugging

        String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TASK_TITLE
                + " TEXT," + COLUMN_TASK_DESCRIPTION + " TEXT,"
                + COLUMN_TASK_FREQUENCY + " TEXT," + COLUMN_TASK_DEADLINE
                + " TEXT," + COLUMN_TASK_URGENCY + " INTEGER" + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    /* Database Handler Functions */
    public void addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_FREQUENCY, frequencyToStringConvert(task));
        values.put(COLUMN_TASK_DEADLINE, dateToStringConvert(task));
        values.put(COLUMN_TASK_URGENCY, urgencyToIntegerConvert(task));

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TASK_TABLE, null, values);
        db.close();
    }

    public void updateTask(Task task){
        System.out.println("chk update database");
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_FREQUENCY, frequencyToStringConvert(task));
        values.put(COLUMN_TASK_DEADLINE, dateToStringConvert(task));
        values.put(COLUMN_TASK_URGENCY, urgencyToIntegerConvert(task));

        String whereClause = COLUMN_ID + "=" + task.getTask_id();
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TASK_TABLE, values, whereClause, null);
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> DBTasks = new ArrayList<Task>();
        String query = "SELECT * FROM " + TASK_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int key = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            Frequency frequency = stringToFrequencyConvert(cursor.getString(3));
            Date deadline = stringToDateConvert(cursor.getString(4));
            Urgency urgency = integerToUrgencyConvert(Integer.parseInt(cursor.getString(5)));
            System.out.println("chk key: " + key);
            Task currentTask = new Task(key, title, description, frequency, deadline, urgency);
            DBTasks.add(currentTask);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        if (DBTasks.size() == 0)
        {
            DBTasks = null;
        }
        return DBTasks;
    }
    /* End Database Handler Functions */

    public boolean deleteTask(Task task) {
        boolean result = false;
        Task DBTask = new Task();

        String query = "SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_ID + " = " + task.getTask_id();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            DBTask.setTask_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TASK_TABLE, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(DBTask.getTask_id()) });
            cursor.close();
            result = true;
        }
        db.close();

        return result;
    }

    private String frequencyToStringConvert(Task task) {
        String frequency;

        switch (task.getFrequency())
        {
            case ONCE:
                frequency = "Once";
                break;
            case DAILY:
                frequency = "Daily";
                break;
            case WEEKLY:
                frequency = "Weekly";
                break;
            case MONTHLY:
                frequency = "Monthly";
                break;
            case ANNUALLY:
                frequency = "Annually";
                break;
            case NEVER:
                frequency = "Never";
                break;
            default:
                frequency = "Never";
                break;
        }

        return frequency;
    }

    private Frequency stringToFrequencyConvert(String frequency) {
        Frequency DBFrequency;
        switch (frequency) {
            case "Once":
                DBFrequency = Frequency.ONCE;
                break;
            case "Daily":
                DBFrequency = Frequency.DAILY;
                break;
            case "Weekly":
                DBFrequency = Frequency.WEEKLY;
                break;
            case "Monthly":
                DBFrequency = Frequency.MONTHLY;
                break;
            case "Annually":
                DBFrequency = Frequency.ANNUALLY;
                break;
            case "Never":
                DBFrequency = Frequency.NEVER;
                break;
            default:
                DBFrequency = Frequency.NEVER;
                break;
        }
        return DBFrequency;
    }

    private int urgencyToIntegerConvert(Task task) {
        int urgency;

        switch (task.getUrgency())
        {
            case LOWEST:
                urgency = 1;
                break;
            case LOW:
                urgency = 2;
                break;
            case MEDIUM:
                urgency = 3;
                break;
            case HIGH:
                urgency = 4;
                break;
            case HIGHEST:
                urgency = 5;
                break;
            default:
                urgency = 1;
                break;
        }

        return urgency;
    }

    private Urgency integerToUrgencyConvert(int urgency) {
        Urgency DBUrgency;

        switch (urgency) {
            case 1:
                DBUrgency = Urgency.LOWEST;
                break;
            case 2:
                DBUrgency = Urgency.LOW;
                break;
            case 3:
                DBUrgency = Urgency.MEDIUM;
                break;
            case 4:
                DBUrgency = Urgency.HIGH;
                break;
            case 5:
                DBUrgency = Urgency.HIGHEST;
                break;
            default:
                DBUrgency = Urgency.LOWEST;
        }

        return DBUrgency;
    }

    private String dateToStringConvert(Task task) {
        int minute, hour, day, month, year;
        String date;

        minute = task.getDeadline().getMinute();
        hour = task.getDeadline().getHour();
        day = task.getDeadline().getDay();
        month = task.getDeadline().getMonth();
        year = task.getDeadline().getYear();

        //Build the date as YYYYMMDD HH:MM:SS 24-hr format
        date = String.valueOf(year) + datePad(month) + datePad(day)
                + " " + datePad(hour) + ":" + datePad(minute)
                + ":00";

        return date;
    }

    private static String datePad(int datePad) {
        if (datePad >= 10)
            return String.valueOf(datePad);
        else
            return "0" + String.valueOf(datePad);
    }

    private Date stringToDateConvert(String date) {
        Date DBDate;
        int year, month, day, hour, minute;

        year = Integer.parseInt(date.substring(0,4));
        month = Integer.parseInt(date.substring(4,6));
        day = Integer.parseInt(date.substring(6,8));

        hour = Integer.parseInt(date.substring(9,11));
        minute = Integer.parseInt(date.substring(12,14));

        DBDate = new Date(year, month, day, hour, minute);

        return DBDate;
    }

}