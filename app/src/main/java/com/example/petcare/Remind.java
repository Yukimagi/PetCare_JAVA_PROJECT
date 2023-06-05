package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Remind extends AppCompatActivity implements View.OnClickListener {

    private int notificationId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        findViewById(R.id.setBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText editText=findViewById(R.id.editText);
        TimePicker timePicker=findViewById(R.id.timePicker);

        Intent intent=new Intent(Remind.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("todo", editText.getText().toString());

        PendingIntent alarmIntent=PendingIntent.getBroadcast(Remind.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarm=(AlarmManager) getSystemService(ALARM_SERVICE);

        if (view.getId() == R.id.setBtn) {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, hour);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.SECOND, 0);
            long alarmStartTime = startTime.getTimeInMillis();

            alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

            Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.cancelBtn) {
            alarm.cancel(alarmIntent);
            Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
        }

    }


}