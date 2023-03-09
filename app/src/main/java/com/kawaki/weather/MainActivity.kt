package com.kawaki.weather

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var etcity:EditText//variable of EditText type
    var api:String="ea4a8be06f4a8d7e1ba8ac87710f2e95"//api key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Hide Action Bar
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }

        etcity=findViewById(R.id.etCity)//assigning EditText variable to edit text box

    }

    //function to call api when button is clicked
    fun ImageButtonClick(view: View){
        MyAsyncTask().execute()
        buttonHide()
    }

    //restarting main activity after error
    fun retry(view: View){
        finish()
        startActivity(intent)
        overridePendingTransition(0,1)
    }

    //light mode
    fun lightkMode(view: View){
        var constarintLayot:ConstraintLayout=findViewById(R.id.constraint)
        var relativeLayout:RelativeLayout=findViewById(R.id.relative)
        if(findViewById<SwitchCompat>(R.id.switch1).isChecked){
            findViewById<SwitchCompat>(R.id.switch1).text="Dark Mode"
            constarintLayot.setBackgroundResource(R.drawable.day)
            relativeLayout.setBackgroundResource(R.drawable.card_day)
        }
        else{
            findViewById<SwitchCompat>(R.id.switch1).text="Light Mode"
            constarintLayot.setBackgroundResource(R.drawable.night)
            relativeLayout.setBackgroundResource(R.drawable.card_night)
        }

    }

    //hide keyboard
    fun buttonHide(){
        val view=this.currentFocus
        val inputMethod=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(),0)
        }
    }

    //inner class inheriting AsyncTask class(inbuilt)
    inner class MyAsyncTask():AsyncTask<String,Void,String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loading).visibility=View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            var city:String?=etcity.text.toString().trim()//"delhi,in"
            var response:String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$api").readText(Charsets.UTF_8)
                }
            catch (ex:Exception){
                response=null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                // Extracting JSON from the API
                val json = JSONObject(result)
                val main = json.getJSONObject("main")
                val sys = json.getJSONObject("sys")
                val weather = json.getJSONArray("weather").getJSONObject(0)

                val temp = main.getString("temp")
                val tempMin =main.getString("temp_min")
                val tempMax = main.getString("temp_max")
                val weatherDescription = weather.getString("description")

                val address = json.getString("name")+","+sys.getString("country")

                //display values
                findViewById<TextView>(R.id.tv1).text="Temperature: $temp°C"
                findViewById<TextView>(R.id.tv2).text= "Min Temp: $tempMin°C"
                findViewById<TextView>(R.id.tv3).text= "Max Temp: $tempMax°C"
                findViewById<TextView>(R.id.tv4).text=weatherDescription
                findViewById<TextView>(R.id.tvWeather).text=address

            }catch (ex:Exception){
                findViewById<CardView>(R.id.cardView).visibility= View.GONE
                findViewById<TextView>(R.id.tvWeather).visibility= View.GONE
                findViewById<SwitchCompat>(R.id.switch1).visibility= View.GONE
                findViewById<TextView>(R.id.tvError).visibility= View.VISIBLE
                findViewById<Button>(R.id.btnRetry).visibility= View.VISIBLE
                buttonHide()
            }
        }

    }
}