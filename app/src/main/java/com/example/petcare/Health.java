package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Health extends AppCompatActivity {

    //-----------------------------------------------------
    private static final String DataBaseName = "Health";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "weight";
    private static String DataBaseTable2 = "med";
    private static SQLiteDatabase db;
    private HealthDataBaseHelper healthDataBaseHelper;
    // 建立SQLiteOpenHelper物件

    //----------------------------------------------
    private EditText editTextWeight;
    private Button buttonAdd;
    //建立體重紀錄會用到的
    String pet;
    boolean click=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        //下面是透過轉換radio button轉換照片
        RadioButton radioButtonCat = findViewById(R.id.radioButton_cat);
        RadioButton radioButtonDog = findViewById(R.id.radioButton_dog);

        radioButtonCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView imageView = findViewById(R.id.imageView);

                if (isChecked) {
                    imageView.setImageResource(R.drawable.cat);
                    pet="貓咪";
                    click=true;
                }
            }
        });

        radioButtonDog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView imageView = findViewById(R.id.imageView);

                if (isChecked) {
                    imageView.setImageResource(R.drawable.dog);
                    pet="狗狗";
                    click=true;
                }
            }
        });
        //以下是用藥需求
        show();
        Button button= findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 獲取標題和內容的值
                EditText editTextTitle = findViewById(R.id.editTextTitle);
                EditText editTextContent = findViewById(R.id.editTextContent);
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                //----------資料庫開啟(一定要再create裡)--------
                healthDataBaseHelper = new HealthDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable2);
                db = healthDataBaseHelper.getWritableDatabase(); // 開啟資料庫
                //新增資料庫
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content", content);
                // 插入資料到記事表
                long insertedId = db.insert(DataBaseTable2,null,values);
                // 檢查是否成功插入資料
                if (insertedId != -1) {
                    Toast.makeText(getApplicationContext(), "用藥新增成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用藥新增失敗", Toast.LENGTH_SHORT).show();
                }
                show();

            }
        });

        ListView listViewMed = findViewById(R.id.listViewMed);
        listViewMed.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 獲取長按的項目的標題
                String s = listViewMed.getItemAtPosition(position).toString();
                String[] title=s.split("\n");
                //----------資料庫開啟(一定要再create裡)--------
                healthDataBaseHelper = new HealthDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable2);
                db = healthDataBaseHelper.getWritableDatabase(); // 開啟資料庫

                // 準備 WHERE 條件，根據標題找到對應的記事項目
                String whereClause = "title" + " = ?";
                String[] whereArgs = {title[0]};

                // 刪除數據庫中的記事項目
                int rowsDeleted = db.delete(DataBaseTable2, whereClause, whereArgs);

                // 檢查是否成功刪除資料
                if (rowsDeleted > 0) {
                    Toast.makeText(getApplicationContext(), "用藥刪除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用藥刪除失敗", Toast.LENGTH_SHORT).show();
                }
                show();
                // 返回 true，表示長按事件已處理
                return true;
            }

        });


        //以下是顯示歷史體重紀錄換頁
        //寵物保健
        Button btnhistory = (Button) findViewById(R.id.button4);
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(Health.this, Weight.class);
                startActivity(history);

            }
        });

        //以下為體重紀錄
        editTextWeight = findViewById(R.id.editTextNumber);
        buttonAdd = findViewById(R.id.button3);

        healthDataBaseHelper = new HealthDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(click) {
                        String weight = editTextWeight.getText().toString();
                        float w = Float.parseFloat(weight);
                        addWeightToDatabase(weight);
                        editTextWeight.setText("");
                        TextView suggest = (TextView) findViewById(R.id.textView4);
                        if (pet.equals("貓咪")) {
                            calfood_cat(w);
                            suggest.setText("您的" + pet + "的體重為" + w + "kg\r\n" +
                                    "因此我們會建議您的" + pet + "的食物熱量為" + hot + "\r\n" +
                                    "請繼續關注您的" + pet + "的體重，並依照對應的食物熱量調整飲食!");
                        }
                        if (pet.equals("狗狗")) {
                            calfood_dog(w);
                            suggest.setText("您的" + pet + "的體重為" + w + "kg\r\n" +
                                    "因此我們會建議您的" + pet + "的食物熱量為" + hot + "\r\n" +
                                    "請繼續關注您的" + pet + "的體重，並依照對應的食物熱量調整飲食!");
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "請按貓或狗，並輸入體重", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception ex){
                    Toast.makeText(getApplicationContext(), "請按貓或狗，並輸入體重", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void show() {
        //----------資料庫開啟(一定要再create裡)--------
        healthDataBaseHelper = new HealthDataBaseHelper(getApplicationContext(), DataBaseName, null, DataBaseVersion, DataBaseTable2);
        db = healthDataBaseHelper.getWritableDatabase(); // 開啟資料庫

        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable2, null);
        // 建立適配器，使用 ArrayAdapter 作為範例
        ArrayList<String> medList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String title = c.getString(1);
                String content = c.getString(2);
                String med = title + "\n" + content;
                medList.add(med);
            } while (c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                medList
        );

        // 設定適配器給 ListView
        ListView listViewMed = findViewById(R.id.listViewMed);
        listViewMed.setAdapter(adapter);
    }
    int i=0;
    private void addWeightToDatabase(String weight) {
        SQLiteDatabase database = healthDataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", i);
        values.put("weight", weight);
        database.insert("weight", null, values);
        i++;
    }
    String hot;
    private void calfood_cat(float w){

        if (w < 1 || w == 1){
            hot="84";
        }
        else if(w > 1 && w < 1.5){
            hot="114";
        }
        else if(w==1.5||(w>1.5f&&w<2f)){
            hot="141";
        }
        else if(w==2||(w>2f&&w<2.5f)){
            hot="167";
        }
        else if(w==2.5||(w>2.5f&&w<3.5f)){
            hot="191";
        }
        else if(w==3.5||(w>3.5f&&w<4f)){
            hot="215";
        }
        else if(w==4||(w>4f&&w<4.5f)){
            hot="238";
        }
        else if(w==4.5||(w>4.5f&&w<5f)){
            hot="259";
        }
        else if(w==5||(w>5f&&w<6f)){
            hot="280";
        }
        else if(w==6||(w>6f&&w<7f)){
            hot="322";
        }
        else if(w==7||(w>7f&&w<8f)){
            hot="361";
        }
        else if(w==8||(w>8f&&w<9f)){
            hot="400";
        }
        else if(w==9||(w>9f&&w<10f)){
            hot="436";
        }
        else if(w==10||(w>10f&&w<11f)){
            hot="472";
        }
        else if(w==11||(w>11f&&w<12f)){
            hot="507";
        }
        else if(w==12||(w>12f&&w<13f)){
            hot="542";
        }
        else if(w==13||(w>13f&&w<14f)){
            hot="575";
        }
        else if(w==14||(w>14f&&w<15f)){
            hot="608";
        }
        else{
            hot="640";
        }
    }
    private void calfood_dog(float w){

        if (w < 1 || w == 1){
            hot="70";
        }
        else if(w>1f&&w<2f){
            hot="119";
        }
        else if(w==2||(w>2f&&w<3f)){
            hot="161";
        }
        else if(w==3||(w>3f&&w<4f)){
            hot="196";
        }
        else if(w==4||(w>4f&&w<5f)){
            hot="231";
        }
        else if(w==5||(w>5f&&w<6f)){
            hot="266";
        }
        else if(w==6||(w>6f&&w<7f)){
            hot="301";
        }
        else if(w==7||(w>7f&&w<8f)){
            hot="336";
        }
        else if(w==8||(w>8f&&w<9f)){
            hot="364";
        }
        else if(w==9||(w>9f&&w<10f)){
            hot="392";
        }
        else if(w==10||(w>10f&&w<11f)){
            hot="420";
        }
        else if(w==11||(w>11f&&w<12f)){
            hot="448";
        }
        else if(w==12||(w>12f&&w<13f)){
            hot="476";
        }
        else if(w==13||(w>13f&&w<14f)){
            hot="504";
        }
        else if(w==14||(w>14f&&w<15f)){
            hot="532";
        }
        else if(w==15||(w>15f&&w<16f)){
            hot="560";
        }
        else if(w==16||(w>16f&&w<17f)){
            hot="588";
        }
        else if(w==17||(w>17f&&w<18f)){
            hot="609";
        }
        else if(w==18||(w>18f&&w<19f)){
            hot="637";
        }
        else if(w==19||(w>19f&&w<20f)){
            hot="665";
        }
        else if(w==20||(w>20f&&w<21f)){
            hot="686";
        }
        else if(w==21||(w>21f&&w<22f)){
            hot="714";
        }
        else if(w==22||(w>22f&&w<23f)){
            hot="735";
        }
        else if(w==23||(w>23f&&w<24f)){
            hot="759";
        }
        else if(w==24||(w>24f&&w<25f)){
            hot="784";
        }
        else if(w==25||(w>25f&&w<26f)){
            hot="784";
        }
        else if(w==26||(w>26f&&w<27f)){
            hot="826";
        }
        else if(w==27||(w>27f&&w<28f)){
            hot="854";
        }
        else if(w==28||(w>28f&&w<29f)){
            hot="875";
        }
        else if(w==29||(w>29f&&w<30f)){
            hot="896";
        }
        else if(w==30||(w>30f&&w<31f)){
            hot="917";
        }
        else if(w==31||(w>31f&&w<32f)){
            hot="945";
        }
        else if(w==32||(w>32f&&w<33f)){
            hot="966";
        }
        else if(w==33||(w>33f&&w<34f)){
            hot="987";
        }
        else if(w==34||(w>34f&&w<35f)){
            hot="1008";
        }
        else if(w==35||(w>35f&&w<36f)){
            hot="1029";
        }
        else if(w==36||(w>36f&&w<37f)){
            hot="1050";
        }
        else if(w==37||(w>37f&&w<38f)){
            hot="1071";
        }
        else if(w==38||(w>38f&&w<39f)){
            hot="1092";
        }
        else if(w==39||(w>39f&&w<40f)){
            hot="1113";
        }
        else if(w==40||(w>40f&&w<41f)){
            hot="1134";
        }
        else if(w==41||(w>41f&&w<42f)){
            hot="1155";
        }
        else if(w==42||(w>42f&&w<43f)){
            hot="1176";
        }
        else if(w==43||(w>43f&&w<44f)){
            hot="1197";
        }
        else if(w==44||(w>44f&&w<45f)){
            hot="1218";
        }
        else if(w==45||(w>45f&&w<46f)){
            hot="1239";
        }
        else if(w==46||(w>46f&&w<47f)){
            hot="1260";
        }
        else if(w==47||(w>47f&&w<48f)){
            hot="1274";
        }
        else if(w==48||(w>48f&&w<49f)){
            hot="1295";
        }
        else if(w==49||(w>49f&&w<50f)){
            hot="1316";
        }
        else{
            hot="1316";
        }
    }
}