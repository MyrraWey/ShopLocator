package com.muravyovdmitr.shoplocator.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.util.SettingsManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class SplashingActivity extends AppCompatActivity {
    private Button mSkip;
    private TextView mTimeLeft;

    private int mTaskTime;
    private SplashingTask mSplashingTask;
    private SettingsManager mSettingsManager;

    private class SplashingTask extends AsyncTask<Void, Integer, Void> {
        protected int mDelayTime;

        public SplashingTask(int delay) {
            this.mDelayTime = delay;
        }

        @Override
        protected Void doInBackground(Void... params) {
            int delay = this.mDelayTime;

            while (delay > 0) {
                publishProgress(delay--);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTimeLeft.setText(
                    getApplicationContext().getResources().getQuantityString(
                            R.plurals.activity_splashing_time_left,
                            values[0],
                            values[0]
                    )
            );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            startMainActivity();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashing);

        this.mSettingsManager = new SettingsManager(this);

        if (!this.mSettingsManager.isSplashingScreenEnable()) {
            startMainActivity();
        }

        this.mTaskTime = this.mSettingsManager.getSplashingScreenDuration();

        this.mSkip = (Button) findViewById(R.id.activity_splashing_skip_button);
        this.mTimeLeft = (TextView) findViewById(R.id.activity_splashing_time_left);

        this.mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.mSplashingTask = new SplashingTask(this.mTaskTime);
        this.mSplashingTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.mSplashingTask.cancel(true);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, ShopsListActivity.class);
        startActivity(intent);
    }
}
