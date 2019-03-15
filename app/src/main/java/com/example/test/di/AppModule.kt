package com.example.test.di

import com.example.test.screens.main.LoanViewModel
import com.example.test.retrofit.RetrofitApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val myModule: Module = module {

    viewModel { LoanViewModel(get(), get()) }
    single { getRetrofit(get()) }
    single { client() }
    single { getGson() }

}



private fun getRetrofit(client: OkHttpClient) : RetrofitApi {

        val retrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("http://freshru.ru")
            .build()
        return retrofit.create(RetrofitApi::class.java)

}

private fun client()=
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

private fun getGson():Gson=GsonBuilder().create()



