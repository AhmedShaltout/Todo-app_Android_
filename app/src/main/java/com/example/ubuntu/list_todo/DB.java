package com.example.ubuntu.list_todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DB extends SQLiteOpenHelper {

    private static final String db = "todo_db";
    private static final String TABLE = "Tasks_info";
    private static final String col_0 = "id";
    private static final String col_1 = "title";
    private static final String col_2 = "date";
    private static final String col_3 = "body";
    private static final String col_4 = "done";
    private static final int version = 14;
    public DB(Context context) {
        super(context, db , null,version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE +" " +
                "(" +
                ""+col_0+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+col_1+" TEXT, " +
                ""+col_2+" DATE DEFAULT (datetime('now')), " +
                ""+col_3+" TEXT," +
                ""+col_4+" INTEGER DEFAULT 0"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public boolean addTask(String title, String body){
        SQLiteDatabase db = this.getWritableDatabase();
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cDate);
        ContentValues values = new ContentValues();
        values.put(col_1,title);
        values.put(col_2, fDate);
        values.put(col_3,body);
        if((db.insert(TABLE, null, values)) > -1 ){
            db.close();
            return true;
        }else{
            db.close();
            return false;
        }
    }

    public Task[] getTodo(){
        return createTasks(0);
    }

    public Task[] getDone(){
        return createTasks(1);
    }

    public Task getTask(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE +" WHERE "+col_0+" = "+value+"",null);
        Task task = null;
        if(res.moveToFirst()) {
            task = new Task(res.getInt(0),res.getString(1),res.getString(2),res.getString(3));
        }
        db.close();
        return task;
    }
    private Task[] createTasks(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE +" WHERE done = "+value+"",null);
        Task[] tasks = new Task[res.getCount()];
        if(res.moveToFirst()) {
            int index = 0;
            do{
                tasks[index] = new Task(res.getInt(0),res.getString(1),res.getString(2),res.getString(3));
                index++;
            }while (res.moveToNext());
        }
        db.close();
        return tasks;
    }

    public boolean updateTask(int id, String title, String body){
        ContentValues values = new ContentValues();
        values.put(col_1, title);
        values.put(col_3, body);
        return update(id, values);
    }

    public boolean markDone(int id){
        ContentValues values = new ContentValues();
        values.put(col_4, new Integer(1));
        return update(id, values);

    }

    private boolean update(int id, ContentValues values){

        SQLiteDatabase db = this.getWritableDatabase();
        if(db.update(TABLE, values, "id ="+id, null)>0){
            db.close();
            return true;
        }else{
            db.close();
            return false;
        }
    }

    public boolean deleteData (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE, "ID = "+id, null)>0){
            db.close();
            return true;
        }else{
            db.close();
            return false;
        }
    }

}
