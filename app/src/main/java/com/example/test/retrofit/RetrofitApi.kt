package com.example.test.retrofit

import io.reactivex.Observable
import retrofit2.http.GET


interface RetrofitApi {

    @GET("/app/index.php?mode=2")
    fun getLoans(): Observable<String>
}