package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;//***換頁很重要
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;//***
import android.widget.Button;//***

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //寵物醫院頁面
        Button button_hospital = (Button) findViewById(R.id.button_hospital);
        button_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hospital = new Intent(MainActivity.this, common.class);
                startActivity(hospital);

            }
        });

        //寵物記帳
        Button button_account = (Button) findViewById(R.id.button_account);
        button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(MainActivity.this, Account.class);
                startActivity(account);

            }
        });

        //代辦事項
        Button button_item = (Button) findViewById(R.id.button_item);
        button_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent item = new Intent(MainActivity.this, Item.class);
                startActivity(item);

            }
        });

        //提醒功能
        Button button_remind = (Button) findViewById(R.id.button_remind);
        button_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remind = new Intent(MainActivity.this, Remind.class);
                startActivity(remind);

            }
        });

        //寵物日記
        Button button_diary = (Button) findViewById(R.id.button_diary);
        button_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diary = new Intent(MainActivity.this, Diary.class);
                startActivity(diary);

            }
        });

        //主人相性測驗
        Button button_test = (Button) findViewById(R.id.button_test);
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MainActivity.this, Test.class);
                startActivity(test);

            }
        });

        //寵物保健
        Button btnHealth = (Button) findViewById(R.id.btnHealth);
        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent health = new Intent(MainActivity.this, Health.class);
                startActivity(health);

            }
        });
    }
}