package com.example.ico.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Vlado on 26.11.2017 г..
 */

public class DBHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;
    public static final String LOG = "dbException";

    public static final String DB_NAME = "dbToDoList.db";
    public static final int DB_VERSION = 1;
    //Tale task
    public static final String TABLE_TASK = "task";
    //table task columns
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_COLUMN_TASK = "task";
    public static final String CREATE_TABLE_TASK="CREATE TABLE "+TABLE_TASK+"("+
            "'" + TASK_COLUMN_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TASK_COLUMN_TASK + "' TEXT NOT NULL)";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK);
        onCreate(sqLiteDatabase);
    }
public void addTask(Task task,Context context){

    try {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TASK_COLUMN_TASK, task.getWord());

        db.insertOrThrow(TABLE_TASK, null, cv);
    }catch (SQLException e){
        Toast.makeText(context, "Something's wrong!!!",
                Toast.LENGTH_LONG).show();
        Log.e(LOG, e.getMessage());
    }finally {
        if(db != null)
            db.close();
    }
}


    public void deleteTask(String task){
        try {
            db =getWritableDatabase();
            db.execSQL("delete from "+TABLE_TASK+" where task ='"+task+"'");
        }catch (SQLException e){
            Log.e(LOG, e.getMessage());
        }finally {
            if (db != null)
                db.close();
        }
    }
    public void updateTask(String task, String newTask, Context context) {
        Cursor c = null;
        ArrayList<Integer> list=new ArrayList<Integer>();
        try {
            String query = "SELECT "+TASK_COLUMN_ID+" FROM " + TABLE_TASK+" where task ='" + task + "'";

            db =getReadableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {

                    list.add(c.getInt(c.getColumnIndex(TASK_COLUMN_ID)));


                } while (c.moveToNext());
            }
            db = getWritableDatabase();

            db.execSQL("update " + TABLE_TASK + " set task ='" + newTask + "' where "+TASK_COLUMN_ID+" = " + list.get(0) );
        } catch (SQLException e) {
            Toast.makeText(context, "Something's wrong!!!",
                    Toast.LENGTH_LONG).show();
            Log.e(LOG, e.getMessage());
        } finally {

            if (db != null)
                db.close();
        }
    }

    public ArrayList<String> getAllTask(){
        ArrayList<String> list =new ArrayList<String>();
        Cursor c = null;

        try {
            String query = "SELECT * FROM " + TABLE_TASK;

            db =getReadableDatabase();
            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {

                    list.add(c.getString(c.getColumnIndex(TASK_COLUMN_TASK)));


                } while (c.moveToNext());
            }
        }catch (SQLException e){
            Log.e(LOG, e.getMessage());
        }finally {
            if(c != null)
                c.close();
            if(db != null)
                db.close();
        }
        return list;
    }

}
