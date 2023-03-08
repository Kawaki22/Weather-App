package com.kawaki.weather

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import org.json.JSONObject
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var etcity:EditText//"dhaka,bd"
    var api:String="06c921750b9a82d8f5d1294e1586276f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Hide Action Bar
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }

        etcity=findViewById(R.id.etCity)
    }

    fun ImageButtonClick(view: View){
        MyAsyncTask().execute()
    }

    fun retry(view: View){
        finish()
        startActivity(intent)
        overridePendingTransition(0,1)
    }

    inner class MyAsyncTask():AsyncTask<String,Void,String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loading).visibility=View.VISIBLE
            //findViewById<CardView>(R.id.cardView).visibility=View.GONE

        }

        override fun doInBackground(vararg params: String?): String? {
            var city:String?=etcity.text.toString()//"delhi,in"
            var response:String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$api").readText(Charsets.UTF_8)
            }catch (ex:Exception){
                response=null
                /*findViewById<CardView>(R.id.cardView).visibility= View.GONE
                findViewById<TextView>(R.id.tvWeather).visibility= View.GONE
                findViewById<TextView>(R.id.tvError).visibility= View.VISIBLE*/
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                // Extracting JSON from the API
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val temp = main.getString("temp")
                val tempMin =main.getString("temp_min")
                val tempMax = main.getString("temp_max")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+","+sys.getString("country")

                //display values
                findViewById<TextView>(R.id.tv1).text="Temperature: $temp°C"
                findViewById<TextView>(R.id.tv2).text= "Min Temp: $tempMin°C"
                findViewById<TextView>(R.id.tv3).text= "Max Temp: $tempMax°C"
                findViewById<TextView>(R.id.tv4).text=weatherDescription
                findViewById<TextView>(R.id.tvWeather).text=address

            }catch (ex:Exception){
                findViewById<CardView>(R.id.cardView).visibility= View.GONE
                findViewById<TextView>(R.id.tvWeather).visibility= View.GONE
                findViewById<TextView>(R.id.tvError).visibility= View.VISIBLE
                findViewById<Button>(R.id.btnRetry).visibility= View.VISIBLE
            }
        }

    }
}