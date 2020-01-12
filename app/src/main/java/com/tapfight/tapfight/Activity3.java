package com.tapfight.tapfight;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity3 extends AppCompatActivity {
    private Button tapToBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Intent intent = getIntent();
        int number = intent.getIntExtra(Activity2.EXTRA_NUMBER, 0);
        TextView textView1 = (TextView) findViewById(R.id.blueEditText);
        textView1.setText("" + number);

        tapToBtn2 =(Button) findViewById(R.id.tapToBtn2);

            Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tapToBtn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            tapToBtn2.setEnabled(false);
                            openLoginActivity();
                        }
                    });
                }

            },2000);

        }



    public void openLoginActivity() {
        Intent login = new Intent(Activity3.this, MainActivity.class);
        startActivity(login);
    }
}


