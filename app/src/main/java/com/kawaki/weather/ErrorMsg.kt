package com.kawaki.weather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ErrorMsg: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error_msg)

    }
    //restarting main activity after error
    fun retry(view: View){
        //var button: MaterialButton=findViewById(R.id.btnRetry)
        onBackPressed()
    }

}