package com.neusoft.eenie.order.controller.main.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.main.MainActivity;
import com.neusoft.eenie.order.utiles.SharePreferencesTool;


public class SplashActivity extends AppCompatActivity {
    RelativeLayout backview;
    LinearLayout logoview;
    ImageView backgroudview;
    SharePreferencesTool sptool;



    private final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sptool = new SharePreferencesTool(SplashActivity.this);


        if (isOpenSplash()) {
            init_view();
            startAnimation();
        } else {
            gotoNextView();
        }






    }

    private void init_view() {
        backview = (RelativeLayout) findViewById(R.id.backview);
        logoview = (LinearLayout) findViewById(R.id.logoview);
        backgroudview = (ImageView) findViewById(R.id.backgroudview);
    }


    public void gotoNextView() {


        if (isFirst()) {
            startActivity(new Intent(SplashActivity.this, IntroduceActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }

        finish();
    }




    public boolean isOpenSplash() {
        return sptool.getBooleanValue(sptool.KEY_OPEN);
    }

    public boolean isFirst() {


        return sptool.getBooleanValue(sptool.KEY_FIRST);

    }

    public void startAnimation(){
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(2000);
        animationSet.playTogether(
                ObjectAnimator.ofFloat(logoview, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(logoview, "translationY", 300, 0),
                ObjectAnimator.ofFloat(backgroudview, "scaleX", 1.3f, 1.05f),
                ObjectAnimator.ofFloat(backgroudview, "scaleY", 1.3f, 1.05f)
        );
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e(TAG, "animation end!");
                gotoNextView();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animationSet.start();
    }






}
