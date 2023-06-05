package com.example.petcare;
//***以下複製
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class accountdata extends SQLiteOpenHelper {
    private static final String DataBaseName = "Noteaccount";//table名稱
    private static final int DataBaseVersion = 1;//版本2

    //下面建立建構子
    public accountdata(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //記帳資料庫
        String SqlTable1 = "CREATE TABLE IF NOT EXISTS account1 (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text not null," +
                "content TEXT not null," +
                "other TEXT not null," +
                "date TEXT not null" +
                ")";
        db.execSQL(SqlTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL1 = "DROP TABLE account";
        db.execSQL(SQL1);
    }

}
