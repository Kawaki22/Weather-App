package com.kawaki.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Hide Action Bar
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
    }

    fun GetWeatherinfo(){
        val city="City"
        val url="OpenWeather..."
    }
}