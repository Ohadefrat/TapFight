package com.tapfight.tapfight;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button newGame;
    private FirebaseAuth mAuth;
    private Button signOut_btn;
    private Button setting;
    InterstitialAd mInterstitialAd;
    Dialog myDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-4032972140338574/9938728681");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        myDialog = new Dialog(this);




// קוד תיזמון טיממר ל10 שניות באפליקציה
       /* ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate( new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }
                        else{
                            Log.d("TAG","Not Loaded");
                        }
                        perpaparAD();


                    }
                });

            }
        },10,10, TimeUnit.SECONDS);

        */

        FacebookSdk.sdkInitialize(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        signOut_btn = findViewById(R.id.signOut_btn);
        newGame = findViewById(R.id.newGame);
        setting = findViewById(R.id.setting_btn);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        //הגדרת כפתור SETTING
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowPopup();
            }
        });
    }

    public void openLoginActivity() {

        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login);
    }

    public void openActivity2(){
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
    }

   /* public void perpaparAD(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }*/


 // הגדרת אופציית חזרה ומעבר לפרסומת
 public void onBackPressed() {

     if (mInterstitialAd.isLoaded()) {
         mInterstitialAd.show();

         mInterstitialAd.setAdListener(new AdListener(){
             @Override
             public void onAdClosed() {
                 super.onAdClosed();
                 finish();
             }
         });
     }

     else {
         super.onBackPressed();
     }
 }

    @SuppressLint("SetTextI18n")
    public void ShowPopup() {
        TextView txtclose,hellotext;
        ImageView profilePic;


        final Button signOut_btn,rateUs;
        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawableResource(
                R.drawable.pop_up_background);
        myDialog.setContentView(R.layout.settingpopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        signOut_btn = myDialog.findViewById(R.id.signOut_btn);
        rateUs = myDialog.findViewById(R.id.rateUs);
        hellotext =myDialog.findViewById(R.id.helloUser);
        profilePic = myDialog.findViewById(R.id.profileImage);
        hellotext.setText("Hello "+ (mAuth.getCurrentUser()).getDisplayName());
        String url = Objects.requireNonNull(mAuth.getCurrentUser().getPhotoUrl()).toString() +  "?type=large";

        Picasso.get().load(url)
                .transform(new CircleTransform())
                .resize(120, 120)
                .into(profilePic);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        signOut_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint(value = "SetTextI18n")
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();

                mAuth.signOut();
                signOut_btn.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        openLoginActivity();
                    }

                }, 1000);
            }
        });
        rateUs.setOnClickListener(new View.OnClickListener() {
            TextView txtclose;

            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.ratebutton);
                Button sumbitbtn = myDialog.findViewById(R.id.submit_button);
                txtclose =myDialog.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowPopup();
                    }
                });
                sumbitbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
                
            }
        });

        myDialog.getWindow();
        myDialog.show();
    }

   public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }


}
