package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    int cat=0;
    int dog=0;
    boolean ok=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        display();
        ImageView imageView_test = findViewById(R.id.imageView_test);
        imageView_test.setVisibility(View.GONE);
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        //ask.setVisibility(View.GONE);
        RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
        testA.setVisibility(View.GONE);
        RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
        testB.setVisibility(View.GONE);
        RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
        testC.setVisibility(View.GONE);
        ask.setVisibility(View.VISIBLE);
        ask.setText("");

        Button button_start=(Button) findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                cat=0;
                dog=0;
                ok=true;

                //ImageButton next=(ImageButton) findViewById(R.id.imageButton_next);
                //next.setVisibility(View.VISIBLE);
                display2();
            }
        });



        Button ret = (Button) findViewById(R.id.button_return);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat=0;
                dog=0;
                ok=true;
                TextView ask=(TextView) findViewById(R.id.textView_ask);
                ask.clearComposingText();
                Intent main = new Intent(Test.this, MainActivity.class);
                startActivity(main);
            }
        });
    }
    Handler handler = new Handler();
    int i=0;
    private void display(){
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ask.setText("大家在交朋友時通常都會選擇跟自已個性比較相近的成為朋友，\r\n" +
                        "那有沒有想過其實在養毛小孩時我們也會選擇跟自己習性相符的動物哦！\r\n" +
                        "所以當你下定決心成為貓奴或是狗奴時，\r\n" +
                        "多數是和自己本身的性格有關，\r\n" +
                        "沒錯！那就是你奴性非常重\r\n" +
                        "（威～不是啦！\r\n" +
                        "現在就讓PetCare問你幾個問題，\r\n" +
                        "再來看看你是貓派還是狗派吧！");
            }
        }, 5000);
    }

    private void display2(){
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
        RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
        RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
        testA.setChecked(false);
        testB.setChecked(false);
        testC.setChecked(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i==0){
                    test1();
                    i++;
                    display2();
                }
                else if(i==1){
                    test2();
                    i++;
                    display2();
                }
                else if(i==2){
                    test3();
                    i++;
                    display2();
                }
                else if(i==3){
                    test4();
                    i++;
                    display2();
                }
                else if(i==4){
                    test5();
                    i++;
                    display2();
                }
                else{
                    ImageView imageView_test = findViewById(R.id.imageView_test);
                    imageView_test.setVisibility(View.GONE);
                    end();
                }
            }
        }, 10000);
    }

    private void end(){
        while(i==5) {
            TextView ask=(TextView) findViewById(R.id.textView_ask);
            //ask.setVisibility(View.GONE);
            RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
            RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
            RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
            //next.setVisibility(View.GONE);
            testA.setVisibility(View.GONE);
            testB.setVisibility(View.GONE);
            testC.setVisibility(View.GONE);
            ask.setText("恭喜你！答題完成！現在就來看看解答吧!\r\n\r\n\r\n");
            if (!ok) {
                ask.append("你其實可能不太適合養寵物，\r\n" +
                        "甚至你可能有一點點的反社會\r\n" +
                        "因此PetCare建議您，可以考慮先不要養寵物喔~\r\n" +
                        "但如果養了，也請不要棄養，可以找尋相關資源協助喔~\r\n");
                break;
            } else {
                if (cat > dog) {
                    ask.append("恭喜您!，\r\n" +
                            "經過測試您可能是一位「貓派」\r\n" +
                            "您可以透過相關資源，並配合PetCare使用\r\n" +
                            "成為一位合格的貓奴喔~\r\n");
                    break;
                } else if (cat < dog) {
                    ask.append("恭喜您!，\r\n" +
                            "經過測試您可能是一位「狗派」\r\n" +
                            "您可以透過相關資源，並配合PetCare使用\r\n" +
                            "成為一位合格的狗奴喔~\r\n");
                    break;
                } else {
                    ask.append("你其實可能並沒有明確的喜好，\r\n" +
                            "因此您可以先去領養中心看看並媒合，\r\n" +
                            "您也可以透過相關資源，並配合PetCare使用\r\n" +
                            "成為一位合格的動物好朋友喔~\r\n");
                    break;
                }
            }
        }
    }

    boolean testing=false;
    private  void test1(){
            ImageView imageView_test = findViewById(R.id.imageView_test);
            imageView_test.setVisibility(View.VISIBLE);
            imageView_test.setImageResource(R.drawable.weekend);
            TextView ask = (TextView) findViewById(R.id.textView_ask);
            ask.clearComposingText();
            ask.setText("平時週末喜歡...");

            RadioButton testA = (RadioButton) findViewById(R.id.radioButton_testA);
            testA.setText("我就是要往外跑！");
            RadioButton testB = (RadioButton) findViewById(R.id.radioButton_testB);
            testB.setText("誰都不准管我，也不要吵我，我要睡覺(I wanna do nothing)。");
            RadioButton testC = (RadioButton) findViewById(R.id.radioButton_testC);
            testC.setText("不要吵我！我要宅在家");
            testA.setVisibility(View.VISIBLE);
            testB.setVisibility(View.VISIBLE);
            testC.setVisibility(View.VISIBLE);

            testA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        dog++;
                    }
                }
            });

            testB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {testA.setChecked(false);
                        testB.setChecked(false);
                        testC.setChecked(false);
                        ok = false;
                    }
                }
            });
            testC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cat++;
                    }
                }

            });
        //ImageButton next=(ImageButton) findViewById(R.id.imageButton_next);


    }
    private void test2(){
        ImageView imageView_test = findViewById(R.id.imageView_test);
        imageView_test.setVisibility(View.VISIBLE);
        imageView_test.setImageResource(R.drawable.stay);
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        ask.clearComposingText();
        ask.setText("喜歡自己逛街、吃飯或是看電影嗎?");

        RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
        testA.setText("當然可以！我是獨行俠");
        RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
        testB.setText("我需要有人陪...");
        RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
        testC.setText("我害怕與非人生物相處，其他都可以~");

        testA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cat++;
                }
            }
        });

        testB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dog++;
                }
            }
        });
        testC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ok=false;
                }
            }
        });
    }
    private void test3(){
        ImageView imageView_test = findViewById(R.id.imageView_test);
        imageView_test.setVisibility(View.VISIBLE);
        imageView_test.setImageResource(R.drawable.team);
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        ask.clearComposingText();
        ask.setText("平時做團體活動時你習慣...");

        RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
        testA.setText("我喜歡當領頭的那個！");
        RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
        testB.setText("有事再叫我做");
        RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
        testC.setText("沒意見~");

        testA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dog++;
                }
            }
        });

        testB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView imageView = findViewById(R.id.imageView);

                if (isChecked) {
                    cat++;
                }
            }
        });

        testC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                }
            }
        });
    }
    private void test4(){
        ImageView imageView_test = findViewById(R.id.imageView_test);
        imageView_test.setVisibility(View.VISIBLE);
        imageView_test.setImageResource(R.drawable.tel);
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        ask.clearComposingText();
        ask.setText("如果跟朋友有公事需要講電話你會...");

        RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
        testA.setText("講完公事立馬說881~");
        RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
        testB.setText("我看人耶~");
        RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
        testC.setText("講完公事還要聊個一小時才爽！");

        testA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cat++;
                }
            }
        });

        testB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                }
            }
        });

        testC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dog++;
                }
            }
        });
    }
    private void test5(){
        ImageView imageView_test = findViewById(R.id.imageView_test);
        imageView_test.setVisibility(View.VISIBLE);
        imageView_test.setImageResource(R.drawable.unhappy);
        TextView ask=(TextView) findViewById(R.id.textView_ask);
        ask.clearComposingText();
        ask.setText("遇到不開心事的時候你會...");

        RadioButton testA=(RadioButton) findViewById(R.id.radioButton_testA);
        testA.setText("把不開心直接講出來");
        RadioButton testB=(RadioButton) findViewById(R.id.radioButton_testB);
        testB.setText("忍一下沒關係");
        RadioButton testC=(RadioButton) findViewById(R.id.radioButton_testC);
        testC.setText("我要毀滅世界!");

        testA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dog++;
                }
            }
        });

        testB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cat++;
                }
            }
        });
        testC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ok=false;
                }
            }
        });
    }

}