package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class Diary extends AppCompatActivity {
    //-----------------------------------------------------
    private static final String DataBaseName = "Note";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "note";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    // 建立SQLiteOpenHelper物件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //新增按鈕
        Button button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 獲取標題和內容的值
                EditText editTextTitle = findViewById(R.id.editTextTextMultiLine3);
                EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new SqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
                //新增資料庫
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content", content);
                // 插入資料到記事表
                long insertedId = db.insert(DataBaseTable,null,values);
                // 檢查是否成功插入資料
                if (insertedId != -1) {
                    Toast.makeText(getApplicationContext(), "記事新增成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "記事新增失敗", Toast.LENGTH_SHORT).show();
                }

            }


        });
        //修改按鈕
        Button button2= findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 獲取標題和內容的值
                EditText editTextTitle = findViewById(R.id.editTextTextMultiLine3);
                EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new SqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
                //修改資料庫
                ContentValues values = new ContentValues();
                //values.put("title", title);
                values.put("content", content);

                // 準備 WHERE 條件，根據標題更新對應的內容
                String whereClause = "title" + " = ?";
                String[] whereArgs = {title};

                // 更新資料庫中的記事項目
                int rowsUpdated = db.update(DataBaseTable, values, whereClause, whereArgs);
                // 檢查是否成功插入資料
                // 檢查是否成功更新資料
                if (rowsUpdated > 0) {
                    Toast.makeText(getApplicationContext(), "記事修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "記事修改失敗", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //歷史紀錄查看
        Button history_btn = (Button) findViewById(R.id.history_btn);
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(Diary.this, DiaryHistory.class);
                startActivity(history);

            }
        });



    }




}