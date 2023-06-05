package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Weight extends AppCompatActivity {
    //-----------------------------------------------------
    private static final String DataBaseName = "Health";
    private static final int DataBaseVersion = 1;
    private static final String DataBaseTable = "weight";
    private static SQLiteDatabase db;
    private HealthDataBaseHelper healthDataBaseHelper;
    // 建立SQLiteOpenHelper物件

    private ListView listViewWeight;
    private ArrayList<String> weightList;
    private ArrayAdapter<String> weightAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        listViewWeight = findViewById(R.id.listViewWeight);
        healthDataBaseHelper = new HealthDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        weightList = new ArrayList<>();
        weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weightList);
        refreshWeightList();
        listViewWeight.setAdapter(weightAdapter);

        ListView listViewWeight2 = findViewById(R.id.listViewWeight);
        listViewWeight2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 獲取長按的項目的標題
                String s = listViewWeight2.getItemAtPosition(position).toString();
                String[] weight=s.split("\n");
                //----------資料庫開啟(一定要再create裡)--------
                healthDataBaseHelper = new HealthDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = healthDataBaseHelper.getWritableDatabase(); // 開啟資料庫

                // 準備 WHERE 條件，根據標題找到對應的記事項目
                String whereClause = "_id" + " = ?";
                String[] whereArgs = {weight[0]};

                // 刪除數據庫中的記事項目
                int rowsDeleted = db.delete(DataBaseTable, whereClause, whereArgs);

                // 檢查是否成功刪除資料
                if (rowsDeleted > 0) {
                    Toast.makeText(getApplicationContext(), "記事刪除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "記事刪除失敗", Toast.LENGTH_SHORT).show();
                }
                refreshWeightList();
                listViewWeight2.setAdapter(weightAdapter);
                // 返回 true，表示長按事件已處理
                return true;
            }

        });
    }
    private void refreshWeightList() {
        weightList.clear();

        db = healthDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseTable, null);

        if (cursor.moveToFirst()) {
            do {
                int id=cursor.getInt(0);
                String weight = cursor.getString(1);
                weightList.add(id+"\n"+weight+"(kg)");
            } while (cursor.moveToNext());
        }

        cursor.close();
        weightAdapter.notifyDataSetChanged();
    }

}