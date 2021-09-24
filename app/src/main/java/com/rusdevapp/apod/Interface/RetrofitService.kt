package com.rusdevapp.apod.Interface

import com.rusdevapp.apod.Model.ModelNASA
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("apod?api_key=LwmzpbFYfehtgkUaH8qsLK3Qeai6qZtqtDq2Pvht")
    fun getAPOD(@Query ("start_date") start_date:String, @Query("end_date") end_date:String): Call<List<ModelNASA>>
}