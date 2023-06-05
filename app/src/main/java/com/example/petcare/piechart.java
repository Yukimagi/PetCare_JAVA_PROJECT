package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;


// Import the required libraries
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class piechart extends AppCompatActivity {


    private static final String DataBaseName = "Noteaccount";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "account1";
    private static SQLiteDatabase db;
    private accountdata sqlDataBaseHelper;


    TextView food,hos,live,play;
    PieChart pieChart;

    int playMoney = 0;
    int liveMoney = 0;
    int hosMoney = 0;
    int foodMoney = 0;
    HashMap<String, Integer> colorMap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);


        food = findViewById(R.id.food);
        hos = findViewById(R.id.hos);
        live = findViewById(R.id.live);
        play = findViewById(R.id.play);
        pieChart = findViewById(R.id.pie);


        setData();
    }

    private void setData()
    {

        //----------資料庫開啟(一定要再create裡)--------
        sqlDataBaseHelper = new accountdata(getApplicationContext(), DataBaseName, null, DataBaseVersion, DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫


        Cursor c = db.rawQuery("SELECT title, SUM(content) FROM " + DataBaseTable + " GROUP BY title", null);


        ArrayList<String> categoryList = new ArrayList<>();
        ArrayList<Integer> moneyList = new ArrayList<>();
        ArrayList<String> typeList = new ArrayList<>();


        if (c.moveToFirst()) {
            do {
                String title = c.getString(0);
                int totalMoney = c.getInt(1);
                if (title.equals("玩具")) {
                    playMoney = totalMoney;
                } else if (title.equals("生活用品")) {
                    liveMoney = totalMoney;
                } else if (title.equals("醫療")) {
                    hosMoney = totalMoney;
                } else if (title.equals("飲食")) {
                    foodMoney = totalMoney;
                }
                typeList.add(title);
                categoryList.add(title + " ($" + totalMoney + ")");
                moneyList.add(totalMoney);
                //System.out.println(title + " ($" + totalMoney + ")");
            } while (c.moveToNext());
        }




// 循環遍歷 categoryList，并添加對應的 PieSlice
        for (int i = 0; i < categoryList.size(); i++) {
            String category = categoryList.get(i);
            String type=typeList.get(i);
            int money = moneyList.get(i);
            int color = getColorForCategory(type);
            //System.out.println(type);

            // 創建 PieModel，並添加到圓餅圖中
            pieChart.addPieSlice(
                    new PieModel(
                            category,
                            money,
                            color));

        }
        play.setText(Integer.toString(playMoney));
        live.setText(Integer.toString(liveMoney));
        hos.setText(Integer.toString(hosMoney));
        food.setText(Integer.toString(foodMoney));



        pieChart.startAnimation();
    }

    private int getColorForCategory(String title) {
        switch (title) {
            case "玩具":
                return Color.parseColor("#4CAF50");
            case "醫療":
                return Color.parseColor("#B73A3A");
            case "生活用品":
                return Color.parseColor("#00BCD4");
            case "飲食":
                return Color.parseColor("#FF9800");
            default:
                return Color.GRAY; // 預設為灰色
        }
    }




}
