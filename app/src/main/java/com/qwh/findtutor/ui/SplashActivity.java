package com.qwh.findtutor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.qwh.findtutor.R;

public class SplashActivity extends Activity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        //采用渐变动画进入

        AlphaAnimation animation = new AlphaAnimation(0.6f, 1.0f);
        animation.setDuration(1000);
        view.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //展示2秒进入程序
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
