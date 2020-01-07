package com.example.tapfight;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;

import java.util.Locale;


public class Activity2 extends AppCompatActivity {


    public static final String EXTRA_NUMBER = "com.example.application.example.EXTRA_NUMBER";
    private static final long START_TIME_IN_MILLIS = 10000;

    private TextView mTextViewCountDown;
    private TextView mTextViewCountDown2;

    private CountDownTimer mCountDownTimer;


    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    Button blueButton;
    Button redButton;
    TextView blueTextView;
    TextView redTextView;
    TextView blueTextView2;
    TextView redTextView2;
    LinearLayout totalSumScreen;
    View viewblue;
    View viewred;

    int tapForBlue, tapForRed, count1, count2, blue_h, red_h,totalSum ;
    private boolean mTimerRunning;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        ConstraintLayout my_bg = findViewById(R.id.my_bg);

        Intent intent = getIntent();
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mTextViewCountDown2 = findViewById(R.id.text_view_countdown2);
        totalSumScreen = findViewById(R.id.totalSumScreen);
        blueButton = (Button) findViewById(R.id.blueButton);
        redButton = (Button) findViewById(R.id.redButton);
        blueTextView = (TextView) findViewById(R.id.blueResultTextView);
        redTextView = (TextView) findViewById(R.id.redResultTextView);
        blueTextView2 = (TextView) findViewById(R.id.blueResultTextView2);
        redTextView2 = (TextView) findViewById(R.id.redResultTextView2);
        viewblue = findViewById(R.id.viewblue);
        viewred = findViewById(R.id.viewred);
        count1 = 0;
        count2 = 0;
        blue_h = (int) (((LinearLayout.LayoutParams) viewred.getLayoutParams()).height);
        red_h = (int) (((LinearLayout.LayoutParams) viewred.getLayoutParams()).height);
        totalSum = (int) totalSumScreen.getWeightSum()/2;
        tapForRed = (int) (((LinearLayout.LayoutParams) viewred.getLayoutParams()).weight);
        tapForBlue = (int) (((LinearLayout.LayoutParams) viewblue.getLayoutParams()).weight);



        final ValueAnimator vmb = new ValueAnimator();
        vmb.setIntValues(Color.parseColor("#000000"), Color.parseColor("#803064FF"));
        vmb.setEvaluator(new ArgbEvaluator());
        vmb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                viewblue.setBackgroundColor((Integer) animation.getAnimatedValue());

            }
        });
        final ValueAnimator vmr = new ValueAnimator();
        vmr.setIntValues(Color.parseColor("#000000"),Color.parseColor("#80FF3030"));
        vmr.setEvaluator(new ArgbEvaluator());
        vmr.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewred.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                count1++;

                blueTextView.setText("TAP:" + count1);
                blueTextView2.setText("BLUE:" + count1);

                if ( count1 != count2 ) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, tapForBlue++);
                    viewblue.setLayoutParams(layoutParams);
                    tapForRed-=count1;
                }

                startTimer();
            }


        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count2++;
                redTextView.setText("TAP:" + count2);
                redTextView2.setText("RED:" + count2);

        if( count1 != count2 ) {

         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, tapForBlue--);
        viewblue.setLayoutParams(layoutParams);
         tapForRed+=count2;

        }

                startTimer();
            }
        });


    }





        private void startTimer () {
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    mTimerRunning = false;
                    blueButton.setEnabled(false);
                    redButton.setEnabled(false);
                    openActivity3or4();


                }

            }.start();

            mTimerRunning = true;
        }
        private void updateCountDownText () {
            int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

            String timeLeftFormatted = String.format(Locale.getDefault(), "%d", seconds);

            mTextViewCountDown.setText(timeLeftFormatted);
            mTextViewCountDown2.setText(timeLeftFormatted);


        }
        public void openActivity3or4 () {
                    if ((tapForBlue*2)+1 > tapForRed) {

                        EditText blueEditText =  findViewById(R.id.blueEditText);
                        int number = count1;
                        Intent blue = new Intent(Activity2.this, Activity3.class);
                        blue.putExtra(EXTRA_NUMBER, number);
                        startActivity(blue);
                    }
                    if ((tapForRed/2)+1 > tapForBlue) {
                        EditText redEditText = findViewById(R.id.blueEditText);
                        int number = count2;
                        Intent red = new Intent(Activity2.this, Activity4.class);
                        red.putExtra(EXTRA_NUMBER, number);
                        startActivity(red);
                    }

        }
    }


