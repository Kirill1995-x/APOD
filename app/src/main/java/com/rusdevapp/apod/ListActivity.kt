package com.rusdevapp.apod

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rusdevapp.apod.Adapter.APODAdapter
import com.rusdevapp.apod.Interface.RetrofitService
import com.rusdevapp.apod.Model.APODModel
import com.rusdevapp.apod.databinding.ActivityListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityListBinding
    private lateinit var listOfAPOD: ArrayList<APODModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //---
        if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT)
            binding.rvList.layoutManager = LinearLayoutManager(this)
        else
            binding.rvList.layoutManager = GridLayoutManager(this, 2)
        //---
        if(savedInstanceState!=null)
        {
            listOfAPOD = savedInstanceState.getParcelableArrayList<APODModel>("arrayList")!!
            binding.rvList.adapter = APODAdapter(listOfAPOD, this)
        }
    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.btnShowList -> checkSpinners()
        }
    }

    private fun checkSpinners()
    {
        val start_day:String = binding.spStartDay.selectedItem.toString()
        val start_month:String = binding.spStartMonth.selectedItem.toString()
        val start_year:String = binding.spStartYear.selectedItem.toString()
        val end_day:String = binding.spEndDay.selectedItem.toString()
        val end_month:String = binding.spEndMonth.selectedItem.toString()
        val end_year:String = binding.spEndYear.selectedItem.toString()
        //---
        if(start_day == R.string.day.toString() || end_day == R.string.day.toString() ||
           start_month == R.string.month.toString() || end_month == R.string.month.toString() ||
           start_year == R.string.year.toString() || end_year == R.string.year.toString())
            DialogError().show(supportFragmentManager, "dialog")
       else
           getListAPOD("$start_year-$start_month-$start_day", "$end_year-$end_month-$end_day")
    }

    private fun getListAPOD(start_date:String, end_date:String)
    {
        binding.progressBar.visibility = View.VISIBLE
        //---
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(App.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //---
        var retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.getAPOD(App.KEY, start_date, end_date)
                       .enqueue(object : Callback<ArrayList<APODModel>>
        {
            override fun onResponse(call: Call<ArrayList<APODModel>>,
                                    response: Response<ArrayList<APODModel>>)
            {
                binding.progressBar.visibility= View.GONE
                if(response.isSuccessful)
                {
                    listOfAPOD = response.body()!!
                    binding.rvList.adapter = APODAdapter(listOfAPOD, this@ListActivity)
                }
                else
                    Toast.makeText(this@ListActivity,
                                    response.errorBody().toString(),
                                    Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<ArrayList<APODModel>>, t: Throwable) {
                binding.progressBar.visibility= View.GONE
                Toast.makeText(this@ListActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("arrayList", listOfAPOD)
    }
}