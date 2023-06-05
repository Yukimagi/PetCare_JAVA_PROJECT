package com.example.petcare;
//***以下複製
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class hospitaldatabase extends SQLiteOpenHelper {
    private static final String DataBaseName = "Note1";//table名稱
    private static final int DataBaseVersion = 1;//版本1

    //下面建立建構子
    public hospitaldatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS note1 (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text not null," +
                "content TEXT not null," +
                "phone TEXT not null" +
                ")";
        db.execSQL(SqlTable);

        String SqlTable1 = "CREATE TABLE IF NOT EXISTS note2 (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text not null," +
                "content TEXT not null," +
                "phone TEXT not null" +
                ")";
        db.execSQL(SqlTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        final String SQL = "DROP TABLE note1";
        db.execSQL(SQL);

        final String SQL1 = "DROP TABLE note2";
        db.execSQL(SQL1);
    }

}
