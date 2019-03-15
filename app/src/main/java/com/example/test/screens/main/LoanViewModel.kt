package com.example.test.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.test.retrofit.Loan
import com.example.test.retrofit.RetrofitApi
import com.example.test.utils.SingleLiveEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers

class LoanViewModel(private var retrofitApi: RetrofitApi, private val gson: Gson) : ViewModel() {

    val loansLiveData = MutableLiveData<List<Loan>>()
    val typeLiveData = MutableLiveData<List<String>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val singleLink = SingleLiveEvent<String>()
    val errorLiveData = SingleLiveEvent<String>()
    val showRetryLiveData = MutableLiveData<Boolean>()

    val diaposable = SerialDisposable()

    init {
        loadData()
    }

    fun loadData() {
        retrofitApi.getLoans()
            .subscribeOn(Schedulers.io())
            .map {
                val fromChar = it.lastIndexOf("[")
                val toChar = it.lastIndexOf("]")
                val substring = it.substring(fromChar, toChar + 1)
                gson.fromJson<List<Loan>>(substring, (object : TypeToken<List<Loan>>() {}).type)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loadingLiveData.value = true
                showRetryLiveData.value = false
            }
            .doFinally { loadingLiveData.value = false }
            .subscribe(
                { loans ->
                    loansLiveData.postValue(loans)
                    typeLiveData.value = loans.map {
                        it.offer_TYPE
                    }.distinct()
                },
                {
                    it.printStackTrace()
                    errorLiveData.value = "Ошибка во время загрузки"
                    showRetryLiveData.value = true
                }
            )
            .apply { diaposable.set(this) }
    }

    fun startLink(link:String){
        singleLink.value = link
    }

    override fun onCleared() {
        super.onCleared()
        diaposable.dispose()
    }
}
