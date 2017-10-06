package com.example.ubuntu.list_todo.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DB extends SQLiteOpenHelper {

    private static final String db = "todo_db";
    private static final String TABLE = "Tasks_info";
    private static final String col_0 = "id";
    private static final String col_1 = "title";
    private static final String col_2 = "date";
    private static final String col_3 = "body";
    private static final String col_4 = "done";
    private static final String col_5 = "dateDone";
    private static final int version = 16;
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
                ""+col_3+" TEXT, " +
                ""+col_4+" INTEGER DEFAULT 0, " +
                ""+col_5+" DATE " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    private String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    public boolean addTask(String title, String body){
        SQLiteDatabase db = this.getWritableDatabase();
        String fDate = getDate();
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
        return createTasks(0,"ORDER BY "+col_0+" DESC");
    }

    public Task[] getDone(){
        return createTasks(1, "ORDER BY "+col_5+" DESC");
    }

    public Task getTask(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE +" WHERE "+col_0+" = "+value+"",null);
        Task task = null;
        if(res.moveToFirst()) {
            String date = "Created: "+res.getString(2);
            if(res.getInt(4)>0){
                date +="\nFinished:"+res.getString(5);
            }
            task = new Task(res.getInt(0),res.getString(1),date,res.getString(3));
        }
        db.close();
        res.close();
        return task;
    }

    public String getTaskDate(int id){SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE +" WHERE "+col_0+" = "+id+"",null);
        if(res.moveToFirst()) {
            String date = "Created: "+res.getString(2);
            if(res.getInt(4)>0){
                date +="\nFinished:"+res.getString(5);
            }
            db.close();
            res.close();
            return date;
        }
        return "";
    }
    private Task[] createTasks(int value, String order){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE +" WHERE done = "+value+" "+order,null);
        Task[] tasks = new Task[res.getCount()];
        if(res.moveToFirst()) {
            int index = 0;
            do{
                String date = "Created: "+res.getString(2);
                if(res.getInt(4)>0){
                    date +="\nFinished:"+res.getString(5);
                }
                tasks[index] = new Task(res.getInt(0), res.getString(1), date,res.getString(3));
                index++;
            }while (res.moveToNext());
        }
        db.close();
        res.close();
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
        values.put(col_4, 1);
        values.put(col_5, getDate());
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
