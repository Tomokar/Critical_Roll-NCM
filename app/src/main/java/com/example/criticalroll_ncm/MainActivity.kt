package com.example.criticalroll_ncm

import android.content.res.Configuration
import android.graphics.Color
import android.hardware.Sensor
import androidx.appcompat.app.AppCompatActivity
import android.hardware.SensorManager
import android.widget.TextView
import android.os.Bundle
import android.hardware.SensorEventListener
import android.hardware.SensorEvent
import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import java.util.*
import kotlin.math.sqrt

//Shake imports
//import android.os.Bundle;
//import android.widget.Toast;
//
class MainActivity : AppCompatActivity() {
    //Shake variables
    private var mSensorManager: SensorManager? = null
    private var mAccel = 0f
    private var mAccelCurrent = 0f

    //
    private var imageViewDice: ImageView? = null
    private val rng = Random()
    private var textViewCrit: TextView? = null
    private var numberView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //shake things
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorManager!!.registerListener(mSensorListener, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        imageViewDice = findViewById(R.id.image_view_dice)
        imageViewDice?.setOnClickListener { rollDice(1) }
        textViewCrit = findViewById(R.id.critView)
        numberView = findViewById(R.id.numberView)
        numberView?.text = ""
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
        {
            Configuration.UI_MODE_NIGHT_YES ->
            {
                textViewCrit?.setTextColor(Color.WHITE)
                numberView?.setTextColor(Color.WHITE)
            }
            Configuration.UI_MODE_NIGHT_NO ->
            {
                textViewCrit?.setTextColor(Color.BLACK)
                numberView?.setTextColor(Color.BLACK)
            }
        }
    }

    //Shake nonsense
    private val mSensorListener: SensorEventListener = object : SensorEventListener
    {
        override fun onSensorChanged(event: SensorEvent)
        {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val mAccelLast = mAccelCurrent
            mAccelCurrent = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = mAccelCurrent - mAccelLast
            mAccel = mAccel * 0.9f + delta
            if (mAccel > 15f)
            {
//                Toast.makeText(getApplicationContext(), "Shaken, not stirred.", Toast.LENGTH_LONG).show();
                rollDice(2)
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume()
    {
        mSensorManager!!.registerListener(mSensorListener, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }

    override fun onPause()
    {
        mSensorManager!!.unregisterListener(mSensorListener)
        super.onPause()
    }

    private fun rollDice(sound: Int)
    {
        var rollSound = MediaPlayer.create(this, R.raw.roll)
        when (sound)
        {
            1 -> rollSound = MediaPlayer.create(this, R.raw.roll)
            2 -> rollSound = MediaPlayer.create(this, R.raw.shake)
        }
        var randomNumber = rng.nextInt(360)
        imageViewDice!!.rotation = randomNumber.toFloat()
        rollSound.start()
        textViewCrit!!.visibility = View.INVISIBLE
        randomNumber = rng.nextInt(20) + 1
        var randoNum = randomNumber.toString()
        when (randomNumber)
        {
            1 ->
            {
                randoNum += " :("
                numberView!!.text = randoNum
            }
            20 ->
            {
                randoNum += " :)"
                numberView!!.text = randoNum
                numberView!!.text = randoNum
            }
            else -> numberView!!.text = randoNum
        }
        when (randomNumber)
        {
            1 ->
            {
                val failSound = MediaPlayer.create(this, R.raw.oof)
                failSound.start()
                imageViewDice!!.setImageResource(R.drawable.d20_1)
                textViewCrit!!.visibility = View.VISIBLE
                textViewCrit!!.setText(R.string.crit_failure)
            }
            2 -> imageViewDice!!.setImageResource(R.drawable.d20_2)
            3 -> imageViewDice!!.setImageResource(R.drawable.d20_3)
            4 -> imageViewDice!!.setImageResource(R.drawable.d20_4)
            5 -> imageViewDice!!.setImageResource(R.drawable.d20_5)
            6 -> imageViewDice!!.setImageResource(R.drawable.d20_6)
            7 -> imageViewDice!!.setImageResource(R.drawable.d20_7)
            8 -> imageViewDice!!.setImageResource(R.drawable.d20_8)
            9 -> imageViewDice!!.setImageResource(R.drawable.d20_9)
            10 -> imageViewDice!!.setImageResource(R.drawable.d20_10)
            11 -> imageViewDice!!.setImageResource(R.drawable.d20_11)
            12 -> imageViewDice!!.setImageResource(R.drawable.d20_12)
            13 -> imageViewDice!!.setImageResource(R.drawable.d20_13)
            14 -> imageViewDice!!.setImageResource(R.drawable.d20_14)
            15 -> imageViewDice!!.setImageResource(R.drawable.d20_15)
            16 -> imageViewDice!!.setImageResource(R.drawable.d20_16)
            17 -> imageViewDice!!.setImageResource(R.drawable.d20_17)
            18 -> imageViewDice!!.setImageResource(R.drawable.d20_18)
            19 -> imageViewDice!!.setImageResource(R.drawable.d20_19)
            20 ->
            {
                val succSound = MediaPlayer.create(this, R.raw.noice)
                //                succSound.setVolume(0,100);
                succSound.start()
                imageViewDice!!.setImageResource(R.drawable.d20_20)
                textViewCrit!!.visibility = View.VISIBLE
                textViewCrit!!.setText(R.string.crit_success)
            }
        }
    }
}