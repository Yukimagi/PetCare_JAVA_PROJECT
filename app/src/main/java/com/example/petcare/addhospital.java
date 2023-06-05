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

public class addhospital extends AppCompatActivity {

    private static final String DataBaseName = "Note1";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "note1";
    private static SQLiteDatabase db;
    private hospitaldatabase sqlDataBaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhospital);

        //新增完成按鈕
        Button button= findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 獲取標題和內容的值
                EditText editTextTitle = findViewById(R.id.editTextTextMultiLine3);
                EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
                EditText editTextPhone = findViewById(R.id.editTextTextMultiLine4);
                //EditText editTextOther = findViewById(R.id.editTextTextMultiLine5);
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                String phone = editTextPhone.getText().toString();
                //String other = editTextOther.getText().toString();
                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new hospitaldatabase(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
                //新增資料庫
                ContentValues values = new ContentValues();


                values.put("title", title);
                values.put("content", content);
                values.put("phone", phone);
                //values.put("other", other);
                // 插入資料到記事表
                long insertedId = db.insert(DataBaseTable,null,values);
                // 檢查是否成功插入資料
                if (insertedId != -1) {
                    Toast.makeText(getApplicationContext(), "記事新增成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(addhospital.this, common.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "記事新增失敗", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }
}