package com.rusdevapp.apod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rusdevapp.apod.Adapter.APODAdapter
import com.rusdevapp.apod.Interface.RetrofitService
import com.rusdevapp.apod.Model.ModelNASA
import com.rusdevapp.apod.databinding.ActivityListBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListActivity : AppCompatActivity() {

    var title:String?=null
    var url:String?=null
    var explanation:String?=null
    private lateinit var binding:ActivityListBinding
    private val BASE_URL:String="https://api.nasa.gov/planetary/"
    private var listOfAPOD:List<ModelNASA>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getListAPOD()
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = APODAdapter(listOfAPOD!!)
    }

    private fun getListAPOD()
    {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.getAPOD("2021-09-01", "2021-09-22").enqueue(object : Callback<List<ModelNASA>>
        {
            override fun onResponse(call: Call<List<ModelNASA>>, response: Response<List<ModelNASA>>) {
                binding.progressBar.visibility= View.GONE
                if(response.isSuccessful) listOfAPOD = response.body()
                else Toast.makeText(this@ListActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()

            }
            override fun onFailure(call: Call<List<ModelNASA>>, t: Throwable) {
                binding.progressBar.visibility= View.GONE
                Toast.makeText(this@ListActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}