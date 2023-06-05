package com.example.petcare.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.petcare.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public static final String DATABASE_NAME = "TODO_DATABASE";
    public static final String TABLE_NAME = "TODO_TABLE";
    public static final String COL_1="ID";
    public static final String COL_2="TASK";
    public static final String COL_3="STATUS";

    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insertTask(ToDoModel model){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, model.getTask());
        values.put(COL_3, 0);
        db.insert(TABLE_NAME, null, values);
    }
    public void updateTask(int id, String task){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id, int status){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_3, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id ){
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }
    public List<ToDoModel> getAllTasks(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(COL_1);
                    int taskIndex = cursor.getColumnIndex(COL_2);
                    int statusIndex = cursor.getColumnIndex(COL_3);

                    do {
                        ToDoModel task = new ToDoModel();
                        if (idIndex >= 0)
                            task.setId(cursor.getInt(idIndex));
                        if (taskIndex >= 0)
                            task.setTask(cursor.getString(taskIndex));
                        if (statusIndex >= 0)
                            task.setStatus(cursor.getInt(statusIndex));
                        modelList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
