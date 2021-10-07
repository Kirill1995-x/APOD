package com.rusdevapp.apod.Interface

import com.rusdevapp.apod.Model.APODModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("apod?")
    fun getAPOD(@Query("api_key") key:String,
                @Query ("start_date") start_date:String,
                @Query("end_date") end_date:String): Call<ArrayList<APODModel>>
}