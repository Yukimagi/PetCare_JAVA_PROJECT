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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class Account extends AppCompatActivity {

    private static final String DataBaseName = "Noteaccount";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "account1";
    private static SQLiteDatabase db;
    private accountdata sqlDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        show();

        //新增按鈕
        Button button= findViewById(R.id.addbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Account.this, addacount.class);
                startActivity(intent);
            }
        });

        Button button1= findViewById(R.id.piebutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Account.this, piechart.class);
                startActivity(intent);
            }
        });


        // 假設您的 ListView 的 ID 是 listViewNotes
        ListView listViewNotes = findViewById(R.id.listViewNotes);
        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 獲取長按的項目的標題

                String s = listViewNotes.getItemAtPosition(position).toString();
                String[] title=s.split("\n");

                // 提取價錢部分
                String money = title[3].split("\\$")[1].trim();


                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new accountdata(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

                // 準備 WHERE 條件，根據標題找到對應的記事項目

                String whereClause = "title" + " = ?";
                String[] whereArgs = {title[1]};

                AlertDialog.Builder builder = new AlertDialog.Builder(Account.this);
                builder.setTitle("編輯")
                        .setMessage("修改還是刪除")
                        .setPositiveButton("刪除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 刪除數據庫中的記事項目
                                int rowsDeleted = db.delete(DataBaseTable, whereClause, whereArgs);
                                // System.out.println(whereClause+whereArgs);

                                // 檢查是否成功刪除資料
                                if (rowsDeleted > 0) {
                                    Toast.makeText(getApplicationContext(), "記事刪除成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "記事刪除失敗", Toast.LENGTH_SHORT).show();
                                }


                                show();
                            }
                        })

                        .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(Account.this, modifyaccount.class);

                                //傳值到下一頁
                                intent.putExtra("title",title[0].toString());
                                intent.putExtra("content",title[1].toString());
                                intent.putExtra("other",title[2].toString());
                                intent.putExtra("money",money.toString());
                                startActivity(intent);
                            }
                        });



                builder.show();

                return false;


            }

        });
    }
    public void show() {

        //----------資料庫開啟(一定要再create裡)--------
        sqlDataBaseHelper = new accountdata(getApplicationContext(), DataBaseName, null, DataBaseVersion, DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable, null);
        // 建立適配器，使用 ArrayAdapter 作為範例
        ArrayList<String> notesList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String title = c.getString(1);
                String content = c.getString(2);
                String other = c.getString(3);
                String date = c.getString(4);
                //String note1 =  date + "\n" + title + " " + other + " $"+ content;
                String note1 =  date + "\n" + title + "\n" + other + "\n"+ "$" + content;

                notesList.add(note1);
            } while (c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                notesList
        );

        // 設定適配器給 ListView
        ListView listViewNotes = findViewById(R.id.listViewNotes);
        listViewNotes.setAdapter(adapter);

    }
}