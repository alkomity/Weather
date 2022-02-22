package com.android.weather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.weather.databinding.ActivityMainBinding
import com.android.weather.models.WeatherInfo
import com.google.gson.Gson
import java.lang.Exception
//import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val apiKey = "ff640649bfea2b9bb366918039a1ba3e"
    private val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            searchWeather()
            binding.searchInput.text.clear()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun searchWeather() {
        try {
            if (binding.searchInput.text.isNotEmpty()){
                val fullUrl = "https://api.openweathermap.org/data/2.5/weather?q=${binding.searchInput.text}&units=metric&appid=${apiKey}"

                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(
                    Request.Method.POST,fullUrl,{response ->
                       val result = gson.fromJson(response,WeatherInfo::class.java)
                        binding.status.text = result.weather[0].main
                        binding.temp.text = result.main.temp.toInt().toString() + " °C"
                        binding.address.text = result.name + ", " + result.sys.country
                        binding.tempMin.text = "Min temp :" + result.main.temp_min + " °C"
                        binding.tempMax.text = "Max temp :" + result.main.temp_max + " °C"
                    },{ _ ->
                       Toast.makeText(this,"هذه المدينة غير موجودة..",Toast.LENGTH_LONG).show()
                    }
                )
                queue.add(stringRequest)
            } else {
                Toast.makeText(this,"الرجاء ادخال اسم المدينة..",Toast.LENGTH_LONG).show()
            }
        }catch (ex:Exception){
            Toast.makeText(this,"hello world",Toast.LENGTH_LONG).show()
        }
    }
}