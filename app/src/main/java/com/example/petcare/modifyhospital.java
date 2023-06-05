package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class modifyhospital extends AppCompatActivity {
    private static final String DataBaseName = "Note1";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "note1";
    private static SQLiteDatabase db;
    private hospitaldatabase sqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyhospital);

        //拿上一頁傳過來的值
        Intent intent = getIntent();
        String titleValue = intent.getStringExtra("title");
        String contentValue = intent.getStringExtra("content");
        String phoneValue = intent.getStringExtra("phone");
        //prc_showmessage(intent.getStringExtra("content"));   //用來測試拿到的值是什麼
        EditText editTextTitle = findViewById(R.id.editTextTextMultiLine3);
        editTextTitle.setText(titleValue);
        EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
        editTextContent.setText(contentValue);
        EditText editTextPhone = findViewById(R.id.editTextTextMultiLine4);
        editTextPhone.setText(phoneValue);

        Button button= findViewById(R.id.modify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 獲取標題和內容的值
                EditText editTextTitle = findViewById(R.id.editTextTextMultiLine3);
                EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
                EditText editTextPhone = findViewById(R.id.editTextTextMultiLine4);

                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                String phone = editTextPhone.getText().toString();
                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new hospitaldatabase(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
                //修改資料庫
                ContentValues values = new ContentValues();
                //values.put("title", title);
                values.put("content", content);
                values.put("phone", phone);

                // 準備 WHERE 條件，根據標題更新對應的內容
                String whereClause = "title" + " = ?";
                String[] whereArgs = {titleValue};

                // 更新資料庫中的記事項目
                int rowsUpdated = db.update(DataBaseTable, values, whereClause, whereArgs);
                // 檢查是否成功插入資料
                // 檢查是否成功更新資料
                if (rowsUpdated > 0) {
                    Toast.makeText(getApplicationContext(), "記事修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(modifyhospital.this, common.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "記事修改失敗", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }
    //測試用->顯示傳過來的值
    public void prc_showmessage(String strmessage)
    {
        Toast objtoast = Toast.makeText(this,strmessage, Toast.LENGTH_SHORT);
        objtoast.show();
    }
}