package com.liad.radiosavta.di

import co.climacell.statefulLiveData.retrofit.StatefulLiveDataCallAdapterFactory
import com.liad.radiosavta.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(StatefulLiveDataCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}