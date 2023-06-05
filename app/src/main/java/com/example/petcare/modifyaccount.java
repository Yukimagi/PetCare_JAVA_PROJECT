package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class modifyaccount extends AppCompatActivity {
    private static final String DataBaseName = "Noteaccount";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "account1";
    private static SQLiteDatabase db;
    private accountdata sqlDataBaseHelper;
    Boolean firstTime=true;
    Spinner sp1;
    String result;
    TextView text;
    Button button;
    DatePickerDialog.OnDateSetListener datePicker;
    Calendar calendar = Calendar.getInstance();
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyaccount);
        //拿上一頁傳過來的值
        Intent intent = getIntent();
        String titleValue = intent.getStringExtra("title");   //date
        String contentValue = intent.getStringExtra("content");   //type
        String otherValue = intent.getStringExtra("other");
        String moneyValue = intent.getStringExtra("money");

        //日期
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);
        text.setText("日期：" + titleValue);

        datePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                text.setText("日期：" + sdf.format(calendar.getTime()));
                dateString = sdf.format(calendar.getTime());
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(modifyaccount.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        //選單
        sp1 = (Spinner)findViewById(R.id.spinner);





        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this
                ,R.array.station,android.R.layout.simple_dropdown_item_1line);


        sp1.setAdapter(adapter1);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (firstTime){
                    firstTime = false;
                    //沒有這個的話Toast會打印一次還沒選擇的狀態
                }
                else{
                    Toast.makeText(view.getContext(),parent.getSelectedItem().toString(),
                            Toast.LENGTH_SHORT).show();
                    result = parent.getItemAtPosition(position).toString();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //沒選擇項目的話，要做的事寫在這。
            }
        });



        //prc_showmessage(intent.getStringExtra("content"));   //用來測試拿到的值是什麼
        EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
        editTextContent.setText(moneyValue);
        EditText editTextOther = findViewById(R.id.editTextTextMultiLine3);
        editTextOther.setText(otherValue);

        Button modifybutton= findViewById(R.id.modify);
        modifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 獲取標題和內容的值
                EditText editTextContent = findViewById(R.id.editTextTextMultiLine2);
                EditText editTextOther = findViewById(R.id.editTextTextMultiLine3);

                String content = editTextContent.getText().toString();
                String other = editTextOther.getText().toString();
                //----------資料庫開啟(一定要再create裡)--------
                sqlDataBaseHelper = new accountdata(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
                db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
                //修改資料庫
                ContentValues values = new ContentValues();
                values.put("title", contentValue);   //type
                values.put("content", content);    //money
                values.put("other", other);
                values.put("date", dateString);

                // 準備 WHERE 條件，根據標題更新對應的內容
                String whereClause = "title" + " = ?";
                String[] whereArgs = {contentValue};

                System.out.println(contentValue);

                // 更新資料庫中的記事項目
                int rowsUpdated = db.update(DataBaseTable, values, whereClause, whereArgs);
                // 檢查是否成功插入資料
                // 檢查是否成功更新資料
                if (rowsUpdated > 0) {
                    Toast.makeText(getApplicationContext(), "記事修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(modifyaccount.this, Account.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "記事修改失敗", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }
}
