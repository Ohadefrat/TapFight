package com.tapfight.tapfight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity4 extends AppCompatActivity {
    private Button tapToBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        Intent intent = getIntent();
        int number = intent.getIntExtra(Activity2.EXTRA_NUMBER, 0);
        TextView textView1 = (TextView) findViewById(R.id.redEditText);
        textView1.setText("" + number);
        tapToBtn =(Button) findViewById(R.id.tapToBtn);


                tapToBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tapToBtn.setEnabled(false);
                        openLoginActivity();
                    }
                });


    }


    public void openLoginActivity() {
        Intent login = new Intent(Activity4.this, MainActivity.class);
        startActivity(login);
    }

}


