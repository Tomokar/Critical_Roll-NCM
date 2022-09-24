package com.example.criticalroll_ncm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//Shake imports
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;
import java.util.Objects;
//
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    //Shake variables
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    //
    private ImageView imageViewDice;
    private Random rng = new Random();
    private TextView textViewCrit;
    private TextView numberView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //shake things
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        imageViewDice = findViewById(R.id.image_view_dice);
        imageViewDice.setOnClickListener(v -> rollDice());

        textViewCrit = findViewById(R.id.critView);
        numberView = findViewById(R.id.numberView);
        numberView.setText("");

        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
        {
            case Configuration.UI_MODE_NIGHT_YES:
                textViewCrit.setTextColor(Color.WHITE);
                numberView.setTextColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                textViewCrit.setTextColor(Color.BLACK);
                numberView.setTextColor(Color.BLACK);
                break;
        }
    }

    //Shake nonsense
    private final SensorEventListener mSensorListener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 15f)
            {
//                Toast.makeText(getApplicationContext(), "Shaken, not stirred.", Toast.LENGTH_LONG).show();
                rollDice();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }
    };
    @Override
    protected void onResume()
    {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause()
    {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    private void rollDice()
    {
        int randomNumber = rng.nextInt(360);
        imageViewDice.setRotation(randomNumber);

        MediaPlayer rollSound = MediaPlayer.create(this, R.raw.roll);
        rollSound.start();
        textViewCrit.setVisibility(View.INVISIBLE);
        randomNumber = rng.nextInt(20) + 1;

        String randoNum = String.valueOf(randomNumber);

        switch(randomNumber)
        {
            case 1:
                randoNum += " :(";
                numberView.setText(randoNum);
                break;
            case 20:
                randoNum += " :)";
                numberView.setText(randoNum);
            default:
                numberView.setText(randoNum);
        }

        switch (randomNumber)
        {
            case 1:
                MediaPlayer failSound = MediaPlayer.create(this, R.raw.oof);
                failSound.start();
                imageViewDice.setImageResource(R.drawable.d20_1);
                textViewCrit.setVisibility(View.VISIBLE);
                textViewCrit.setText(R.string.crit_failure);
                break;
            case 2:
                imageViewDice.setImageResource(R.drawable.d20_2);
                break;
            case 3:
                imageViewDice.setImageResource(R.drawable.d20_3);
                break;
            case 4:
                imageViewDice.setImageResource(R.drawable.d20_4);
                break;
            case 5:
                imageViewDice.setImageResource(R.drawable.d20_5);
                break;
            case 6:
                imageViewDice.setImageResource(R.drawable.d20_6 );
                break;
            case 7:
                imageViewDice.setImageResource(R.drawable.d20_7);
                break;
            case 8:
                imageViewDice.setImageResource(R.drawable.d20_8);
                break;
            case 9:
                imageViewDice.setImageResource(R.drawable.d20_9);
                break;
            case 10:
                imageViewDice.setImageResource(R.drawable.d20_10);
                break;
            case 11:
                imageViewDice.setImageResource(R.drawable.d20_11);
                break;
            case 12:
                imageViewDice.setImageResource(R.drawable.d20_12);
                break;
            case 13:
                imageViewDice.setImageResource(R.drawable.d20_13);
                break;
            case 14:
                imageViewDice.setImageResource(R.drawable.d20_14);
                break;
            case 15:
                imageViewDice.setImageResource(R.drawable.d20_15);
                break;
            case 16:
                imageViewDice.setImageResource(R.drawable.d20_16);
                break;
            case 17:
                imageViewDice.setImageResource(R.drawable.d20_17);
                break;
            case 18:
                imageViewDice.setImageResource(R.drawable.d20_18);
                break;
            case 19:
                imageViewDice.setImageResource(R.drawable.d20_19);
                break;
            case 20:
                MediaPlayer succSound = MediaPlayer.create(this, R.raw.noice);
                succSound.setVolume(0,100);
                succSound.start();
                imageViewDice.setImageResource(R.drawable.d20_20);
                textViewCrit.setVisibility(View.VISIBLE);
                textViewCrit.setText(R.string.crit_success);
                break;
        }
    }
}