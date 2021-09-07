package com.rusdevapp.apod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import com.rusdevapp.apod.Additional.CheckFields
import com.rusdevapp.apod.Interface.RetrofitService
import com.rusdevapp.apod.Model.ModelNASA
import com.rusdevapp.apod.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var date:String?=null
    private lateinit var binding:ActivityMainBinding
    private val BASE_URL:String="https://api.nasa.gov/planetary/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (view != null)
            when(view.id)
            {
                binding.btnSend.id->
                    if(!CheckFields(binding.etDay, binding.etMonth, binding.etYear).CheckResult())
                        sendRequest()
            }

    }

    private fun sendRequest() {
        binding.tvTitleAPOD.visibility=View.GONE
        binding.tvExplanationAPOD.visibility=View.GONE
        binding.progressBar.visibility=View.VISIBLE
        date="${binding.etYear.text}-${binding.etMonth.text}-${binding.etDay.text}"
        var retrofit:Retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
        if(date!=null) {
            var retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
            retrofitService.getAPOD(date!!).enqueue(object : Callback<ModelNASA>{
                override fun onResponse(call: Call<ModelNASA>, response: Response<ModelNASA>) {
                    binding.progressBar.visibility=View.GONE
                    if(response.isSuccessful) {
                        binding.tvTitleAPOD.visibility = View.VISIBLE
                        binding.tvExplanationAPOD.visibility = View.VISIBLE
                        var title:String? = response.body()!!.title
                        var explanation:String? = response.body()!!.explanation
                        if(title!=null)binding.tvTitleAPOD.text = title
                        if(explanation!=null)binding.tvExplanationAPOD.text = explanation
                    }
                    else
                    {
                        Toast.makeText(this@MainActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ModelNASA>, t: Throwable) {
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })

        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("day", binding.etDay.text.toString())
        outState.putString("month", binding.etMonth.text.toString())
        outState.putString("year", binding.etYear.text.toString())
        outState.putString("title", binding.tvTitleAPOD.text.toString())
        outState.putString("explanation", binding.tvExplanationAPOD.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.etDay.setText(savedInstanceState.getString("day"))
        binding.etMonth.setText(savedInstanceState.getString("month"))
        binding.etYear.setText(savedInstanceState.getString("year"))
        binding.tvTitleAPOD.text = savedInstanceState.getString("title")
        binding.tvExplanationAPOD.text = savedInstanceState.getString("explanation")
    }
}