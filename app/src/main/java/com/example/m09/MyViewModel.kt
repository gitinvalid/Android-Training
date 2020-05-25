package com.example.m09

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel(private val repo: AdviceRepo) : ViewModel() {
    private val _result: MutableLiveData<String> = MutableLiveData()
    val result: LiveData<String>
        get() = _result

    fun load() {
        repo.load { _result.value = it }
    }
}