package com.muravyovdmitr.shoplocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.util.SettingsManager;

/**
 * Created by Dima Muravyov on 10.05.2016.
 */
public class SettingsActivity extends AppCompatActivity {
    private Switch mSplashScreenEnable;
    private ImageButton mSplashScreenDelayIncrease;
    private ImageButton mSplashScreenDelayDecrease;
    private EditText mSplashScreenDelay;

    private SettingsManager mSettings;

    private View.OnClickListener mDecreaseDelay = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setSplashDelayValue(mSettings.getSplashingScreenDuration() + 1);
        }
    };

    private View.OnClickListener mIncreaseDelay = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setSplashDelayValue(mSettings.getSplashingScreenDuration() - 1);
        }
    };

    private CompoundButton.OnCheckedChangeListener mDelayEnable = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setSplashEnable(isChecked);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_settings_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.drawer_settings);

        this.mSettings = new SettingsManager(this);

        findView();
        setupData();
    }

    private void findView() {
        this.mSplashScreenEnable = (Switch) findViewById(R.id.activity_settings_splash_enable);
        this.mSplashScreenDelayIncrease = (ImageButton) findViewById(R.id.activity_settings_increase_delay);
        this.mSplashScreenDelayDecrease = (ImageButton) findViewById(R.id.activity_settings_decrease_delay);
        this.mSplashScreenDelay = (EditText) findViewById(R.id.activity_settings_delay);
    }

    private void setupData() {
        this.mSplashScreenEnable.setOnCheckedChangeListener(this.mDelayEnable);
        this.mSplashScreenEnable.setChecked(this.mSettings.isSplashingScreenEnable());
        setSplashScreenDelayEnable(this.mSplashScreenEnable.isChecked());

        this.mSplashScreenDelay.setText(String.valueOf(this.mSettings.getSplashingScreenDuration()));

        this.mSplashScreenDelayDecrease.setOnClickListener(this.mDecreaseDelay);

        this.mSplashScreenDelayIncrease.setOnClickListener(this.mIncreaseDelay);
    }

    private void setSplashScreenDelayEnable(boolean enable) {
        this.mSplashScreenDelayDecrease.setEnabled(enable);
        this.mSplashScreenDelayIncrease.setEnabled(enable);
    }

    private void setSplashEnable(boolean enable) {
        this.mSettings.setSplashingScreenEnable(enable);
        setSplashScreenDelayEnable(enable);
    }

    private void setSplashDelayValue(int value) {
        if (value < 1 || value > 100) {
            return;
        }

        this.mSettings.setSplashingScreenDuration(value);
        this.mSplashScreenDelay.setText(String.valueOf(value));
    }
}
