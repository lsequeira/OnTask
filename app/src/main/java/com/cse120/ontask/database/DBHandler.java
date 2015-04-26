package com.cse120.ontask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cse120.ontask.task_attributes.Date;
import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Task;
import com.cse120.ontask.task_attributes.Urgency;

import java.util.ArrayList;
import java.util.Random;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskDB.db";
    private static final String TASK_TABLE = "task_table";
    private static final String PROJECT_TABLE = "project_table";

    /*-----------Table Columns--------------*/
    //Task
    //TODO: ADD TASK UNIQUE ID AND USER ID
    //TODO: LOAD TASKS INTO CORRESPONDING PROJECTS BASED ON UNIQUE PROJECT ID
    public static final String COLUMN_TASK_KEY = "task_key";
    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK_DESCRIPTION = "description";
    public static final String COLUMN_TASK_DEADLINE = "deadline";
    public static final String COLUMN_TASK_URGENCY = "urgency";
    public static final String COLUMN_TASK_FOR_PROJECT = "for_project";
    public static final String COLUMN_TASK_PROJECT_ID = "project_id";
    public static final String COLUMN_TASK_IS_COMPLETE = "is_complete";

    //Project
    public static final String COLUMN_PROJECT_KEY = "project_key";
    public static final String COLUMN_PROJECT_ID = "project_id";
    public static final String COLUMN_PROJECT_TITLE = "title";
    public static final String COLUMN_PROJECT_DESCRIPTION = "description";
    public static final String COLUMN_PROJECT_DEADLINE = "deadline";
    public static final String COLUMN_PROJECT_URGENCY = "urgency";
    public static final String COLUMN_PROJECT_IS_COMPLETE = "is_complete";
    /*-----------Table Columns END--------------*/

    Random genProjID;

    public DBHandler(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        //Delete Database with line below
        //context.deleteDatabase(DATABASE_NAME);

        genProjID = new Random();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE + "("+
                COLUMN_TASK_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TASK_TITLE + " TEXT," +
                COLUMN_TASK_DESCRIPTION + " TEXT," +
                COLUMN_TASK_DEADLINE + " TEXT," +
                COLUMN_TASK_URGENCY + " INTEGER, " +
                COLUMN_TASK_FOR_PROJECT + " BOOLEAN, " +
                COLUMN_TASK_PROJECT_ID + " INTEGER, " +
                COLUMN_TASK_IS_COMPLETE + " BOOLEAN " +
                ")";
        db.execSQL(CREATE_TASK_TABLE);

        String CREATE_PROJECT_TABLE = "CREATE TABLE " + PROJECT_TABLE + "("+
                COLUMN_PROJECT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TASK_TITLE + " TEXT," +
                COLUMN_PROJECT_ID + " INTEGER, " +
                COLUMN_PROJECT_DESCRIPTION + " TEXT," +
                COLUMN_PROJECT_DEADLINE + " TEXT," +
                COLUMN_PROJECT_URGENCY + " INTEGER, " +
                COLUMN_PROJECT_IS_COMPLETE + " BOOLEAN " +
                ")";
        db.execSQL(CREATE_PROJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    /* Database Handler Functions */
    public void addTask(Task task) {
        int prevRowID = 0;

        String query = "SELECT * FROM " + TASK_TABLE + " ORDER BY " + COLUMN_TASK_KEY
                + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            prevRowID = Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        task.setTaskAutoIncKey(prevRowID+1);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DEADLINE, dateToStringConvert(task.getDeadline()));
        values.put(COLUMN_TASK_URGENCY, urgencyToIntegerConvert(task.getUrgency()));
        values.put(COLUMN_TASK_FOR_PROJECT, task.getForProject());
        values.put(COLUMN_TASK_PROJECT_ID, task.getTaskProject_id());
        values.put(COLUMN_TASK_IS_COMPLETE, task.getIsCompleted());

        db = this.getWritableDatabase();

        db.insert(TASK_TABLE, null, values);
        db.close();
    }

    public void addProject(Project project) {
        int prevRowID = 0;

        //Get the previous max autoincremented row key
        String query = "SELECT * FROM " + PROJECT_TABLE + " ORDER BY " + COLUMN_PROJECT_KEY
                + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            prevRowID = Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        //Set what will be the next auto incremented key
        project.setProjectAutoIncKey(prevRowID+1);

        //Range from 0 to 2^30
        project.setProject_id(genProjID.nextInt(1073741824));


        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_TITLE, project.getTitle());

        //note that project_key, rather than project_id, refers to the auto increment key
        values.put(COLUMN_PROJECT_ID, project.getProject_id());
        values.put(COLUMN_PROJECT_DESCRIPTION, project.getDescription());
        values.put(COLUMN_PROJECT_DEADLINE, dateToStringConvert(project.getDeadline()));
        values.put(COLUMN_PROJECT_URGENCY, urgencyToIntegerConvert(project.getUrgency()));
        values.put(COLUMN_PROJECT_IS_COMPLETE, project.getIsCompleted());

        db = this.getWritableDatabase();
        db.insert(PROJECT_TABLE, null, values);
        db.close();
    }

    public void updateTask(Task task){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DEADLINE, dateToStringConvert(task.getDeadline()));
        values.put(COLUMN_TASK_URGENCY, urgencyToIntegerConvert(task.getUrgency()));
        values.put(COLUMN_TASK_IS_COMPLETE, task.getIsCompleted());

        String whereClause = COLUMN_TASK_KEY + "=" + task.getTaskAutoIncKey();
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TASK_TABLE, values, whereClause, null);
        db.close();
    }

    public void updateProject(Project project) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_TITLE, project.getTitle());
        values.put(COLUMN_PROJECT_ID, project.getProject_id());
        values.put(COLUMN_PROJECT_DESCRIPTION, project.getDescription());
        values.put(COLUMN_PROJECT_DEADLINE, dateToStringConvert(project.getDeadline()));
        values.put(COLUMN_PROJECT_URGENCY, urgencyToIntegerConvert(project.getUrgency()));
        values.put(COLUMN_TASK_IS_COMPLETE, project.getIsCompleted());

        System.out.println("chk proj key: " + project.getProjectAutoIncKey());
        String whereClause = COLUMN_PROJECT_KEY + "=" + project.getProjectAutoIncKey();
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(PROJECT_TABLE, values, whereClause, null);
        db.close();
    }

    public ArrayList<Task> loadTasks(boolean loadCompletedTasks) {
        ArrayList<Task> DBTasks = new ArrayList<Task>();
        String query = "SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_TASK_PROJECT_ID + " = -1";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int key = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            Date deadline = stringToDateConvert(cursor.getString(3));
            Urgency urgency = integerToUrgencyConvert(Integer.parseInt(cursor.getString(4)));

            //booleans are stored as int 0 == false, 1 == true
            int forProjectCheck = Integer.parseInt(cursor.getString(5));
            boolean forProject = false;
            if(forProjectCheck == 1){
                forProject = true;
            }

            int taskProject_id = Integer.parseInt(cursor.getString(6));

            //check if the task being loaded is complete
            int isCompleteCheck = Integer.parseInt(cursor.getString(7));
            boolean isComplete;
            Task currentTask;
            if(isCompleteCheck == 1) {
                isComplete = true;
            }
            else{
                isComplete = false;
            }

            currentTask = new Task(key, title, description, deadline, urgency, forProject, taskProject_id, isComplete);
            if(loadCompletedTasks && isComplete){
                DBTasks.add(currentTask);
            }
            else if(!loadCompletedTasks && !isComplete){
                DBTasks.add(currentTask);
            }
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

    public ArrayList<Project> loadProjects(boolean loadCompletedProjects) {
        ArrayList<Project> DBProjects = new ArrayList<Project>();
        String query = "SELECT * FROM " + PROJECT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int key = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            int id = Integer.parseInt(cursor.getString(2));
            String description = cursor.getString(3);
            Date deadline = stringToDateConvert(cursor.getString(4));
            Urgency urgency = integerToUrgencyConvert(Integer.parseInt(cursor.getString(5)));

            //check if the task being loaded is complete
            int isCompleteCheck = Integer.parseInt(cursor.getString(6));
            boolean isComplete;
            if(isCompleteCheck == 1) {
                isComplete = true;
            }
            else{
                isComplete = false;
            }

            Project currentProject = new Project(key, title, id, description, deadline, urgency, isComplete);
            if(loadCompletedProjects && isComplete){
                DBProjects.add(currentProject);
            }
            else if(!loadCompletedProjects && !isComplete){
                DBProjects.add(currentProject);
            }

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        if (DBProjects.size() == 0)
        {
            DBProjects = null;
        }

        return DBProjects;
    }

    public boolean deleteTask(Task task) {
        boolean result = false;
        Task DBTask = new Task();

        String query = "SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_TASK_KEY + " = " + task.getTaskAutoIncKey();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            DBTask.setTaskAutoIncKey(Integer.parseInt(cursor.getString(0)));
            db.delete(TASK_TABLE, COLUMN_TASK_KEY + " = ?",
                    new String[] { String.valueOf(DBTask.getTaskAutoIncKey()) });
            cursor.close();
            result = true;
        }
        cursor.close();
        db.close();

        return result;
    }

    public boolean deleteProject(Project project){
        boolean result = false;
        Project DBProject = new Project();

        String query = "SELECT * FROM " + PROJECT_TABLE + " WHERE " + COLUMN_PROJECT_KEY + " = " + project.getProjectAutoIncKey();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            DBProject.setProjectAutoIncKey(Integer.parseInt(cursor.getString(0)));
            db.delete(PROJECT_TABLE, COLUMN_PROJECT_KEY + " = ?",
                    new String[] { String.valueOf(DBProject.getProjectAutoIncKey()) });
            cursor.close();
            result = true;
        }
        cursor.close();
        db.close();

        return result;
    }

    /* End Database Handler Functions */

    private int urgencyToIntegerConvert(Urgency urgency) {
        int urg;
        switch (urgency)
        {
            case LOWEST:
                urg = 1;
                break;
            case LOW:
                urg = 2;
                break;
            case MEDIUM:
                urg = 3;
                break;
            case HIGH:
                urg = 4;
                break;
            case HIGHEST:
                urg = 5;
                break;
            default:
                urg = 1;
                break;
        }

        return urg;
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

    private String dateToStringConvert(Date deadline) {
        String date;

        //Build the date as YYYYMMDD HH:MM:SS 24-hr format
        date = String.valueOf(deadline.getYear()) + datePad(deadline.getMonth()) + datePad(deadline.getDay())
                + " " + datePad(deadline.getHour()) + ":" + datePad(deadline.getMinute())
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