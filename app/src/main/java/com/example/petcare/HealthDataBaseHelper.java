package com.example.petcare;
//***以下複製
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HealthDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "Health";//database名稱
    private static final int DataBaseVersion = 1;//版本1

    //下面建立建構子
    public HealthDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Weight = "CREATE TABLE IF NOT EXISTS weight (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "weight text not null" +
                ")";
        db.execSQL(Weight);

        String SqlTable = "CREATE TABLE IF NOT EXISTS med (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text not null," +
                "content TEXT not null" +
                ")";
        db.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        final String SQL1 = "DROP TABLE weight";
        db.execSQL(SQL1);

        final String SQL2 = "DROP TABLE med";
        db.execSQL(SQL2);
    }
}
