package com.algebra.vubusstop;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity {
	
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		final ImageView vu = (ImageView) findViewById(R.id.vu_splash);
		final ImageView bus = (ImageView) findViewById(R.id.bus_splash);
		final ImageView stop = (ImageView) findViewById(R.id.stop_splash);
		
		Animation anim = AnimationUtils.loadAnimation(SplashActivity.this,
				R.anim.scale_animation);
		Animation anim2 = AnimationUtils.loadAnimation(SplashActivity.this,
				R.anim.move);
		Animation anim3 = AnimationUtils.loadAnimation(SplashActivity.this,
				R.anim.bounce);
		vu.startAnimation(anim);
		bus.startAnimation(anim2);
		stop.startAnimation(anim3);
		
		new Handler().postDelayed(new Runnable() {
			 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MapActivity.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}
