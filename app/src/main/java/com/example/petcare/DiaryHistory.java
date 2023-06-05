package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiaryHistory extends AppCompatActivity {
    private static final String DataBaseName = "Note";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "note";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;

    private ListView listview_diary;
    private ArrayList<String> diaryList;
    private ArrayAdapter<String> diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_history);

        // 假設您的 ListView 的 ID 是 listViewNotes
        listview_diary = findViewById(R.id.listview_diary);
        sqlDataBaseHelper = new SqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        diaryList = new ArrayList<>();
        diaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diaryList);
        refresh();
        listview_diary.setAdapter(diaryAdapter);
        ListView listViewdiary2 = findViewById(R.id.listview_diary);

        listViewdiary2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 獲取長按的項目的標題
                String s = listViewdiary2.getItemAtPosition(position).toString();
                String[] title=s.split("\n");
                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new SqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

                // 準備 WHERE 條件，根據標題找到對應的記事項目
                String whereClause = "title" + " = ?";
                String[] whereArgs = {title[0]};

                // 刪除數據庫中的記事項目
                int rowsDeleted = db.delete(DataBaseTable, whereClause, whereArgs);

                // 檢查是否成功刪除資料
                if (rowsDeleted > 0) {
                    Toast.makeText(getApplicationContext(), "記事刪除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "記事刪除失敗", Toast.LENGTH_SHORT).show();
                }
                refresh();
                listViewdiary2.setAdapter(diaryAdapter);
                // 返回 true，表示長按事件已處理
                return true;
            }

        });


    }

    private void refresh() {
        diaryList.clear();

        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable, null);

        if (c.moveToFirst()) {
            do {
                String title = c.getString(1);
                String content = c.getString(2);
                String note = title + "\n" + content;
                diaryList.add(note);
            } while (c.moveToNext());
        }

        c.close();
        diaryAdapter.notifyDataSetChanged();
    }


}