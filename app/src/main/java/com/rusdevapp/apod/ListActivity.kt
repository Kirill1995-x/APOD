package com.rusdevapp.apod

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ArrayAdapter
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
        val startDayArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                               R.layout.item_spinner, resources.getStringArray(R.array.array_day))
        startDayArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spStartDay.adapter=startDayArrayAdapter
        val startMonthArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                               R.layout.item_spinner, resources.getStringArray(R.array.array_month))
        startMonthArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spStartMonth.adapter=startMonthArrayAdapter
        val startYearArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                               R.layout.item_spinner, resources.getStringArray(R.array.array_year))
        startYearArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spStartYear.adapter=startYearArrayAdapter
        val endDayArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                               R.layout.item_spinner, resources.getStringArray(R.array.array_day))
        endDayArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spEndDay.adapter=endDayArrayAdapter
        val endMonthArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                               R.layout.item_spinner, resources.getStringArray(R.array.array_month))
        endMonthArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spEndMonth.adapter=endMonthArrayAdapter
        val endYearArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                               R.layout.item_spinner, resources.getStringArray(R.array.array_year))
        endYearArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        binding.spEndYear.adapter=endYearArrayAdapter
        //---
        if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT)
            binding.rvList.layoutManager = LinearLayoutManager(this)
        else
            binding.rvList.layoutManager = GridLayoutManager(this, 2)
        //---
        if(savedInstanceState?.getParcelableArrayList<APODModel>("arrayList") != null)
        {
            listOfAPOD = savedInstanceState.getParcelableArrayList<APODModel>("arrayList")
                         as ArrayList<APODModel>
            binding.rvList.adapter = APODAdapter(listOfAPOD, this)
        }
        binding.btnShowList.setOnClickListener(this)
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
        if(start_day == resources.getString(R.string.day) ||
           end_day == resources.getString(R.string.day) ||
           start_month == resources.getString(R.string.month) ||
           end_month == resources.getString(R.string.month) ||
           start_year == resources.getString(R.string.year) ||
           end_year == resources.getString(R.string.year))
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
        if(::listOfAPOD.isInitialized)outState.putParcelableArrayList("arrayList", listOfAPOD)
        outState.putInt("start_day", binding.spStartDay.selectedItemPosition)
        outState.putInt("start_month", binding.spStartMonth.selectedItemPosition)
        outState.putInt("start_year", binding.spStartYear.selectedItemPosition)
        outState.putInt("end_day", binding.spEndDay.selectedItemPosition)
        outState.putInt("end_month", binding.spEndMonth.selectedItemPosition)
        outState.putInt("end_year", binding.spEndYear.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.spStartDay.setSelection(savedInstanceState.getInt("start_day"))
        binding.spStartMonth.setSelection(savedInstanceState.getInt("start_month"))
        binding.spStartYear.setSelection(savedInstanceState.getInt("start_year"))
        binding.spEndDay.setSelection(savedInstanceState.getInt("end_day"))
        binding.spEndMonth.setSelection(savedInstanceState.getInt("end_month"))
        binding.spEndYear.setSelection(savedInstanceState.getInt("end_year"))
    }
}